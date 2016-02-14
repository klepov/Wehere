package klep.wehere.engine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;

import klep.wehere.utils.CreateJSON;
import klep.wehere.utils.SendJSONToServer;

/**
 * Created by klep.io on 14.02.16.
 */
public class EnginePresenter extends MvpBasePresenter<EngineView> {
//    public static final String WS_AUTH = "WS_AUTH";
//    private BroadcastReceiver engineReceiver;

    private Context context;


    public EnginePresenter(Context context) {
        super();
        this.context = context;
    }

    @Override
    public void attachView(EngineView view) {
        super.attachView(view);
//        startReceiver();
    }

    public void doLogin(String login){
        if (isViewAttached()){


            getView().showLoading();
            SendJSONToServer.sendJsonToServer
                    (CreateJSON.auth(login));
        }

    }

//    public void startReceiver() {
//        engineReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                Log.d("asdQQ",intent.getAction());
//            }
//        };
//
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(WS_AUTH);
//        context.registerReceiver(engineReceiver,intentFilter);
//    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
//        context.unregisterReceiver(engineReceiver);
    }
}