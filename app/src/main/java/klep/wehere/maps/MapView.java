package klep.wehere.maps;

import com.hannesdorfmann.mosby.mvp.MvpView;

import klep.wehere.model.user.Datum;
import klep.wehere.model.user.Users;

/**
 * Created by klep.io on 16.02.16.
 */
public interface MapView extends MvpView{

    void showUpdate(Users datum);
    void showMap();
    void showLoading();
    void showError();
}
