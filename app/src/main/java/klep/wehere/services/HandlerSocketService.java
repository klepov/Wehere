package klep.wehere.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;

import java.io.IOException;

import de.greenrobot.event.EventBus;
import klep.wehere.socket.MessageEvent;
import klep.wehere.socket.SocketAdapter;
import klep.wehere.utils.Const;

public class HandlerSocketService extends Service {

    WebSocket ws;


    @Override
    public void onCreate() {

        super.onCreate();
        startSocket();
    }

    private void startSocket() {

        new Thread(() -> {
            try {
                ws = new WebSocketFactory().createSocket(Const.WS_URL);
                ws.addListener(new SocketAdapter(getApplicationContext()));
                ws.connect();
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

    @Override
    public void onDestroy() {
        try {
            ws.disconnect();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        stopSelf();
    }

}
