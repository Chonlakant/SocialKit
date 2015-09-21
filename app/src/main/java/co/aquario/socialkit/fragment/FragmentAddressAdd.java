package co.aquario.socialkit.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

import co.aquario.socialkit.R;
import co.aquario.socialkit.activity.ItemListActivitySave;
import co.aquario.socialkit.model.AddAddress;


public class FragmentAddressAdd extends BaseFragment {
    MaterialEditText et_name;
    MaterialEditText et_pass;
    MaterialEditText et_contry;
    MaterialEditText et_district;
    MaterialEditText et_postal;
    MaterialEditText et_home;
    public static final String ARG_PAGE = "ARG_PAGE";
    Button btn_add;
    private int mPage;
    String name;
    String pass;
    String contry;
    String district;
    String postal;
    String home;
    List<AddAddress> list = new ArrayList<>();

    public static FragmentAddressAdd newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        FragmentAddressAdd fragment = new FragmentAddressAdd();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_address, container, false);

        et_name = (MaterialEditText) rootView.findViewById(R.id.et_name);
        et_pass = (MaterialEditText) rootView.findViewById(R.id.et_pass);
        et_contry = (MaterialEditText) rootView.findViewById(R.id.et_contry);
        et_district = (MaterialEditText) rootView.findViewById(R.id.et_district);
        et_postal = (MaterialEditText) rootView.findViewById(R.id.et_postal);
        et_home = (MaterialEditText) rootView.findViewById(R.id.et_home);

        btn_add = (Button) rootView.findViewById(R.id.btn_add);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = et_name.getText().toString();
                pass = et_pass.getText().toString();
                contry = et_contry.getText().toString();
                district = et_district.getText().toString();
                postal = et_postal.getText().toString();
                home = et_home.getText().toString();
                Log.e("name", name);


                AddAddress add = new AddAddress(name,pass,contry,district,postal,home);
                list.add(add);

                Toast.makeText(getActivity(),list.size()+"",Toast.LENGTH_SHORT).show();
            }
        });



        return rootView;
    }

}