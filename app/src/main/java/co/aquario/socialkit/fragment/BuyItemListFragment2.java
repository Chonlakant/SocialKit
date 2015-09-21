package co.aquario.socialkit.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.AQUtility;
import com.squareup.otto.Subscribe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import co.aquario.socialkit.R;
import co.aquario.socialkit.activity.ItemActivity;
import co.aquario.socialkit.adapater.ItemListAdapter;
import co.aquario.socialkit.adapater.ItemListAdapter2;
import co.aquario.socialkit.model.ProductAquery;
import co.aquario.socialkit.model.Subcategory;


/**
 * Created by Joseph on 7/7/15.
 */
public class BuyItemListFragment2 extends Fragment {

    public static final String EXTRA_SUBCATEGORY = "Subcategory items";
    public static final String EXTRA_NAME = "Subcategory name";
    public static final String EXTRA_USERNAME = "Username";
    public static final String EXTRA_PASSWORD = "Password";
    public static final String EXTRA_CATEGORY = "Cateogry name";

    String url2 = "http://103.253.145.83/api/v1/products";

    List<ProductAquery> Products = new ArrayList<>() ;
    ItemListAdapter2 mAdapter;
    RecyclerView mRecyclerView;
    GridLayoutManager glm;
    TextView mSortButton;
    android.support.v7.app.ActionBar mToolbar;

    public static BuyItemListFragment2 newInstance() {
        Bundle args = new Bundle();
        //args.putSerializable(EXTRA_SUBCATEGORY, s.getItems());
        //args.putString(EXTRA_NAME, s.getName());

        BuyItemListFragment2 frag = new BuyItemListFragment2();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize item list
        //mItems = DataSource.get(getActivity()).getItems();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_buy_item_list, container, false);
        AQuery aq = new AQuery(getActivity());
        mRecyclerView = (RecyclerView) v.findViewById(R.id.item_list_rv);
        // Initialize RecyclerView and adapters
        mAdapter = new ItemListAdapter2(Products, true,getActivity());
        aq.ajax(url2, JSONObject.class, this, "getjson");

        glm = new GridLayoutManager(getActivity(), 2);
        glm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(glm);
        mRecyclerView.setHasFixedSize(true);

        // Implement the sort button
        mSortButton = (TextView) v.findViewById(R.id.sort_button);
        mSortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sort the list via dialog interface
                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.sort_by)
                        .setItems(R.array.sort_options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Handle the sort
                                sortItems(which);
                                Toast.makeText(getActivity(), "Sort by " + which, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Cancel the dialog
                                dialog.cancel();
                            }
                        })
                        .create().show();
            }
        });

        mAdapter.SetOnItemClickListener(new ItemListAdapter2.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ItemActivity.class);
                intent.putExtra("title",Products.get(position).getName());
                intent.putExtra("price",Products.get(position).getPrice());
                intent.putExtra("decs",Products.get(position).getDesc());
                intent.putExtra("urlImage",Products.get(position).getImage());
                intent.putExtra("productList", Parcels.wrap(Products));
                startActivity(intent);
            }
        });
        return v;
    }

    public void getjson(String url, JSONObject jo, AjaxStatus status)
            throws JSONException {
        AQUtility.debug("jo", jo);

        if (jo != null) {
            JSONArray ja = jo.optJSONArray("products");
            for (int i = 0; i < ja.length(); i++) {
                JSONObject obj = ja.optJSONObject(i);
                Log.e("obj", obj + "");

                String name = obj.optString("name");
                Double price = obj.optDouble("price");
                String image = obj.optString("image");
                String desc = obj.optString("desc");

                ProductAquery list_item = new ProductAquery();
                list_item.setName(name);
                list_item.setPrice(price);
                list_item.setImage(image);
                list_item.setDesc(desc);
                Products.add(list_item);
            }
            mAdapter.notifyDataSetChanged();
            AQUtility.debug("done");

        } else {
            AQUtility.debug("error!");
        }
    }

    // Method to sort items TODO: Implement Sort
    private void sortItems(int which) {
        if (which == 0) { // Sort by Hot
            Collections.sort(Products, new Comparator<ProductAquery>() {
                @Override
                public int compare(ProductAquery one, ProductAquery two) {
                    return 0;
                }
            });
        } else if (which == 1) { // Sort by New
            Collections.sort(Products, new Comparator<ProductAquery>() {
                @Override
                public int compare(ProductAquery one, ProductAquery two) {
                    return 0;
                }
            });
        } else if (which == 2) { // Sort by Low Price
            Collections.sort(Products, new Comparator<ProductAquery>() {
                @Override
                public int compare(ProductAquery one, ProductAquery two) {
                    return 0;
                }
            });
        } else if (which == 3) { // Sort by High Price
            Collections.sort(Products, new Comparator<ProductAquery>() {
                @Override
                public int compare(ProductAquery one, ProductAquery two) {
                    return 0;
                }
            });
        }
    }


}
