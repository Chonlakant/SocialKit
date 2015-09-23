package co.aquario.socialkit.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import co.aquario.socialkit.R;
import co.aquario.socialkit.adapater.ImageListAdapter;
import me.drakeet.materialdialog.MaterialDialog;


public class PaypalFragment extends BaseFragment {

    public static final String ARG_PAGE = "ARG_PAGE";

    public static PaypalFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PaypalFragment fragment = new PaypalFragment();
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
        final View rootView = inflater.inflate(R.layout.paypal_price, container, false);

        return rootView;
    }


}