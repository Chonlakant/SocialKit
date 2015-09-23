package co.aquario.socialkit;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import co.aquario.socialkit.handler.ApiBus;
import co.aquario.socialkit.handler.ApiHandler;
import co.aquario.socialkit.handler.ApiService;
import co.aquario.socialkit.model.Controller;
import co.aquario.socialkit.model.ModelCart;
import co.aquario.socialkit.model.ProductAquery;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by Mac on 3/2/15.
 */
public class MainApplication extends Application {

    private static final String ENDPOINT = "http://wallsplash.lanora.io";
    private static PrefManager prefManager;
    private ApiHandler someApiHandler;
    private ArrayList<ProductAquery> myProducts = new ArrayList<ProductAquery>();
    private ModelCart myCart = new ModelCart();
    @Override public void onCreate() {
        super.onCreate();
        someApiHandler = new ApiHandler(this, buildApi(),
                ApiBus.getInstance());
        someApiHandler.registerForEvents();
        prefManager = new PrefManager(getSharedPreferences("App", MODE_PRIVATE));

    }

    ApiService buildApi() {

        Log.e("HEY!", "after post");

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

    public static MainApplication get(Context context) {
        return (MainApplication) context.getApplicationContext();
    }

    public static PrefManager getPrefManager() {
        return prefManager;
    }
    public ProductAquery getProducts(int pPosition) {

        return myProducts.get(pPosition);
    }

    public void setProducts(ProductAquery Products) {

        myProducts.add(Products);

    }

    public ModelCart getCart() {

        return myCart;

    }

    public int getProductsArraylistSize() {

        return myProducts.size();
    }

}
