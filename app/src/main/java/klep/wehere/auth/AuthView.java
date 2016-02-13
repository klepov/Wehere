package klep.wehere.auth;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by klep.io on 31.01.16.
 */
public interface AuthView extends MvpView{

    public void showAuthForm();
    public void showError(int error);
    public void showLoading();
    public void authSuccessful();
}
