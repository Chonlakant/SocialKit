package co.aquario.folkrice.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import co.aquario.folkrice.R;
import co.aquario.folkrice.MainApplication;
import co.aquario.folkrice.PrefManager;
import co.aquario.folkrice.adapater.ProductAdapter;
import co.aquario.folkrice.model.ProductAquery;
import co.aquario.folkrice.model.ShoppingCartHelper;


public class FragmentPayMentsDetail extends BaseFragment {

    double lat, lng;
    public static final String ARG_PAGE = "ARG_PAGE";
    Button btn_price;
    private int mPage;
    PrefManager pref;
    ProductAdapter mAdapter;
    ListView list;
    double sumAll = 0;
    double subTotal = 0;
    double subTotalAll = 0;
    double latitude;
    double longitude;
    private List<ProductAquery> mCartList = new ArrayList<>();

    TextView txtName, txtCountry, txtDistrict, txtPostal, txtHome, sumPrice, sum;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.main_list_buy, container, false);
        Toast.makeText(getActivity(), "มาแล้ว", Toast.LENGTH_SHORT).show();
        list = (ListView) rootView.findViewById(R.id.cart_items_list);


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

        sum.setText("ราคารวม:" + subTotal);
        sumPrice.setText(sumAll + "บาท");

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
        Double i = 40.00;
        // Refresh the data
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }

        int quantity = 0;
        for (ProductAquery p : mCartList) {
            quantity = ShoppingCartHelper.getProductQuantity(p);
            subTotal += p.getPrice() * quantity + i;
            sumAll += p.getPrice() * quantity;
            ShoppingCartHelper.setQuantity(p, quantity);


        }
        sum.setText("ราคารวม:" + subTotal);

        sumPrice.setText(sumAll + "บาท");

//        number_items.setText("จำนวน: " + quantity);
    }

}