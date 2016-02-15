package klep.wehere.maps;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by klep.io on 16.02.16.
 */
public interface MapView extends MvpView{

    void showUpdate();
    void showMap();
    void showLoading();
    void showError();
}
