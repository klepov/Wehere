package klep.wehere.registration;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import klep.wehere.common.RegPresenter;
import klep.wehere.common.RegView;
import klep.wehere.common.ServiceRetrofit;
import klep.wehere.model.Authentication;
import klep.wehere.model.RegistrationCredentials;
import klep.wehere.model.token.Token;
import klep.wehere.model.token.TokenSubscribe;
import klep.wehere.utils.CreateJSON;
import klep.wehere.utils.SendJSONToServer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by klep.io on 28.02.16.
 */
public class RegParentPresenter extends RegPresenter {
    Subscriber<Token> subscriber;
    BroadcastReceiver engineReceiver;

    public static final String WS_AUTH = "WS_AUTH";
    public static final String EngineReceiver = "EnginePresenterReceiver";
    public static final int SUCCESS = 77;
    Context context;

    RegParentPresenter(Context context) {
        this.context = context;
    }
    @Override
    public void doReg(RegistrationCredentials credentials) {
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
                    SendJSONToServer.sendJsonToServer(CreateJSON.auth(token.getToken()));

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

    public void startReceiver() {
        engineReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int error = intent.getExtras().getInt(WS_AUTH);
                if (error == SUCCESS) {

                    getView().showRegComplete();
                } else {
                    getView().showRegError(error);
                }

            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(EngineReceiver);
        context.registerReceiver(engineReceiver,intentFilter);
    }

    @Override
    public void attachView(RegView view) {
        super.attachView(view);
        startReceiver();
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        context.unregisterReceiver(engineReceiver);
    }
}
