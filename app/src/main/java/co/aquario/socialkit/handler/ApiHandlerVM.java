package co.aquario.socialkit.handler;

import android.content.Context;
import android.util.Log;

import com.squareup.otto.Subscribe;

import java.util.HashMap;
import java.util.Map;

import co.aquario.socialkit.event.LoginEvent;
import co.aquario.socialkit.event.LoginFailedEvent;
import co.aquario.socialkit.event.LoginSuccessEvent;
import co.aquario.socialkit.model.LoginData;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by matthewlogan on 9/3/14.
 */
public class ApiHandlerVM {

    private Context context;
    private ApiServiceVM api;
    private ApiBus apiBus;

    public ApiHandlerVM(Context context, ApiServiceVM api,
                        ApiBus apiBus) {

        this.context = context;
        this.api = api;
        this.apiBus = apiBus;
    }

    public void registerForEvents() {
        apiBus.register(this);
    }

    @Subscribe public void onLoginEvent(LoginEvent event) {
        Log.e("HEY2!","Login: " +event.getUsername() + " : " + event.getPassword());

        Map<String, String> options = new HashMap<String, String>();
        options.put("username", event.getUsername());
        options.put("password", event.getPassword());

        api.login(options, new Callback<LoginData>() {
            @Override
            public void success(LoginData loginData, Response response) {
                Log.e("loginData",loginData.apiToken);
                Log.e("response",response.getBody().toString());
                apiBus.post(new LoginSuccessEvent(loginData));

                Log.e("POSTBACK","post response back to LoginActivity");
            }

            @Override
            public void failure(RetrofitError error) {
                apiBus.post(new LoginFailedEvent());
            }
        });
    }
}
