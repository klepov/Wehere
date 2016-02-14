package klep.wehere.utils;

import android.util.Log;

import org.json.JSONObject;

import de.greenrobot.event.EventBus;
import klep.wehere.socket.MessageEvent;

/**
 * Created by klep.io on 14.02.16.
 */
public class SendJSONToServer {
    public static void sendJsonToServer(JSONObject json) {

        send(String.valueOf(json));

    }

    private static void send(final String json) {
        EventBus.getDefault().postSticky(new MessageEvent(json));
    }
}
