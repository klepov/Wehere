package klep.wehere.maps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import klep.wehere.model.user.Datum;
import klep.wehere.model.user.Users;
import klep.wehere.utils.CreateJSON;
import klep.wehere.utils.SendJSONToServer;

/**
 * Created by klep.io on 16.02.16.
 */
public class MapPresenter extends MvpBasePresenter<MapView> {

    public static final String GET_RELATIONS = "GET_RELATIONS";
    private Context context;
    BroadcastReceiver mapReceiver;
    public final static String TAG_RECEIVER = "MapReceiver.class";

    public MapPresenter(Context context) {
        super();
        this.context = context;
    }

    @Override
    public void attachView(MapView view) {
        super.attachView(view);
        regReceiver();
    }

    public void getRelation(String login){
        SendJSONToServer.sendJsonToServer(CreateJSON.listRelation("chil"));
//        getView().showLoading();
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        context.unregisterReceiver(mapReceiver);
    }

    private void regReceiver(){
        mapReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Users u = intent.getExtras().getParcelable(GET_RELATIONS);

                getView().showUpdate(u);
            }
        };

        IntentFilter intentFilter = new IntentFilter(TAG_RECEIVER);
        intentFilter.addAction(GET_RELATIONS);
        context.registerReceiver(mapReceiver,intentFilter);
    }
}
