package klep.wehere.model.image;

import android.graphics.Bitmap;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by klep.io on 28.02.16.
 */
public class ResizeImageImpl implements ResizeImage {
    @Override
    public Observable<Bitmap> doResize(Bitmap bitmap) {
        return Observable.just(bitmap);
    }
}
