package klep.wehere.maps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import klep.wehere.model.user.User;
import klep.wehere.model.users.Data;
import klep.wehere.model.users.Users;
import klep.wehere.utils.Const;
import klep.wehere.utils.CreateJSON;
import klep.wehere.utils.SendJSONToServer;

/**
 * Created by klep.io on 16.02.16.
 */
public class MapPresenter extends MvpBasePresenter<MapView> {

    public static final String GET_RELATIONS = "GET_RELATIONS";
    public static final String GET_UPDATE_USER = "GET_UPDATE_USER";
    private Context context;
    BroadcastReceiver mapReceiver;
    public final static String ABSTRACT_USER = "MapReceiver.class";

    private List<Data> user;
    public MapPresenter(Context context) {
        super();
        this.context = context;
    }

    @Override
    public void attachView(MapView view) {
        super.attachView(view);
        user = new ArrayList<>();
        regReceiver();
    }

    public void getRelation(String token){

        SendJSONToServer.sendJsonToServer(CreateJSON.listRelation(token));
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
                Data dataUser;
                switch (intent.getAction()){
                    case GET_RELATIONS:
                        Users userRelation = intent.getExtras().getParcelable(ABSTRACT_USER);
                        user.addAll(userRelation.getData());
                        break;


                    case GET_UPDATE_USER:
                        User updateUser = intent.getExtras().getParcelable(ABSTRACT_USER);
                        dataUser = updateUser.getData();
                        user.add(dataUser);
                        break;


                }
//                ArrayList <ImageView> imageViews = inflateImageData();
                getView().showUpdate(user);
                user.clear();



//                Users u = intent.getExtras().getParcelable(GET_RELATIONS);
//                getView().showUpdate(u);
            }
        };

        IntentFilter intentFilter = new IntentFilter(ABSTRACT_USER);
        intentFilter.addAction(GET_RELATIONS);
        intentFilter.addAction(GET_UPDATE_USER);
        context.registerReceiver(mapReceiver,intentFilter);
    }

    private ArrayList<ImageView> inflateImageData(){

        ArrayList <ImageView> images =  new ArrayList<ImageView>();
        for (int i = 0; i<user.size(); i++){
            ImageView imageView = new ImageView(context);


            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(30,30);
            layoutParams.gravity= Gravity.CENTER;
            imageView.setLayoutParams(layoutParams);
            Picasso.with(context)
                    .load(Const.IMAGE_URL + user.get(i).getLinkToImage())
                    .into(imageView);
            images.add(imageView);
        }


        return images;
    }
}
