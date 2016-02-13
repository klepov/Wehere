package klep.wehere;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by klep.io on 09.01.16.
 */
public class CreateJSON {

    public static JSONObject auth(String device_ID){
        JSONObject method = new JSONObject();
        JSONObject data = new JSONObject();

        try {
            method.put("method","auth");
            data.put("device_ID", device_ID);
            method.put("data",data);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return method;
    }

//    {"method": "create_or_update","data":
//      {"IMEI": 666,"device_ID": "parent","latitude":10.6666,"longitude": 10.548874}}

    public static JSONObject update(String IMEI, String device_ID,double latitude,double longitude){
        JSONObject method = new JSONObject();
        JSONObject data = new JSONObject();

        try {
            method.put("method","create_or_update");
            data.put("IMEI",IMEI);
            data.put("device_ID",device_ID);
            data.put("latitude",latitude);
            data.put("longitude",longitude);
            method.put("data",data);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return method;
    }

}
