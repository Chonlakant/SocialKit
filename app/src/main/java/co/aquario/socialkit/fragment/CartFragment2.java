package co.aquario.socialkit.fragment;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import java.util.ArrayList;
import java.util.List;

import co.aquario.socialkit.R;
import co.aquario.socialkit.UserManager;
import co.aquario.socialkit.activity.Activity_main_Buy;
import co.aquario.socialkit.activity.Activity_main_login;
import co.aquario.socialkit.activity.ItemListActivitySave;
import co.aquario.socialkit.adapater.ItemListAdapter2;
import co.aquario.socialkit.adapater.ProductAdapter;
import co.aquario.socialkit.model.ProductAquery;
import co.aquario.socialkit.model.ShoppingCartHelper;
import co.aquario.socialkit.model.Storage;
import me.drakeet.materialdialog.MaterialDialog;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class CartFragment2 extends DialogFragment  {
    private static final int REQUEST_SIMPLE_DIALOG = 42;
    private List<ProductAquery> mCartList = new ArrayList<>();
    ProductAdapter mAdapter;
    TextView productPriceTextView, number_items;
    ListView list;
    ProductAquery curProduct;
    MaterialDialog mMaterialDialog;
    UserManager mManager;
    double subTotal = 0 ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize items
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Remove the title
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cart, container, false);
        mMaterialDialog = new MaterialDialog(getActivity());
        mManager  = new UserManager(getActivity());

        mCartList = ShoppingCartHelper.getCartList();
        productPriceTextView = (TextView) v.findViewById(R.id.textView2);
        number_items = (TextView) v.findViewById(R.id.number_items);
//        Log.e("",mCartList.get(0).getTitle());

        // Make sure to clear the selections

        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
            for (int i = 0; i < mCartList.size(); i++) {
                mCartList.get(i).selected = false;
            }
        }



        // Create the list
        list = (ListView) v.findViewById(R.id.cart_items_list);
        mAdapter = new ProductAdapter(mCartList, getActivity().getLayoutInflater(), true, getActivity());
        list.setAdapter(mAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "Hello", Toast.LENGTH_SHORT).show();
                removeItemFromList(i);
            }
        });

        // Cancel button
        ImageView cancelButton = (ImageView) v.findViewById(R.id.x_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                getDialog().dismiss();
            }
        });

        // Set Paypal buttons
        RelativeLayout paypalButton = (RelativeLayout) v.findViewById(R.id.paypal_button);
        paypalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open paypal
                getDialog().dismiss();
            }
        });

        // Set Normal Checkout Button
        TextView checkoutButton = (TextView) v.findViewById(R.id.checkout_button);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (mMaterialDialog != null) {
                    mMaterialDialog.setTitle("เข้าสู่ระบบเพื่อดำเนินการต่อ")
                            .setMessage(
                                    "กรุณาเข้าสู่ระบบ หรือสมัคร folkrice เพื่อดำเนินการต่อไป"
                            )
                                    //mMaterialDialog.setBackgroundResource(R.drawable.background);
                            .setPositiveButton(
                                    "OK", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            mMaterialDialog.dismiss();
                                            Toast.makeText(getActivity(), "ตกลง", Toast.LENGTH_LONG).show();
                                            Intent i =new Intent(getActivity(), Activity_main_Buy.class);
                                            i.putExtra("sumTotal",subTotal);
                                            startActivity(i);

                                        }
                                    }
                            )
                            .setNegativeButton(
                                    "CANCLE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            mMaterialDialog.dismiss();
                                            Toast.makeText(getActivity(), "ยกเลิก", Toast.LENGTH_LONG).show();
                                        }
                                    }
                            )
                            .setCanceledOnTouchOutside(false)
                                    // You can change the message anytime.
                                    // mMaterialDialog.setTitle("提示");
                            .setOnDismissListener(
                                    new DialogInterface.OnDismissListener() {
                                        @Override
                                        public void onDismiss(DialogInterface dialog) {
//                                            Toast.makeText(getActivity(), "onDismiss", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                            )
                            .show();
                    // You can change the message anytime.
                    // mMaterialDialog.setMessage("嗨！这是一个 MaterialDialog. 它非常方便使用，你只需将它实例化，这个美观的对话框便会自动地显示出来。它简洁小巧，完全遵照 Google 2014 年发布的 Material Design 风格，希望你能喜欢它！^ ^");
                } else {
                    Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return v;
    }

    protected void removeItemFromList(int position) {
        final int deletePosition = position;

        AlertDialog.Builder alert = new AlertDialog.Builder(
                getActivity());

        alert.setTitle("ลบ");
        alert.setMessage("คุณต้องการลบสินค้านี้หรือไม่?");
        alert.setPositiveButton("ตกลง", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TOD O Auto-generated method stub

                // main code on after clicking yes
                mCartList.remove(deletePosition);
                mAdapter.notifyDataSetChanged();
                mAdapter.notifyDataSetInvalidated();
                // Refresh the data
                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }


                int quantity = 0;
                for (ProductAquery p : mCartList) {
                    quantity = ShoppingCartHelper.getProductQuantity(p);
                    subTotal += p.getPrice() * quantity;

                    ShoppingCartHelper.setQuantity(p, quantity);


                }
                productPriceTextView.setText("ราคารวม:" + subTotal);
                number_items.setText("จำนวน: " + quantity);

            }
        });
        alert.setNegativeButton("ยกเลิก", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        alert.show();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Make Dialog fill parent width and height
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void onResume() {
        super.onResume();



        // Refresh the data
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }

        int quantity = 0;
        for (ProductAquery p : mCartList) {
            quantity = ShoppingCartHelper.getProductQuantity(p);
            subTotal += p.getPrice() * quantity;

            ShoppingCartHelper.setQuantity(p, quantity);


        }
        productPriceTextView.setText("ราคารวม:" + subTotal);
        number_items.setText("จำนวน: " + quantity);
    }


}
