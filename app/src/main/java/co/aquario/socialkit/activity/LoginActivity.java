package co.aquario.socialkit.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
import co.aquario.socialkit.event.LoadFbProfileEvent;
import co.aquario.socialkit.event.LoginEvent;
import co.aquario.socialkit.event.LoginFailedEvent;
import co.aquario.socialkit.event.LoginSuccessEvent;
import co.aquario.socialkit.fragment.BaseFragment;
import co.aquario.socialkit.handler.ApiBus;
import co.aquario.socialkit.model.FbProfile;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.login_container, new LoginFragment(),"LOGIN").commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class LoginFragment extends BaseFragment {

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

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            aq = new AQuery(getActivity());
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
            //Snackbar.with(getActivity()).text(event.getLoginData().token).show(getActivity());
        }

        @Subscribe
        public void onLoginFailed(LoginFailedEvent event) {
            Log.e("ARAIWA","onLoginFailed");
        }

        @Produce
        public LoadFbProfileEvent produceProfileEvent() {
            return new LoadFbProfileEvent(profile);
        }
    }

    public static class ThinkingFragment extends BaseFragment {

        public ThinkingFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_thinking, container, false);
            return rootView;
        }
    }

    public static class RegisterFragment extends BaseFragment {

        public RegisterFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_register, container, false);
            return rootView;
        }
    }
}
