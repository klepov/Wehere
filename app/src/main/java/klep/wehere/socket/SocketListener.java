package klep.wehere.socket;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketFrame;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import klep.wehere.auth.AuthPresenter;
import klep.wehere.engine.EnginePresenter;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dima on 10.11.15.
 */
public class SocketListener extends WebSocketAdapter {

    private static final String AUTH = "auth";
    private static final String RELATION = "list_relation";
    private static final String UPDATE = "update";
    private Context context;

    public SocketListener(Context context) {
        this.context = context;
    }

    @Override
    public void onTextMessage(WebSocket websocket, String text) throws Exception {
        parserJSON(text);
    }

    private void parserJSON(String text) {
        try {
            JSONObject json = new JSONObject(text);
            String method = json.getString("method");

            switch (method){
                case AUTH:

                    int code = new JSONObject(json.getString("data")).getInt("code");
                    Intent intent = new Intent(AuthPresenter.EngineReceiver);
                    intent.putExtra(AuthPresenter.WS_AUTH,code);

                    context.sendBroadcast(intent);
                    break;

                case RELATION:
                    break;
                case UPDATE:
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
