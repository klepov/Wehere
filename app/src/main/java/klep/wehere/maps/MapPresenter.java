package klep.wehere.maps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

/**
 * Created by klep.io on 16.02.16.
 */
public class MapPresenter extends MvpBasePresenter<MapView> {

    private static final String UPDATE_POLY = "UPDATE_POLY_MAP";
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



    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        context.unregisterReceiver(mapReceiver);
    }

    private void regReceiver(){
        mapReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        };

        IntentFilter intentFilter = new IntentFilter(TAG_RECEIVER);
        intentFilter.addAction(UPDATE_POLY);
        context.registerReceiver(mapReceiver,intentFilter);
    }
}
