package klep.wehere.auth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import klep.wehere.common.ServiceRetrofit;
import klep.wehere.model.Authentication;
import klep.wehere.model.error.ErrorHandlerModel;
import klep.wehere.model.token.Token;
import klep.wehere.model.token.TokenSubscribe;
import klep.wehere.model.token.TokenTest;
import klep.wehere.utils.CreateJSON;
import klep.wehere.utils.ErrorCode;
import klep.wehere.utils.SendJSONToServer;
import retrofit.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by klep.io on 31.01.16.
 */
public class AuthPresenter extends MvpBasePresenter<AuthView> {

    public static final String WS_AUTH = "WS_AUTH";
    public static final String EngineReceiver = "EnginePresenterReceiver";
    public static final int SUCCESS = 77;
    private BroadcastReceiver engineReceiver;

    Subscriber<Token> subscriber;

    private Context context;

    public AuthPresenter(Context context) {
        this.context = context;
    }

    public void doLogin(String login, String password) {

        final Authentication authentication = ServiceRetrofit
                .createService(Authentication.class);


        if (isViewAttached()) {
            getView().showLoading();
        }

        subscriber = new TokenSubscribe() {
            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(Token token) {
                super.onNext(token);
                SendJSONToServer.sendJsonToServer(CreateJSON.auth(token.getToken()));
            }
        };

        authentication.login(login,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }



    public void startReceiver() {
        engineReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int error = intent.getExtras().getInt(WS_AUTH);
                if (error == SUCCESS){

                    getView().authSuccessful();
                }
                else {
                    getView().showError(error);
                }

            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(EngineReceiver);
        context.registerReceiver(engineReceiver,intentFilter);
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
            context.unregisterReceiver(engineReceiver);
        }
    }

    @Override
    public void attachView(AuthView view) {
        super.attachView(view);
        startReceiver();
    }
}
