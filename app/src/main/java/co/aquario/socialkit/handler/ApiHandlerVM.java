package co.aquario.socialkit.handler;

import android.content.Context;
import android.util.Log;

import com.squareup.otto.Subscribe;

import java.util.HashMap;
import java.util.Map;

import co.aquario.socialkit.event.FailedNetworkEvent;
import co.aquario.socialkit.event.FbAuthEvent;
import co.aquario.socialkit.event.LoginEvent;
import co.aquario.socialkit.event.LoginFailedAuthEvent;
import co.aquario.socialkit.event.LoginSuccessEvent;
import co.aquario.socialkit.event.RegisterEvent;
import co.aquario.socialkit.event.RegisterFailedEvent;
import co.aquario.socialkit.event.RegisterSuccessEvent;
import co.aquario.socialkit.event.RequestOtpEvent;
import co.aquario.socialkit.model.LoginData;
import co.aquario.socialkit.model.RegisterData;
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
                //Log.e("loginData",loginData.apiToken);
                Log.e("response",response.getBody().toString());

                if(loginData.status.equals("1"))
                    apiBus.post(new LoginSuccessEvent(loginData));
                else
                    apiBus.post(new LoginFailedAuthEvent());

                Log.e("POSTBACK","post response back to LoginFragment");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("response",error.getBody().toString());
                apiBus.post(new FailedNetworkEvent());
            }
        });
    }

    @Subscribe public void onFbLoginEvent(FbAuthEvent event) {

        Map<String, String> options = new HashMap<String, String>();
        options.put("access_token", event.getFbToken());

        api.fbLogin(options, new Callback<LoginData>() {
            @Override
            public void success(LoginData loginData, Response response) {
                //Log.e("loginData",loginData.apiToken);
                Log.e("response",response.getBody().toString());

                if(loginData.status.equals("1"))
                    apiBus.post(new LoginSuccessEvent(loginData));
                else
                    apiBus.post(new LoginFailedAuthEvent());

                Log.e("POSTBACK","post response back to LoginFragment");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("response",error.getBody().toString());
                apiBus.post(new FailedNetworkEvent());
            }
        });
    }

    @Subscribe public void onRegisterEvent(RegisterEvent event) {
        Map<String, String> options = new HashMap<String, String>();

        options.put("name", event.getName());
        options.put("username", event.getUsername());
        options.put("password", event.getPassword());
        options.put("email", event.getEmail());
        options.put("gender", event.getGender());

        api.register(options, new Callback<RegisterData>() {
            @Override
            public void success(RegisterData registerData, Response response) {
                //Log.e("loginData",loginData.apiToken);
                Log.e("response", response.getBody().mimeType());

                if (registerData.status.equals("1")) {
                    apiBus.post(new RegisterSuccessEvent(registerData));
                }
                else {
                    apiBus.post(new RegisterFailedEvent(registerData.message));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("response", error.getBody().toString());
                apiBus.post(new FailedNetworkEvent());
            }
        });
    }

    @Subscribe public void onRequestOtp(RequestOtpEvent event) {
        Map<String, String> options = new HashMap<String, String>();

        options.put("mobile", event.getMobile());
        options.put("message", event.getMessage());

        api.otp(options);
    }
}
