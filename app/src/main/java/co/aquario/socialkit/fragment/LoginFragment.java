package co.aquario.socialkit.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.auth.FacebookHandle;
import com.androidquery.callback.AjaxStatus;
import com.google.gson.Gson;
import com.nispok.snackbar.Snackbar;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.otto.Produce;
import com.squareup.otto.Subscribe;

import org.json.JSONException;
import org.json.JSONObject;

import co.aquario.socialkit.MainApplication;
import co.aquario.socialkit.R;
import co.aquario.socialkit.activity.MainActivity;
import co.aquario.socialkit.event.LoadFbProfileEvent;
import co.aquario.socialkit.event.LoginEvent;
import co.aquario.socialkit.event.LoginFailedAuthEvent;
import co.aquario.socialkit.event.LoginFailedNetworkEvent;
import co.aquario.socialkit.event.LoginSuccessEvent;
import co.aquario.socialkit.event.UpdateProfileEvent;
import co.aquario.socialkit.handler.ApiBus;
import co.aquario.socialkit.model.FbProfile;
import co.aquario.socialkit.model.UserProfile;
import co.aquario.socialkit.util.PrefManager;

public class LoginFragment extends BaseFragment {

    private AQuery aq;
    private FacebookHandle handle;

    private FbProfile profile;

    public LoginFragment() {
    }

    private MaterialEditText userEt;
    private MaterialEditText passEt;

    private TextView loginBtn;
    private TextView registerBtn;

    private LinearLayout fbBtn;

    public PrefManager prefManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aq = new AQuery(getActivity());
        prefManager = MainApplication.get(getActivity()).getPrefManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        userEt = (MaterialEditText) rootView.findViewById(R.id.et_user);
        passEt = (MaterialEditText) rootView.findViewById(R.id.et_pass);

        loginBtn = (TextView) rootView.findViewById(R.id.tv_login);
        registerBtn = (TextView) rootView.findViewById(R.id.tv_reg);

        fbBtn = (LinearLayout) rootView.findViewById(R.id.btn_fb);
        fbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authFacebook();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiBus.getInstance().post(new LoginEvent(userEt.getText().toString(),
                        passEt.getText().toString()));
            }
        });

        return rootView;
    }

    public void authFacebook() {
        handle = new FacebookHandle(getActivity(), MainApplication.APP_ID, MainApplication.APP_PERMISSIONS);
        String url = "https://graph.facebook.com/me";
        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.setInverseBackgroundForced(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setTitle("Authenicating...");

        aq.auth(handle).progress(dialog)
                .ajax(url, JSONObject.class, this, "facebookCb");
    }

    public void facebookCb(String url, JSONObject jo, AjaxStatus status)
            throws JSONException {
        if (jo != null) {

            Log.e("THISJSON", jo.toString());

            Gson gson = new Gson();
            profile = gson.fromJson(jo.toString(), FbProfile.class);

            Snackbar.with(getActivity().getApplicationContext())
                    .text(profile.id)
                    .show(getActivity());

            getFragmentManager().beginTransaction().add(R.id.login_container, new ThinkingFragment()).commit();
            ApiBus.getInstance().post(produceProfileEvent());

        }
    }

    @Subscribe
    public void onLoginSuccess(LoginSuccessEvent event) {
        MainApplication.USER_TOKEN = event.getLoginData().token;
        Log.e("ARAIWA",MainApplication.USER_TOKEN);

        prefManager
                .username().put(event.getLoginData().user.username)
                .userId().put(event.getLoginData().user.id)
                .token().put(event.getLoginData().token)
                .cover().put(event.getLoginData().user.cover)
                .avatar().put(event.getLoginData().user.avatar)
                .isLogin().put(true)
                .commit();

        Snackbar.with(getActivity().getApplicationContext()).text(event.getLoginData().token).show(getActivity());

        Intent main = new Intent(getActivity(),MainActivity.class);
        startActivity(main);

        UserProfile user = event.getLoginData().user;
        ApiBus.getInstance().post(new UpdateProfileEvent(user));
    }

    @Subscribe
    public void onLoginFailedNetwork(LoginFailedNetworkEvent event) {
        Log.e("ARAIWA", "onLoginFailedNetwork");
        prefManager.clear();
        Snackbar.with(getActivity().getApplicationContext()).text("Cannot connect to server").show(getActivity());
    }

    @Subscribe
    public void onLoginFailedAuth(LoginFailedAuthEvent event) {
        Log.e("ARAIWA", "onLoginFailedAuth");
        Snackbar.with(getActivity().getApplicationContext()).text("Wrong username or password").show(getActivity());
        //prefManager.clear();
    }

    @Produce
    public LoadFbProfileEvent produceProfileEvent() {
        return new LoadFbProfileEvent(profile);
    }
}
