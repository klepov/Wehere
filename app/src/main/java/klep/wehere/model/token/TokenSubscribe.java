package klep.wehere.model.token;

import com.orhanobut.hawk.Hawk;

import klep.wehere.utils.Const;
import rx.Subscriber;

/**
 * Created by klep.io on 22.02.16.
 */
public abstract class TokenSubscribe extends Subscriber<Token> {
    @Override
    public void onCompleted() {

    }

    @Override
    public abstract void onError(Throwable e);

    @Override
    public void onNext(Token token) {
        Hawk.clear();
        Hawk.put(Const.TOKEN,token.getToken());
    }
}
