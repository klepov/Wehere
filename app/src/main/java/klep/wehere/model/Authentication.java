package klep.wehere.model;

import klep.wehere.model.error.ErrorHandlerModel;
import klep.wehere.model.token.Token;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by klep.io on 31.01.16.
 */
public interface Authentication {
    @FormUrlEncoded
    @POST("api/get-token/")
    Observable<Token> login(@Field("username")String username,
                            @Field("password")String password);
    @FormUrlEncoded
    @POST("api/signup/")
    Observable<Token> registration(@Field("username")String username,
                                        @Field("password1")String password1,
                                               @Field("password2")String password2);

    @FormUrlEncoded
    @POST("api/add/child/")
    Observable<ErrorHandlerModel> registrationChild(@Field("name_child")String username,
                                                    @Field("password1")String password1,
                                                    @Field("password2")String password2);
}
