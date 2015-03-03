package co.aquario.socialkit;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import co.aquario.socialkit.handler.ApiBus;
import co.aquario.socialkit.handler.ApiHandlerVM;
import co.aquario.socialkit.handler.ApiServiceVM;
import co.aquario.socialkit.model.UserProfile;
import co.aquario.socialkit.util.PrefManager;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by Mac on 3/2/15.
 */
public class MainApplication extends Application {

    //private static final String ENDPOINT = "http://wallsplash.lanora.io";

    private static final String ENDPOINT = "http://api.vdomax.com";
    
    public static final String APP_ID = "1466320060320782";
    public static final String APP_NAMESPACE = "pop-rak";
    public static final String APP_PERMISSIONS = "read_stream,read_friendlists,manage_friendlists,manage_notifications,publish_stream,publish_checkins,offline_access,user_about_me,friends_about_me,user_activities,friends_activities,user_checkins,friends_checkins,user_events,friends_events,user_groups,friends_groups,user_interests,friends_interests,user_likes,friends_likes,user_notes,friends_notes,user_photos,friends_photos,user_status,friends_status,user_videos,friends_videos";

    private PrefManager prefManager;
    public static String USER_TOKEN;
    public static UserProfile user;

    private ApiHandlerVM loginApiHandler;

    public static MainApplication get(Context context) {
        return (MainApplication) context.getApplicationContext();
    }

    public PrefManager getPrefManager() {
        return prefManager;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override public void onCreate() {
        super.onCreate();
        loginApiHandler = new ApiHandlerVM(this, buildLoginApi(),
                ApiBus.getInstance());
        loginApiHandler.registerForEvents();

        prefManager = new PrefManager(getSharedPreferences("App", MODE_PRIVATE));
    }


    ApiServiceVM buildLoginApi() {

        Log.e("HEY!","called after post");

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
                .create(ApiServiceVM.class);
    }

    /*
    ApiService buildRandomUnsplashImageApi() {

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
    */



}
