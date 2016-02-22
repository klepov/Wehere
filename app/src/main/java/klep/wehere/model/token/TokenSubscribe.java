package klep.wehere.model.token;

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
        int tokenCount = (int) Token.count(Token.class,null,null);
        if (tokenCount > 0){
            Token.deleteAll(Token.class);
        }
        new Token(token.getToken()).save();
    }
}
