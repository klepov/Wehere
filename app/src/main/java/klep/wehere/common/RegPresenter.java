package klep.wehere.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import klep.wehere.model.RegistrationCredentials;
import klep.wehere.model.image.ResizeImage;
import klep.wehere.model.image.ResizeImageImpl;
import klep.wehere.model.token.Token;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by klep.io on 14.02.16.
 */

public abstract class RegPresenter extends MvpBasePresenter<RegView> {
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
    public abstract void doReg(RegistrationCredentials credentials);

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
