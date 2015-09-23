package co.aquario.socialkit.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

import co.aquario.socialkit.R;
import co.aquario.socialkit.activity.Activity_main_Buy;
import co.aquario.socialkit.adapater.ImageListAdapter;
import co.aquario.socialkit.model.AddAddress;
import me.drakeet.materialdialog.MaterialDialog;


public class FragmentPayMents extends BaseFragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    Button btn_add;
    private int mPage;
    ImageListAdapter imageListAdapter;
    ListView listView;
    MaterialDialog mMaterialDialog;
    public static String[] eatFoodyImages = {
            "http://tech.thaivisa.com/wp-content/uploads/2015/07/paypal.jpg",
            "http://www.thaidnsservice.com/wp-content/uploads/2015/07/kbang.jpg",
            "http://buzzinmediagroup.com/wp-content/uploads/2015/07/SCB_logo.jpg"
    };
    String[] title = {"PayPal","ธนาคารกสิกรไทย","ธนาคารไทยพาณิชย์"};

    public static FragmentPayMents newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        FragmentPayMents fragment = new FragmentPayMents();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // mPage = getArguments().getInt(ARG_PAGE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.payments, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView);
        mMaterialDialog = new MaterialDialog(getActivity());
        imageListAdapter = new ImageListAdapter(getActivity(),eatFoodyImages,title);
        listView.setAdapter(imageListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    PaypalFragment oneFragment = new PaypalFragment();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container, oneFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                if (i == 1) {
                    if (mMaterialDialog != null) {
                        mMaterialDialog.setTitle("การชำระเงิน")
                                .setMessage(
                                        "โอนเงิน เข้าบัญชี ธนสคารกสิกรไทย(KBANK) นายอนุกูล ทรายเพชร และ นาย ธนานนท์ เงินถาวร 730-2-12382-5 เซนทรัลลาดพร้าวแจ้งโอนเงินมาที่ Folkrice.th@gmail.com หรือ Line FolkRice"
                                )
                                        //mMaterialDialog.setBackgroundResource(R.drawable.background);
                                .setPositiveButton(
                                        "OK", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                mMaterialDialog.dismiss();


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
                    }
                }
                if (i == 2) {
                    if (mMaterialDialog != null) {
                        mMaterialDialog.setTitle("การชำระเงิน")
                                .setMessage(
                                        "โอนเงิน เข้าบัญชี ธนสคารกสิกรไทย(KBANK) นายอนุกูล ทรายเพชร และ นาย ธนานนท์ เงินถาวร 730-2-12382-5 เซนทรัลลาดพร้าวแจ้งโอนเงินมาที่ Folkrice.th@gmail.com หรือ Line FolkRice"
                                )
                                        //mMaterialDialog.setBackgroundResource(R.drawable.background);
                                .setPositiveButton(
                                        "OK", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                mMaterialDialog.dismiss();


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
                    }
                }
            }
        });
        return rootView;
    }


}