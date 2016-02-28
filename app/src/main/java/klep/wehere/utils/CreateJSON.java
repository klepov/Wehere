package klep.wehere.utils;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by klep.io on 14.02.16.
 */
public class CreateJSON {

    public static JSONObject auth(String token){
        JSONObject data = new JSONObject();
        JSONObject method = new JSONObject();

        try {
            method.put("method","auth");

            data.put("token",token);

            method.put("data",data);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("method",""+method);
        return method;
    }

    public static JSONObject listRelation(String token){
        JSONObject data = new JSONObject();
        JSONObject method = new JSONObject();

        try {
            method.put("method","list_relation");

            data.put("token",token);

            method.put("data",data);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("method",""+method);
        return method;
    }


public static JSONObject updateLocation(String token, String device_ID, String IMEI, double latitude, double longitude){
    JSONObject data = new JSONObject();
    JSONObject method = new JSONObject();

    try {
        method.put("method","update");

        data.put("token",token);
        data.put("device_ID",device_ID);
        data.put("IMEI",IMEI);
        data.put("latitude",latitude);
        data.put("longitude",longitude);

        method.put("data",data);

    } catch (JSONException e) {
        e.printStackTrace();
    }
    Log.d("method",""+method);
    return method;
}



}
