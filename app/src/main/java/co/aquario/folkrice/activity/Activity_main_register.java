package co.aquario.folkrice.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;

import co.aquario.folkrice.R;
import co.aquario.folkrice.PrefManager;
import co.aquario.folkrice.fragment.FragmentRegisterOne;

/**
 * Created by root1 on 9/18/15.
 */
public class Activity_main_register extends AppCompatActivity {
    Boolean status = false;
    RadioButton radioButton1,radioButton2,radioButton3;
    PrefManager pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_register);


        FragmentRegisterOne fragment = new FragmentRegisterOne();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, fragment);
        transaction.commit();

    }
}
