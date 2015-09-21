package co.aquario.socialkit.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.astuetz.PagerSlidingTabStrip;

import co.aquario.socialkit.R;
import co.aquario.socialkit.adapater.PagerAdapter;
import co.aquario.socialkit.adapater.PagerAdapter2;

/**
 * Created by root1 on 9/18/15.
 */
public class Activity_main_Buy extends AppCompatActivity {
    public PagerSlidingTabStrip tabsStrip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        viewPager.setAdapter(new PagerAdapter2(getSupportFragmentManager()));
        // Give the PagerSlidingTabStrip the ViewPager
        tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);

    }
}
