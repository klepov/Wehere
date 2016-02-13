package klep.wehere.common;

import com.squareup.okhttp.OkHttpClient;

import klep.wehere.utils.Const;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by klep.io on 31.01.16.
 */
public class ServiceRetrofit {
    private static OkHttpClient httpClient = new OkHttpClient();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
            .baseUrl(Const.HTTP_URL)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass){
        Retrofit retrofit = builder.client(httpClient).build();
        return retrofit.create(serviceClass);
    }
}
