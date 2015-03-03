package co.aquario.socialkit.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.otto.Subscribe;

import co.aquario.socialkit.R;
import co.aquario.socialkit.event.RegisterEvent;
import co.aquario.socialkit.event.RegisterSuccessEvent;
import co.aquario.socialkit.handler.ApiBus;

public class RegisterFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private LinearLayout btnRegister;
    private String name;

    private MaterialEditText etUsername;
    private MaterialEditText etPassword;
    private MaterialEditText etRepeatPassword;
    private MaterialEditText etEmail;
    private RadioGroup radioGroupGender;
    private RadioButton radioGender;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);

        etEmail = (MaterialEditText) rootView.findViewById(R.id.et_email);
        etUsername = (MaterialEditText) rootView.findViewById(R.id.et_username);
        etPassword = (MaterialEditText) rootView.findViewById(R.id.et_password);
        etRepeatPassword = (MaterialEditText) rootView.findViewById(R.id.et_repeat_password);
        radioGroupGender = (RadioGroup) rootView.findViewById(R.id.et_gender);

        int selectedId = radioGroupGender.getCheckedRadioButtonId();
        radioGender = (RadioButton) rootView.findViewById(selectedId);

        btnRegister = (LinearLayout) rootView.findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = "";

                ApiBus.getInstance().post(new RegisterEvent(
                        name,
                                etUsername.getText().toString().trim(),
                                etPassword.getText().toString().trim(),
                                etEmail.getText().toString().trim(),
                        radioGender.getText().toString()));
            }
        });
        return rootView;
    }

    @Subscribe public void onRegisterSuccess(RegisterSuccessEvent event) {
        getFragmentManager().beginTransaction().replace(R.id.login_container, new RegisterSuccessFragment(),"REGISTER_SUCCESS").commit();
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
