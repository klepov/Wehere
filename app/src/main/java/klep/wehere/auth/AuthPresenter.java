package klep.wehere.auth;

import android.util.Log;
import android.view.View;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import klep.wehere.common.ServiceRetrofit;
import klep.wehere.model.Authentication;
import klep.wehere.model.error.ErrorHandlerModel;
import klep.wehere.utils.ErrorCode;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by klep.io on 31.01.16.
 */
public class AuthPresenter extends MvpBasePresenter<AuthView> {

    Subscriber<ErrorHandlerModel> subscriber;

    public void doLogin(String login, String password) {

        final Authentication authentication = ServiceRetrofit
                .createService(Authentication.class);


        if (isViewAttached()) {
            getView().showLoading();
        }

        subscriber = new Subscriber<ErrorHandlerModel>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {

                e.printStackTrace();
            }

            @Override
            public void onNext(ErrorHandlerModel errorCode) {
                if (errorCode.getData().getCode() == 99){
                    getView().authSuccessful();
                }

                getView().showError(errorCode.getData().getCode());
            }
        };

        authentication.login(login, password)
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
    public void attachView(AuthView view) {
        super.attachView(view);
    }
}
