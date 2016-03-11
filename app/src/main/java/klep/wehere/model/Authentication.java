package klep.wehere.model;

import com.squareup.okhttp.RequestBody;

import klep.wehere.model.error.ErrorHandlerModel;
import klep.wehere.model.token.Token;
import klep.wehere.model.token.TokenTest;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;

import rx.Observable;

/**
 * Created by klep.io on 31.01.16.
 */
public interface Authentication {
    @FormUrlEncoded
    @POST("api/get-token/")
    Observable<Token> login(@Field("username")String username,
                            @Field("password")String password);

    @Multipart
    @POST("api/signup/")
    Observable<Token> registration(@Part("username")String username,
                                   @Part("password1")String password1,
                                   @Part("password2")String password2,
                                   @Part("name") String name,
                                   @Part("image\"; filename=\"image.png\" ")RequestBody photo);

    @Multipart
    @POST("api/add/child/")
    Observable<ErrorHandlerModel> registrationChild(@Header("Authorization")String token,
                                                    @Part("login_child")String username,
                                                    @Part("password1")String password1,
                                                    @Part("password2")String password2,
                                                    @Part("name_child")String name,
                                                    @Part("image\"; filename=\"image.png\" ")RequestBody photo);

}
