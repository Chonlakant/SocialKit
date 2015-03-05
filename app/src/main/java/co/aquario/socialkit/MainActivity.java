package co.aquario.socialkit;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.nispok.snackbar.Snackbar;

import net.yanzm.mth.MaterialTabHost;

import co.aquario.socialkit.fragment.ArtistPagerAdapter;
import co.aquario.socialkit.fragment.ExplorePagerAdapter;
import co.aquario.socialkit.fragment.MyMusicPagerAdapter;
import co.aquario.socialkit.fragment.NowFragment;
import co.aquario.socialkit.fragment.PopularPagerAdapter;
import co.aquario.socialkit.fragment.WebviewFragment;
import co.aquario.socialkit.handler.ApiBus;


public class MainActivity extends ActionBarActivity {

    private Drawer.Result result = null;
    private Drawer drawer = null;

    AccountHeader.Result headerResult = null;

    LinearLayout view;

    private Context context;
    private Activity activity;

    PopularPagerAdapter popularPagerAdapter;
    ExplorePagerAdapter explorePagerAdapter;
    ArtistPagerAdapter artistPagerAdapter;
    MyMusicPagerAdapter myMusicPagerAdapter;
    ViewPager viewPager;
    MaterialTabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        activity = this;

        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabHost = (MaterialTabHost) findViewById(android.R.id.tabhost);
        tabHost.setType(MaterialTabHost.Type.FullScreenWidth);

        popularPagerAdapter = new PopularPagerAdapter(getSupportFragmentManager());
        for (int i = 0; i < popularPagerAdapter.getCount(); i++) {
            tabHost.addTab(popularPagerAdapter.getPageTitle(i));
        }




        setTitle("Popular");

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(popularPagerAdapter);
        viewPager.setOnPageChangeListener(tabHost);

        tabHost.setOnTabChangeListener(new MaterialTabHost.OnTabChangeListener() {
            @Override
            public void onTabSelected(int position) {

                viewPager.setCurrentItem(position);
            }
        });

        IProfile profile = new ProfileDrawerItem().withEmail("My Account").withName("Jonathan Odds").withIcon(getResources().getDrawable(R.drawable.profile)).withTextColorRes(R.color.material_drawer_primary);



        headerResult = new AccountHeader()
                .withActivity(this)
                //.withCompactStyle(true)
                .withHeaderBackground(R.drawable.coding)

                .addProfiles(
                        profile,
                        new ProfileSettingDrawerItem().withEmail("Favorite Music").withIcon(GoogleMaterial.Icon.gmd_3d_rotation),
                        new ProfileSettingDrawerItem().withEmail("Playlists").withIcon(GoogleMaterial.Icon.gmd_settings))

