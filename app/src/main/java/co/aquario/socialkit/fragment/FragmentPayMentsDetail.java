package co.aquario.socialkit.fragment;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;
import java.util.List;

import co.aquario.socialkit.MainApplication;
import co.aquario.socialkit.PrefManager;
import co.aquario.socialkit.R;
import co.aquario.socialkit.adapater.ProductAdapter;
import co.aquario.socialkit.model.ProductAquery;
import co.aquario.socialkit.model.ShoppingCartHelper;



public class FragmentPayMentsDetail extends BaseFragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener   {

    double lat, lng;
    public static final String ARG_PAGE = "ARG_PAGE";
    Button btn_price;
    private int mPage;
    PrefManager pref;
    ProductAdapter mAdapter;
    ListView list;
    double latitude;
    double longitude;
    private List<ProductAquery> mCartList = new ArrayList<>();

    TextView txtName, txtCountry, txtDistrict, txtPostal, txtHome,sumPrice,sum;
    private GoogleApiClient googleApiClient;
    public static FragmentPayMentsDetail newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        FragmentPayMentsDetail fragment = new FragmentPayMentsDetail();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mPage = getArguments().getInt(ARG_PAGE);
        pref = MainApplication.getPrefManager();
        Log.e("aaaaa", (pref == null) + "");
        mCartList = ShoppingCartHelper.getCartList();
    }
    GoogleMap map;
    MapView mapView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.main_list_buy, container, false);
        Toast.makeText(getActivity(), "มาแล้ว", Toast.LENGTH_SHORT).show();
        list = (ListView) rootView.findViewById(R.id.cart_items_list);



        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mAdapter = new ProductAdapter(mCartList, getActivity().getLayoutInflater(), true, getActivity());
        txtName = (TextView) rootView.findViewById(R.id.txt_name);
        txtCountry = (TextView) rootView.findViewById(R.id.txt_contry);
        txtDistrict = (TextView) rootView.findViewById(R.id.txt_district);
        txtPostal = (TextView) rootView.findViewById(R.id.txt_postal);
        txtHome = (TextView) rootView.findViewById(R.id.txt_home);
        sumPrice = (TextView) rootView.findViewById(R.id.sum_price);
        sum = (TextView) rootView.findViewById(R.id.sum);
        btn_price = (Button) rootView.findViewById(R.id.btn_price);


        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
            for (int i = 0; i < mCartList.size(); i++) {
                mCartList.get(i).selected = false;
            }
        }


        list.setAdapter(mAdapter);
            String name = pref.name().getOr("");
            String contry = pref.country().getOr("");
            String district = pref.district().getOr("");
            String postal = pref.postal().getOr("");
            String home = pref.home().getOr("");

            Log.e("222", name);
            Log.e("333", contry);
            Log.e("444", district);
            Log.e("555", postal);
            Log.e("666", home);

        txtName.setText(name);
        txtCountry.setText(contry);
        txtDistrict.setText(district);
        txtPostal.setText(postal);
        txtHome.setText(home);


        btn_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentPayMents oneFragment = new FragmentPayMents();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, oneFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

            return rootView;

        }

    @Override
    public void onResume() {
        super.onResume();



        // Refresh the data
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }

        double subTotal = 0;
        double sumAll = 0;
        int quantity = 0;
        for (ProductAquery p : mCartList) {
            quantity = ShoppingCartHelper.getProductQuantity(p);
            subTotal += p.getPrice() * quantity + 40;
            sumAll += p.getPrice() * quantity;
            ShoppingCartHelper.setQuantity(p, quantity);


        }
        sum.setText("ราคารวม:" + subTotal);


        sumPrice.setText(sumAll + "บาท");

//        number_items.setText("จำนวน: " + quantity);
    }
    @Override
    public void onStart() {
        super.onStart();

        // Connect to Google API Client
        googleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (googleApiClient != null && googleApiClient.isConnected()) {
            // Disconnect Google API Client if available and connected
            googleApiClient.disconnect();
        }
    }


    @Override
    public void onConnected(Bundle bundle) {
        LocationAvailability locationAvailability = LocationServices.FusedLocationApi.getLocationAvailability(googleApiClient);
        if(locationAvailability.isLocationAvailable()) {
            LocationRequest locationRequest = new LocationRequest()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(5000);
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        } else {
            // Do something when location provider not available
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
            Log.e("Lat",location.getLongitude()+"");
            Log.e("Long",location.getLongitude()+"");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}