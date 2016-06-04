package klep.wehere.socket;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.orhanobut.hawk.Hawk;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import klep.wehere.auth.AuthPresenter;
import klep.wehere.model.user.User;
import klep.wehere.model.users.Data;
import klep.wehere.model.users.Users;
import klep.wehere.utils.Const;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SocketAdapter extends WebSocketAdapter {

    private static final String AUTH = "auth";
    private static final String RELATION = "list_relation";
    private static final String UPDATE = "update";
    private Context context;
    private WebSocket ws;

    public SocketAdapter(Context context) {
        this.context = context;
        EventBus.getDefault().registerSticky(this);
    }

    @Override
    public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
        super.onConnected(websocket, headers);
        ws = websocket;
    }

    @Override
    public void onTextMessage(WebSocket websocket, String text) throws Exception {
        parserJSON(text);
        Log.d("LOGGGGG", text);
    }


    public void onEvent(MessageEvent event) {
        ws.sendText(event.json);
    }


    private void parserJSON(String text) {
        try {
            JSONObject json = new JSONObject(text);
            String method = json.getString("method");
            Log.d("methods", method);
            Intent intent;

            switch (method) {
                case AUTH:
                    int code = new JSONObject(json.getString("data")).getInt("code");
                    Data data = new Gson().fromJson(json.getString("user"), Data.class);
                    Hawk.put(Const.USER, data);
                    intent = new Intent(AuthPresenter.EngineReceiver);
                    intent.putExtra(AuthPresenter.WS_AUTH, code);
                    context.sendBroadcast(intent);
                    break;
                case RELATION:
                    parseUsers(json);
                case UPDATE:
                    parseUser(json);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void parseUsers(JSONObject json) {


        Observable<List<Users>> observableList = (Observable<List<Users>>) Observable.just(json)
                .onErrorReturn(throwable -> {
                    throwable.printStackTrace();
                    return null;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(jsonObject -> new Gson().fromJson("" + json, Users.class))
                .subscribe(new SocketListener(context));
    }

    private void parseUser(JSONObject json) {


        Observable<User> observableList = (Observable<User>) Observable.just(json)
                .onErrorReturn(throwable -> {
                    throwable.printStackTrace();
                    return null;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(jsonObject -> new Gson().fromJson("" + json, User.class))
                .subscribe(new SocketListener(context));

    }


}
