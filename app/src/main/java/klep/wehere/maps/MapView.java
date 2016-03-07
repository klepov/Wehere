package klep.wehere.maps;

import android.widget.ImageView;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.ArrayList;
import java.util.List;

import klep.wehere.model.user.User;
import klep.wehere.model.users.Data;
import klep.wehere.model.users.Users;

/**
 * Created by klep.io on 16.02.16.
 */
public interface MapView extends MvpView{

    void updateRelation(List<Data> users);
    void updateUser(Data user);
    void showMap();
    void showLoading();
    void showError();
}
