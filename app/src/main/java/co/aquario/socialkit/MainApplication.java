package co.aquario.socialkit;

import android.app.Application;
import android.util.Log;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.Iconics;
import com.mikepenz.meteocons_typeface_library.Meteoconcs;

import co.aquario.socialkit.handler.ApiBus;
import co.aquario.socialkit.handler.ApiHandler;
import co.aquario.socialkit.handler.ApiService;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by Mac on 3/2/15.
 */
public class MainApplication extends Application {

    private static final String ENDPOINT = "http://wallsplash.lanora.io";

    private ApiHandler someApiHandler;

    @Override public void onCreate() {
        super.onCreate();
        someApiHandler = new ApiHandler(this, buildApi(),
                ApiBus.getInstance());
        someApiHandler.registerForEvents();

        Iconics.registerFont(new Meteoconcs());
        Iconics.registerFont(new GoogleMaterial());
        //Iconics.registerFont(new CustomFont());
    }

    ApiService buildApi() {

        Log.e("HEY!","after post");

        return new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(ENDPOINT)

                .setRequestInterceptor(new RequestInterceptor() {
                    @Override public void intercept(RequestFacade request) {
                        //request.addQueryParam("p1", "var1");
                        //request.addQueryParam("p2", "");
                    }
                })

                .build()
                .create(ApiService.class);
    }

}
