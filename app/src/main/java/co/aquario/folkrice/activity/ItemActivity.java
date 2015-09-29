package co.aquario.folkrice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.parceler.Parcels;

import java.util.ArrayList;

import co.aquario.folkrice.R;
import co.aquario.folkrice.MainApplication;
import co.aquario.folkrice.fragment.CartFragment2;
import co.aquario.folkrice.fragment.ItemFragment;
import co.aquario.folkrice.model.ProductAquery;
import co.aquario.folkrice.model.ShoppingCartHelper;


/**
 * Created by Joseph on 7/7/15.
 */
public class ItemActivity extends AppCompatActivity {

    Toolbar mToolbar;
    ArrayList<ProductAquery> person;
    TextView countTv;
    TextView mBuyButton;

    String title;
    String decs;
    Double price;
    String urlImage;
    int ProductsSize;
    ProductAquery list;
    MainApplication aController;
     EditText editTextQuantity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_viewpager);

        editTextQuantity = (EditText) findViewById(R.id.editTextQuantity);
        aController = (MainApplication) getApplicationContext();


        //editTextQuantity.setInputType(InputType.TYPE_NULL);



        // Get the Item extras
        Bundle extras = getIntent().getExtras();
        title = extras.getString("title");
        price = extras.getDouble("price");
        decs = extras.getString("decs");
        urlImage = extras.getString("urlImage");
//        String itemName = extras.getString(ItemFragment.EXTRA_NAME);
//        curItem = DataSource.get(this).getItem(itemName);

         list = new ProductAquery();
        list.setName(title);
        list.setPrice(price);
        list.setImage(urlImage);


        aController.setProducts(list);


        ProductsSize = aController.getProductsArraylistSize();

        Log.e("ProductsSize", ProductsSize + "");

        Intent intent = getIntent();
        person = Parcels.unwrap(intent.getParcelableExtra("productList"));

        Log.e("1111", person.get(0).getName());
        // Inflate the ItemFragment

        ItemFragment frag = ItemFragment.newInstance(title, price, decs, urlImage);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.item_container, frag).commit();

        // Initialize and set up the toolbar
        mToolbar = (Toolbar) findViewById(R.id.action_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



//             for(int i = 0 ; i < count ; count++){
//                 final int index = i;
//                 ProductAquery tempProductObject = aController.getProducts(index);
//                 if(!aController.getCart().checkProductInCart(tempProductObject)){
//                     aController.getCart().setProducts(tempProductObject);
//                 }
//             }





        // Set the buy button
        mBuyButton = (TextView) findViewById(R.id.buy_button);
        mBuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int quantity = 0;
                try {
                    quantity = Integer.parseInt(editTextQuantity.getText()
                            .toString());

                    if (quantity < 0) {
                        Toast.makeText(getBaseContext(),
                                "Please enter a quantity of 0 or higher",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                } catch (Exception e) {
                    Toast.makeText(getBaseContext(),
                            "Please enter a numeric quantity",
                            Toast.LENGTH_SHORT).show();

                    return;
                }

                // If we make it here, a valid quantity was entered
                ShoppingCartHelper.setQuantity(list, quantity);

                // Close the activity
                finish();

//                // Open bought dialog
//                AlertDialog.Builder builder = new AlertDialog.Builder(ItemActivity.this);
//                builder.setTitle(R.string.add_cart)
//                        .setMessage(title + " has been added to your cart.")
//                        .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                // Close the dialog window
//                                for (int j = 0; j < ProductsSize; j++) {
//                                    final int index = j;
//                                    //Clicked button index
//                                    Log.e("index :", "" + index);
//                                    // Get product instance for index
//                                    ProductAquery tempProductObject = aController.getProducts(index);
//
//                                    //Check Product already exist in Cart or Not
//                                    if (!aController.getCart().checkProductInCart(tempProductObject)) {
//                                        aController.getCart().setProducts(tempProductObject);
//
//                                        Toast.makeText(getApplicationContext(), "Now Cart size: " + aController.getCart().getCartSize(), Toast.LENGTH_LONG).show();
//                                    } else {
//
//                                        // Cart product arraylist contains Product
//                                        Toast.makeText(getApplicationContext(), "Product " + (index + 1) + " already added in cart.", Toast.LENGTH_LONG).show();
//                                    }
//
//                                }
//
//                                dialog.dismiss();
//                            }
//                        })
//                        .setPositiveButton(R.string.show_cart, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                // Dismiss dialog and open cart
//
//                                dialog.dismiss();
//                                CartFragment2 cartFrag = new CartFragment2();
//                                cartFrag.show(getSupportFragmentManager(), "My Cart");
//                            }
//                        }).create().show();
            }
        });
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
}
