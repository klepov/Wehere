package klep.wehere.addChildren;

import com.orhanobut.hawk.Hawk;

import klep.wehere.common.RegPresenter;
import klep.wehere.common.ServiceRetrofit;
import klep.wehere.model.Authentication;
import klep.wehere.model.RegistrationCredentials;
import klep.wehere.model.error.ErrorHandlerModel;
import klep.wehere.utils.Const;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by klep.io on 14.02.16.
 */
public class RegPresenterChild extends RegPresenter {
    Subscriber<ErrorHandlerModel> subscriber;

    public void doReg(RegistrationCredentials credentials) {
        if (isViewAttached()) {
            getView().showRegLoading();
        }
        final Authentication authentication = ServiceRetrofit
                .createService(Authentication.class);

        subscriber = new Subscriber<ErrorHandlerModel>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(ErrorHandlerModel errorHandlerModel) {
                if (errorHandlerModel.getData().getCode() == 99 && isViewAttached()) {
                    getView().showRegComplete();
                }

                getView().showRegError(errorHandlerModel.getData().getCode());


            }
        };

        String token = Hawk.get(Const.TOKEN);
        String con = "Token " + token;
        authentication.registrationChild(
                con,
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
