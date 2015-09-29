package co.aquario.folkrice.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.aquario.folkrice.R;
import co.aquario.folkrice.MainApplication;
import co.aquario.folkrice.PrefManager;
import co.aquario.folkrice.model.AddAddress;


public class FragmentRegisterOne extends Fragment {
    EditText et_mail;
    EditText et_password;
    EditText et_confirmPassword;

    public static final String ARG_PAGE = "ARG_PAGE";
    Button btn_add;
//    private int mPage;
    String name;
    String pass;
    String confirmpassWord;

    List<AddAddress> list = new ArrayList<>();
    public static FragmentRegisterOne newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        FragmentRegisterOne fragment = new FragmentRegisterOne();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = MainApplication.getPrefManager();
        Log.e("aaaaa",(pref == null) + "" );
      //  mPage = getArguments().getInt(ARG_PAGE);

    }

    PrefManager pref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.register_one, container, false);

//        String password = pref.passWord().getOr("");
//        String username = pref.userName().getOr("");
        //toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        et_mail = (EditText) rootView.findViewById(R.id.email);
        et_mail.setHint("อีเมล์");
        et_password = (EditText) rootView.findViewById(R.id.password);
        et_password.setHint("รหัสผ่าน");
        et_confirmPassword = (EditText) rootView.findViewById(R.id.confirm_password);
        et_confirmPassword.setHint("ยืนยันรหัสผ่าน");

//        if (toolbar != null)
//            getActivity().setTitle("ที่อยู่จัดส่ง");

      //  toolbar.setTitle("ที่อยู่จัดส่ง");
        btn_add = (Button) rootView.findViewById(R.id.btn_add);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = et_mail.getText().toString();
                pass = et_password.getText().toString();
                confirmpassWord = et_confirmPassword.getText().toString();

                pref.userName().put(name);
                pref.passWord().put(pass);
                pref.confirmpassWord().put(confirmpassWord);
                pref.commit();

                FragmentRegisterTwo oneFragment = new FragmentRegisterTwo();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, oneFragment);
                transaction.addToBackStack(null);
                transaction.commit();

                Toast.makeText(getActivity(), list.size() + "", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        if(pref.isAddress().getOr(false)){
            et_mail.setText(pref.userName().getOr(""));
            et_password.setText(pref.passWord().getOr(""));
            et_confirmPassword.setText(pref.confirmpassWord().getOr(""));


        }
    }
}