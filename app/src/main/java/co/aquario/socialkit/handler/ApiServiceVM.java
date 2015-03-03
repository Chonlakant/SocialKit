package co.aquario.socialkit.handler;

import java.util.Map;

import co.aquario.socialkit.model.LoginData;
import co.aquario.socialkit.model.RegisterData;
import retrofit.Callback;
import retrofit.http.POST;
import retrofit.http.QueryMap;

/**
 * Created by matthewlogan on 9/3/14.
 */
public interface ApiServiceVM {
    @POST("/1.0/auth")
    public void login(@QueryMap Map<String, String> options,
                                Callback<LoginData> responseJson);

    @POST("/user/login")
    public void register(@QueryMap Map<String, String> options,
                      Callback<RegisterData> responseJson);
}
