package klep.wehere.registration;

import android.graphics.Bitmap;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by klep.io on 14.02.16.
 */
public interface RegView extends MvpView {

    void showRegForm();
    void showRegError(int error);
    void showRegComplete();
    void showRegLoading();
    void showImage(Bitmap bitmap);
}
