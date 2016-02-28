package klep.wehere.registration;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import klep.wehere.auth.AuthView;
import klep.wehere.common.ServiceRetrofit;
import klep.wehere.maps.HandleActivity;
import klep.wehere.model.Authentication;
import klep.wehere.model.RegistrationCredentials;
import klep.wehere.model.error.ErrorHandlerModel;
import klep.wehere.model.image.ResizeImage;
import klep.wehere.model.image.ResizeImageImpl;
import klep.wehere.model.token.Token;
import klep.wehere.model.token.TokenSubscribe;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by klep.io on 14.02.16.
 */

public class RegPresenter extends MvpBasePresenter<RegView> {
    Subscriber <Token> subscriber;

    public void resizeImage(String path){
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        ResizeImage resizeImage = new ResizeImageImpl();

        resizeImage.doResize(bitmap)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(bitmap1 -> Bitmap.createScaledBitmap(bitmap,100,100,false))
                .subscribe(bitmap1 -> {
                    getView().showImage(bitmap1);
                });

    }
    public void doReg(RegistrationCredentials credentials){
        if (isViewAttached()){
            getView().showRegLoading();
        }
        final Authentication authentication = ServiceRetrofit
                .createService(Authentication.class);

        subscriber = new TokenSubscribe() {
            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Token token) {
                if (token.getToken() == null){
                    getView().showRegError(1);
                }else {
                    super.onNext(token);
                    getView().showRegComplete();
                }
            }
        };

        authentication.registration(
                credentials.getLogin(),
                credentials.getPassword1(),
                credentials.getPassword2(),
                credentials.getName(),
                credentials.getRequestBody())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private void cancelSubscribe(){
        if (subscriber != null && !subscriber.isUnsubscribed()){
            subscriber.unsubscribe();
        }
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (!retainInstance){
            cancelSubscribe();
        }
    }

    @Override
    public void attachView(RegView view) {
        super.attachView(view);
    }
}
