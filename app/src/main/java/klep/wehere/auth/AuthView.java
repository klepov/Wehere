package klep.wehere.auth;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by klep.io on 31.01.16.
 */
public interface AuthView extends MvpView{

    void showAuthForm();
    void showError(int error);
    void showLoading();
    void authSuccessful();
}
