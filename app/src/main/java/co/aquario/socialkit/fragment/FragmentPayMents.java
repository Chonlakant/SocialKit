package co.aquario.socialkit.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

import co.aquario.socialkit.R;
import co.aquario.socialkit.model.AddAddress;


public class FragmentPayMents extends BaseFragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    Button btn_add;
    private int mPage;
    public RadioButton radio_credit;
    public RadioButton radio_paypal;
    public RadioButton radio_bank;
    Boolean isCheck = false;
    LinearLayout view_credit;
    LinearLayout view_paypla;
    LinearLayout view_bank;
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
        mPage = getArguments().getInt(ARG_PAGE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.payments, container, false);

        radio_credit = (RadioButton) rootView.findViewById(R.id.radio_credit);
        radio_paypal = (RadioButton) rootView.findViewById(R.id.radio_paypal);
        radio_bank = (RadioButton) rootView.findViewById(R.id.radio_bank);


        view_credit = (LinearLayout) rootView.findViewById(R.id.view_credit);
        view_paypla = (LinearLayout) rootView.findViewById(R.id.view_paypla);
        view_bank = (LinearLayout) rootView.findViewById(R.id.view_bank);

        radio_credit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                view_credit.setVisibility(View.VISIBLE);
            }
        });

        radio_paypal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                view_paypla.setVisibility(View.VISIBLE);
            }
        });

        radio_bank.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                view_bank.setVisibility(View.VISIBLE);
            }
        });

        return rootView;
    }


}