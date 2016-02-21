package klep.wehere.socket;

import rx.Subscriber;

/**
 * Created by klep.io on 21.02.16.
 */
public abstract class HandlerSubscribe extends Subscriber {

    @Override
    public void onCompleted() {

    }

    @Override
    public abstract void onError(Throwable e);

    @Override
    public abstract void onNext(Object o);
}
