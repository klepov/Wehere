package klep.wehere.model.image;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.File;

/**
 * Created by klep.io on 28.02.16.
 */
public class CreateImage {
    public static RequestBody makeImage(String path){
        try {
            File file = new File(path);

            return RequestBody.create(MediaType.parse("multipart/form-data"), file);
        }catch (Exception e){
            return null;
        }

    }
}
