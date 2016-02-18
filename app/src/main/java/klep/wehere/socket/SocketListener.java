package klep.wehere.socket;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.Gson;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import klep.wehere.auth.AuthPresenter;
import klep.wehere.maps.MapPresenter;
import klep.wehere.model.user.Users;
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
            Intent intent;

            switch (method){
                case AUTH:

                    int code = new JSONObject(json.getString("data")).getInt("code");
                    intent = new Intent(AuthPresenter.EngineReceiver);
                    intent.putExtra(AuthPresenter.WS_AUTH,code);

                    context.sendBroadcast(intent);
                    break;

                case RELATION:
                    parseRelation(json);

                case UPDATE:
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseRelation(JSONObject json) {
        Users ss;
        Subscriber<Users> subscriber = new Subscriber<Users>() {
            @Override
            public void onCompleted() {
                Log.d("asd","asd");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("asd","asd");

            }

            @Override
            public void onNext(Users users) {

                    Intent intent = new Intent(MapPresenter.TAG_RECEIVER);
                    intent.putExtra(MapPresenter.GET_RELATIONS, users);
                    context.sendBroadcast(intent);
            }
        };



        Observable<List<Users>> observable = (Observable<List<Users>>) Observable.just(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(jsonObject -> new Gson().fromJson(""+json,Users.class))
                .subscribe(subscriber);

    }
}
