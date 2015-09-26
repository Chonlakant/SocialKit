package co.aquario.socialkit.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import co.aquario.socialkit.R;
import co.aquario.socialkit.fragment.BuyItemListFragment2;
import co.aquario.socialkit.fragment.CartFragment;
import co.aquario.socialkit.fragment.CartFragment2;
import co.aquario.socialkit.model.Item;

public class ItemListActivity extends AppCompatActivity {

    Toolbar mToolbar;
    ArrayList<Item> mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        mToolbar = (Toolbar) findViewById(R.id.action_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Create new Fragment
        BuyItemListFragment2 frag = new BuyItemListFragment2();

        // Add List Item fragment to container
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.fragment_container, frag)
                .addToBackStack(null).commit();
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
            Toast.makeText(getApplicationContext(), "ไปหน้า CartFragment", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onBackPressed() {
//        int count = getSupportFragmentManager().getBackStackEntryCount();
//        // Closes the activity if end of back stack
//        if (count <= 1 ) {
//            finish();
//        } else {
//            getFragmentManager().popBackStack();
//        }
//
//    }
}
