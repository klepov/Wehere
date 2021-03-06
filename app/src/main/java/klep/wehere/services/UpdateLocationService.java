package klep.wehere.services;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.neovisionaries.ws.client.WebSocket;
import com.orhanobut.hawk.Hawk;

import org.json.JSONObject;

import klep.wehere.utils.Const;
import klep.wehere.utils.CreateJSON;
import klep.wehere.utils.SendJSONToServer;

public class UpdateLocationService extends Service implements GoogleApiClient.ConnectionCallbacks, com.google.android.gms.location.LocationListener {
    GoogleApiClient googleApiClient;
    LocationRequest locationRequest;
    WebSocket ws;
    private double latitude;
    private double longitude;
    private String device_id;

    @Override
    public void onCreate() {

        super.onCreate();

//
        TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        device_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .build();

        googleApiClient.connect();


        locationRequest = new LocationRequest();
//        locationRequest.setSmallestDisplacement(3);
        locationRequest.setInterval(5000); // Update location every 1 minute
//        locationRequest.setFastestInterval(10000);


//        getLocal();
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onDestroy() {
        googleApiClient.disconnect();
        stopSelf();
    }


    @Override
    public void onConnected(Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient, locationRequest, this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        latitude = location.getLatitude();
        longitude = location.getLongitude();
        Log.d("coordinate", latitude + " " + longitude);

        String token = Hawk.get(Const.TOKEN);
        if (token == null) {
            return;
        }
        JSONObject json = CreateJSON.
                updateLocation(token, device_id, latitude, longitude);
        SendJSONToServer.sendJsonToServer(json);

    }
}
