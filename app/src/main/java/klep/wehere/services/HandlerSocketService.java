package klep.wehere.services;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;

import java.io.IOException;

import de.greenrobot.event.EventBus;
import klep.wehere.socket.MessageEvent;
import klep.wehere.socket.SocketAdapter;
import klep.wehere.utils.Const;

public class HandlerSocketService extends Service {
    GoogleApiClient googleApiClient;
    LocationRequest locationRequest;
    WebSocket ws;


    @Override
    public void onCreate() {

        super.onCreate();
        startSocket();
        EventBus.getDefault().registerSticky(this);
    }

    private void startSocket() {
        new Thread(() -> {
            try {
                synchronized (HandlerSocketService.class) {
                    ws = new WebSocketFactory().createSocket(Const.WS_URL);
                    ws.addListener(new SocketAdapter(getApplicationContext()));
                    ws.connect();
                }
            } catch (IOException | WebSocketException e) {
                e.printStackTrace();
            }
        }).start();
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void onEvent(MessageEvent event) {

        if (ws == null) {
//            если сокет не открыт -
//            синхранизировать потоки
            synchronized (HandlerSocketService.class) {
                if (ws == null) {
//                    если и там он не открыт
//                    то открыть принудительно
                    startSocket();
//                  ждать пока откроется за 10 попыток
                    int attempts = 10;
                    while (ws == null && !(attempts == 0)) {
                        SystemClock.sleep(100);
                        attempts--;
                    }
                } else {
                    ws.sendText(event.json);
                }

            }

        } else {
            ws.sendText(event.json);
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        try {

            ws.disconnect();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        stopSelf();
    }

}
