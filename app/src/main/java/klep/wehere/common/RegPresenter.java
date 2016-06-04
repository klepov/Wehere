package klep.wehere.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.orhanobut.hawk.Hawk;

import klep.wehere.model.Authentication;
import klep.wehere.model.EditCredentials;
import klep.wehere.model.RegistrationCredentials;
import klep.wehere.model.error.ErrorHandlerModel;
import klep.wehere.model.image.ResizeImage;
import klep.wehere.model.image.ResizeImageImpl;
import klep.wehere.model.token.Token;
import klep.wehere.utils.Const;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by klep.io on 14.02.16.
 */

public abstract class RegPresenter extends MvpBasePresenter<RegView> {
    Subscriber<Token> subscriber;

    public void resizeImage(String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        ResizeImage resizeImage = new ResizeImageImpl();

        resizeImage.doResize(bitmap)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(bitmap1 -> Bitmap.createScaledBitmap(bitmap, 100, 100, false))
                .subscribe(bitmap1 -> {
                    getView().showImage(bitmap1);
                });

    }

    public abstract void doReg(RegistrationCredentials credentials);

    private void cancelSubscribe() {
        if (subscriber != null && !subscriber.isUnsubscribed()) {
            subscriber.unsubscribe();
        }
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (!retainInstance) {
            cancelSubscribe();
        }
    }

    @Override
    public void attachView(RegView view) {
        super.attachView(view);
    }

    public void doEdit(EditCredentials editCredentials) {
        final Authentication authentication = ServiceRetrofit
                .createService(Authentication.class);
        authentication.editUser(
                Hawk.get(Const.TOKEN),
                editCredentials.getName(),
                editCredentials.getUser(),
                editCredentials.getRequestBody())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ErrorHandlerModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ErrorHandlerModel errorHandlerModel) {
                        if (errorHandlerModel.getData().getCode() == 99) {
                            getView().showRegComplete();
                        } else if (errorHandlerModel.getData().getCode() == 7) {
                            getView().showRegError(7);
                        }
                    }
                });
    }
}
