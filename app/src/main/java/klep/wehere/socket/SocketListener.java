package klep.wehere.socket;

import android.util.Log;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketFrame;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dima on 10.11.15.
 */
public class SocketListener extends WebSocketAdapter {

    WebSocket mWebSocket;

    @Override
    public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
        super.onDisconnected(websocket, serverCloseFrame, clientCloseFrame, closedByServer);
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(MessageEvent event){
        mWebSocket.sendText(event.json);
    }

    @Override
    public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
        Log.d("tru","connect");
        EventBus.getDefault().register(this);
        mWebSocket = websocket;
    }

    @Override
    public void onTextMessage(WebSocket websocket, String text) throws Exception {

        Log.d("devices",text);
    }
}
