package co.aquario.folkrice.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import co.aquario.folkrice.R;
import co.aquario.folkrice.fragment.CartFragment2;
import co.aquario.folkrice.fragment.FragmentAddressAdd;
import co.aquario.folkrice.model.Item;

public class ItemListActivitySave extends AppCompatActivity {

    Toolbar mToolbar;
    ArrayList<Item> mItems;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        mToolbar = (Toolbar) findViewById(R.id.action_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

            FragmentAddressAdd frag = new FragmentAddressAdd();
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().add(R.id.fragment_container, frag)
                    .addToBackStack(null).commit();

        // Create new Fragment

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

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        // Closes the activity if end of back stack
        if (count <= 1 ) {
            finish();
        } else {
            getFragmentManager().popBackStack();
        }

    }
}
