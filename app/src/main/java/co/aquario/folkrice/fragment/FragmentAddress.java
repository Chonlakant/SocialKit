package co.aquario.folkrice.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import co.aquario.folkrice.R;
import co.aquario.folkrice.activity.ItemListActivitySave;


public class FragmentAddress extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    Button btnAdd;
    private int mPage;
    String name;
    RecyclerView recycler;
    public static FragmentAddress newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        FragmentAddress fragment = new FragmentAddress();
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
        View rootView = inflater.inflate(R.layout.fragment_address_add, container, false);
        recycler = (RecyclerView) rootView.findViewById(R.id.item_list_rv);
        btnAdd = (Button) rootView.findViewById(R.id.button);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), ItemListActivitySave.class);
                startActivity(i);

            }
        });
        return rootView;
    }


}