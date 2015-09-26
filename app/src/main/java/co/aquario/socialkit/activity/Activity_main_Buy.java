package co.aquario.socialkit.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.astuetz.PagerSlidingTabStrip;

import co.aquario.socialkit.R;
import co.aquario.socialkit.adapater.PagerAdapter;
import co.aquario.socialkit.adapater.PagerAdapter2;
import co.aquario.socialkit.fragment.FragmentAddressAdd;

/**
 * Created by root1 on 9/18/15.
 */
public class Activity_main_Buy extends AppCompatActivity {
    public PagerSlidingTabStrip tabsStrip;
    Double  sumTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_test);

        sumTotal = getIntent().getDoubleExtra("sumTotal",0);
        Log.e("12345",sumTotal+"");
        FragmentAddressAdd fragment = new FragmentAddressAdd();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, fragment);
        transaction.commit();

    }
}
