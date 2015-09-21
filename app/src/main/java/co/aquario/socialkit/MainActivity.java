package co.aquario.socialkit;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;

import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.materialdrawer.model.interfaces.OnCheckedChangeListener;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import co.aquario.socialkit.adapater.FragmentAdapter;
import co.aquario.socialkit.adapater.TabListener;
import co.aquario.socialkit.custom.CustomPrimaryDrawerItem;
import co.aquario.socialkit.event.FailedEvent;
import co.aquario.socialkit.event.SomeEvent;
import co.aquario.socialkit.event.SuccessEvent;
import co.aquario.socialkit.fragment.BaseFragment;
import co.aquario.socialkit.fragment.CartFragment;
import co.aquario.socialkit.fragment.CartFragment2;
import co.aquario.socialkit.handler.ApiBus;
import co.aquario.socialkit.model.SomeData;


public class MainActivity extends AppCompatActivity {

    private static final int PROFILE_SETTING = 1;

    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer result = null;
    private Toolbar toolbar;
    private IProfile profile;

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
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
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
