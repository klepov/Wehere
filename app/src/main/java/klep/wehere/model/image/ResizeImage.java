package klep.wehere.model.image;

import android.graphics.Bitmap;

import rx.Observable;

/**
 * Created by klep.io on 28.02.16.
 */
public interface ResizeImage {
    Observable <Bitmap> doResize(Bitmap bitmap);
}
