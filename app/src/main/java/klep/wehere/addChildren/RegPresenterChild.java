package klep.wehere.addChildren;

import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import klep.wehere.common.ServiceRetrofit;
import klep.wehere.model.Authentication;
import klep.wehere.model.RegistrationCredentials;
import klep.wehere.model.error.ErrorHandlerModel;
import klep.wehere.model.token.Token;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by klep.io on 14.02.16.
 */
public class RegPresenterChild extends MvpBasePresenter<RegViewChild> {
    Subscriber <ErrorHandlerModel> subscriber;

    public void doReg(RegistrationCredentials credentials){
        if (isViewAttached()){
            getView().showRegLoading();
        }
        final Authentication authentication = ServiceRetrofit
                .createService(Authentication.class);

        subscriber = new Subscriber<ErrorHandlerModel>() {
            @Override
            public void onCompleted() {
                Log.d("asd","complite");

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(ErrorHandlerModel errorHandlerModel) {
                if (errorHandlerModel.getData().getCode() == 99 && isViewAttached()){
                    getView().showRegComplete();
                }

                getView().showRegError(errorHandlerModel.getData().getCode());
            }
        };

        String token = Token.find(Token.class,null,null).get(0).getToken();
        String con = "Token "+token;
        Log.d("token_is",con);
        authentication.registrationChild(
                con,
                credentials.getLogin(),
                credentials.getPassword1(),
                credentials.getPassword2())
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
    public void attachView(RegViewChild view) {
        super.attachView(view);
    }
}
