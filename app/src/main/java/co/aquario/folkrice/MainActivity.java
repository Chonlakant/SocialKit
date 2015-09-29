package co.aquario.folkrice;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import co.aquario.folkrice.adapater.FragmentAdapter;
import co.aquario.folkrice.adapater.TabListener;
import co.aquario.folkrice.event.SomeEvent;
import co.aquario.folkrice.fragment.CartFragment2;
import co.aquario.folkrice.handler.ApiBus;


public class MainActivity extends AppCompatActivity {

    private static final int PROFILE_SETTING = 1;


    private Toolbar toolbar;


    Bundle savedState;
    TabListener mTabListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedState = savedInstanceState;
        setContentView(R.layout.activity_main);
        initToolbar();

        if (savedInstanceState == null) {

            ApiBus.getInstance().post(new SomeEvent("var1",
                    2));
        }

        mTabListener = new TabListener(getSupportFragmentManager());

        // Initialize the TabLayout
        TabLayout tabs = (TabLayout) findViewById(R.id.bottom_tabs);
        tabs.setTabsFromPagerAdapter(new FragmentAdapter(getSupportFragmentManager(), this));

        // Set up the tab listener
        tabs.setOnTabSelectedListener(mTabListener);

    }


    public void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
//        if (result != null && result.isDrawerOpen()) {
//            result.closeDrawer();
//        } else {
//            super.onBackPressed();
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ApiBus.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        ApiBus.getInstance().unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.cart) { // Show the cart
            CartFragment2 cartFrag = new CartFragment2();
            cartFrag.show(getSupportFragmentManager(), "My Cart");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Pops the fragment at the top of the stack
        getSupportFragmentManager().popBackStack();
        return true;
    }


}
