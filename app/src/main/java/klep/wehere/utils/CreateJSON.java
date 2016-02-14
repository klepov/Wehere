package klep.wehere.utils;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by klep.io on 14.02.16.
 */
public class CreateJSON {

    public static JSONObject auth(String login){
        JSONObject data = new JSONObject();
        JSONObject method = new JSONObject();

        try {
            method.put("method","auth");

            data.put("username",login);

            method.put("data",data);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("method",""+method);
        return method;
    }
}