                .withOnAccountHeaderClickListener(new AccountHeader.OnAccountHeaderClickListener() {
                    @Override
                    public void onProfileClick(View view, IProfile profile) {
                        Toast.makeText(MainActivity.this, profile.getName(), Toast.LENGTH_SHORT).show();

                        setTitle("My Account");
                        viewPager.setVisibility(View.GONE);
                        tabHost.setVisibility(View.GONE);

                    }

                    @Override
                    public void onSelectionClick(IProfile currentProfile) {
                        if (!TextUtils.isEmpty(currentProfile.getName())) {
                            Toast.makeText(MainActivity.this, currentProfile.getName(), Toast.LENGTH_SHORT).show();
                        }

                        setTitle("My Music");
                        myMusicPagerAdapter = new MyMusicPagerAdapter(getSupportFragmentManager());
                        tabHost.getTabWidget().removeAllViewsInLayout();

                        for (int i = 0; i < myMusicPagerAdapter.getCount(); i++) {
                            tabHost.addTab(myMusicPagerAdapter.getPageTitle(i));

                        }

                        viewPager = (ViewPager) findViewById(R.id.pager);
                        viewPager.setAdapter(myMusicPagerAdapter);

                        viewPager.setVisibility(View.VISIBLE);
                        tabHost.setVisibility(View.VISIBLE);




                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();

        //new Drawer().withActivity(this).build();
        result =  new Drawer().withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .withActionBarDrawerToggle(true)
                .withTranslucentStatusBar(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Popular").withIcon(FontAwesome.Icon.faw_asterisk),
                        new PrimaryDrawerItem().withName("Explore").withIcon(FontAwesome.Icon.faw_search),
                        new PrimaryDrawerItem().withName("Browse Artists").withIcon(FontAwesome.Icon.faw_headphones),
                        new SectionDrawerItem().withName("Menu"),
                        new SecondaryDrawerItem().withName("Blog"),
                        new SecondaryDrawerItem().withName("About us")

                )

                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {


                        switch (position) {
                            case 0:
                                break;
                            case 1:
                                setTitle("Popular");
                                popularPagerAdapter = new PopularPagerAdapter(getSupportFragmentManager());
                                tabHost.getTabWidget().removeAllViewsInLayout();

                                for (int i = 0; i < popularPagerAdapter.getCount(); i++) {
                                    tabHost.addTab(popularPagerAdapter.getPageTitle(i));

                                }

                                viewPager = (ViewPager) findViewById(R.id.pager);
                                viewPager.setAdapter(popularPagerAdapter);

                                viewPager.setVisibility(View.VISIBLE);
                                tabHost.setVisibility(View.VISIBLE);

                                break;
                            case 2:
                                setTitle("Explore");
                                explorePagerAdapter = new ExplorePagerAdapter(getSupportFragmentManager());
                                tabHost.getTabWidget().removeAllViewsInLayout();
                                for (int i = 0; i < explorePagerAdapter.getCount(); i++) {
                                    tabHost.addTab(explorePagerAdapter.getPageTitle(i));

                                }

                                viewPager = (ViewPager) findViewById(R.id.pager);
                                viewPager.setAdapter(explorePagerAdapter);

                                viewPager.setVisibility(View.VISIBLE);
                                tabHost.setVisibility(View.VISIBLE);
                                break;
                            case 3:
                                setTitle("Browse Artists");
                                artistPagerAdapter = new ArtistPagerAdapter(getSupportFragmentManager());
                                tabHost.getTabWidget().removeAllViewsInLayout();
                                for (int i = 0; i < artistPagerAdapter.getCount(); i++) {
                                    tabHost.addTab(artistPagerAdapter.getPageTitle(i));
                                }

                                viewPager = (ViewPager) findViewById(R.id.pager);
                                viewPager.setAdapter(artistPagerAdapter);

                                viewPager.setVisibility(View.VISIBLE);
                                tabHost.setVisibility(View.VISIBLE);
                                break;
                            case 5:
                                setTitle("Blog");
                                viewPager.setVisibility(View.GONE);
                                tabHost.setVisibility(View.GONE);
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.container, new WebviewFragment())
                                        .commit();
                                break;
                            default:
                                break;
                        }
                        if (drawerItem instanceof Nameable) {
                            Snackbar.with(getApplicationContext()).text(((Nameable) drawerItem).getName() + " at " + position).show(activity);
                        }
                    }
                })
                .build();


        /*
        .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTitle("My Music");
                myMusicPagerAdapter = new MyMusicPagerAdapter(getSupportFragmentManager());
                tabHost.getTabWidget().removeAllViewsInLayout();

                for (int i = 0; i < myMusicPagerAdapter.getCount(); i++) {
                    tabHost.addTab(myMusicPagerAdapter.getPageTitle(i));

                }

                viewPager = (ViewPager) findViewById(R.id.pager);
                viewPager.setAdapter(myMusicPagerAdapter);

                viewPager.setVisibility(View.VISIBLE);
                tabHost.setVisibility(View.VISIBLE);
            }
        });
        */

       // new Drawer().withActivity(this).withHeader(result.getHeader()).withHeaderClickable(true).withDrawerGravity(Gravity.END).append(result);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new NowFragment())
                    .commit();

            //Snackbar.with(this).text("before post").show(this);
            //ApiBus.getInstance().post(new SomeEvent("var1",
              //      2));
        }
    }

    @Override public void onResume() {
        super.onResume();
        ApiBus.getInstance().register(this);
    }

    @Override public void onPause() {
        super.onPause();
        ApiBus.getInstance().unregister(this);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

}
