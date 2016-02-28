package klep.wehere.socket;

import android.content.Context;
import android.content.Intent;

import klep.wehere.maps.MapPresenter;
import klep.wehere.model.user.User;
import klep.wehere.model.users.Users;

/**
 * Created by klep.io on 21.02.16.
 */
public class SocketListener extends HandlerSubscribe{
    private Context context;

    public SocketListener(Context context) {
        this.context = context;
    }


    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(Object o) {
        if (o instanceof Users){
            Intent intent = new Intent(MapPresenter.GET_RELATIONS );
            intent.putExtra(MapPresenter.ABSTRACT_USER, (Users)o);
            context.sendBroadcast(intent);
        }
        else {
            Intent intent = new Intent(MapPresenter.GET_UPDATE_USER);
            intent.putExtra(MapPresenter.ABSTRACT_USER, (User)o);
            context.sendBroadcast(intent);

        }
    }
}
