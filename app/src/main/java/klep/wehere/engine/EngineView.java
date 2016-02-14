package klep.wehere.engine;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by klep.io on 14.02.16.
 */
public interface EngineView extends MvpView{

    void showLoading();
    void loadingSuccessful();
    void showError(int error);
}
