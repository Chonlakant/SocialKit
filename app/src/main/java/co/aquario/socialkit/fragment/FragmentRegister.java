package co.aquario.socialkit.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import co.aquario.socialkit.MainApplication;
import co.aquario.socialkit.PrefManager;
import co.aquario.socialkit.R;
import co.aquario.socialkit.UserManager;
import co.aquario.socialkit.activity.ItemActivity;
import co.aquario.socialkit.activity.ItemListActivity;
import co.aquario.socialkit.model.Subcategory;


public class FragmentRegister extends BaseFragment  {
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String DATEPICKER_TAG = "datepicker";
    private int mPage;
    private Toolbar toolbar;
    private Button btnRegister;
    private EditText mUsername;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private UserManager mManager;
    public PrefManager prefManager;
    String username;
    String password;
    String confirmPassword;
    public static FragmentRegister newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        FragmentRegister fragment = new FragmentRegister();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        prefManager = MainApplication.getPrefManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);

        btnRegister = (Button) rootView.findViewById(R.id.btn_register);
        mUsername = (EditText) rootView.findViewById(R.id.username);
        mPassword = (EditText) rootView.findViewById(R.id.password);
        mConfirmPassword = (EditText) rootView.findViewById(R.id.confirm_password);
        mManager  = new UserManager(getActivity());

       // prefManager = MainApplication.get(getActivity()).getPrefManager();

         username = mUsername.getText().toString().trim().toLowerCase();
         password = mPassword.getText().toString();
         confirmPassword = mConfirmPassword.getText().toString();
         btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if (!prefManager.isLogin().getOr(false)){
//
//                }
//                prefManager.userName().put(username);
//                prefManager.passWord().put(password);
//                prefManager.confirmpassWord().put(confirmPassword);
//                prefManager.commit();
//            }

            }
        });
        return rootView;
    }

}