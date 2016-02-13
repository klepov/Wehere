//package klep.wehere.services;
//
//import android.Manifest;
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.os.Bundle;
//import android.os.IBinder;
//import android.provider.Settings;
//import android.support.v4.app.ActivityCompat;
//import android.telephony.TelephonyManager;
//import android.util.Log;
//
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationServices;
//import com.neovisionaries.ws.client.WebSocket;
//import com.neovisionaries.ws.client.WebSocketException;
//import com.neovisionaries.ws.client.WebSocketFactory;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//
//import de.greenrobot.event.EventBus;
//import klep.wehere.CreateJSON;
//import klep.wehere.socket.MessageEvent;
//import klep.wehere.socket.SocketListener;
//import klep.wehere.utils.Const;
//
//public class HandlerSocket extends Service implements GoogleApiClient.ConnectionCallbacks, com.google.android.gms.location.LocationListener {
//    GoogleApiClient googleApiClient;
//    LocationRequest locationRequest;
//    WebSocket ws;
//    private double latitude;
//    private double longitude;
//    private String IMEI;
//    private String device_id;
//
//    @Override
//    public void onCreate() {
//
//        super.onCreate();
//        startSocket();
////        Log.d("start", "services start");
////
////        TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
////        IMEI = mngr.getDeviceId();
////        device_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
////                Settings.Secure.ANDROID_ID);
////        Log.d("ss",device_id);
//
//        googleApiClient = new GoogleApiClient.Builder(this)
//                .addApi(LocationServices.API)
//                .addConnectionCallbacks(this)
//                .build();
//
//        googleApiClient.connect();
//
//
//        locationRequest = new LocationRequest();
////        locationRequest.setSmallestDisplacement(10);
//        locationRequest.setInterval(100); // Update location every 1 minute
////        locationRequest.setFastestInterval(10000);
//
//
////        getLocal();
//    }
//
//    private void startSocket() {
//        new Thread(() -> {
//            try {
//                ws = new WebSocketFactory().createSocket(Const.WS_URL);
//                ws.addListener(new SocketListener());
//                ws.connect();
//                ws.sendText(String.valueOf(CreateJSON.auth(device_id)));
//            } catch (IOException | WebSocketException e) {
//                e.printStackTrace();
//            }
//        }).start();
//    }
//
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
//
//    @Override
//    public void onDestroy() {
//        Log.d("stop", "services stop");
//        googleApiClient.disconnect();
//        try {
//
//            ws.disconnect();
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }
//        stopSelf();
//    }
//
//
//    @Override
//    public void onConnected(Bundle bundle) {
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        LocationServices.FusedLocationApi.requestLocationUpdates(
//                googleApiClient, locationRequest, this);
//
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
//
//
//        EventBus.getDefault().post(new MessageEvent(""+CreateJSON.update(IMEI,device_id,latitude,longitude)));
//
//    }
//}
