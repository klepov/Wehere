package klep.wehere.addChildren;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by klep.io on 14.02.16.
 */
public interface RegViewChild extends MvpView {

    public void showRegForm();
    public void showRegError(int error);
    public void showRegComplete();
    public void showRegLoading();
}