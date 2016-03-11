package klep.wehere.registration;

import klep.wehere.common.RegPresenter;
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
}
