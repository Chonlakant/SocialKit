package co.aquario.socialkit.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.ksoichiro.android.observablescrollview.ObservableGridView;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import co.aquario.socialkit.MainApplication;
import co.aquario.socialkit.R;
import co.aquario.socialkit.activity.LoginActivity;
import co.aquario.socialkit.adapter.FriendAdapter;
import co.aquario.socialkit.adapter.FriendRecyclerAdapter;
import co.aquario.socialkit.event.LoadFriendListEvent;
import co.aquario.socialkit.event.LoadFriendListSuccessEvent;
import co.aquario.socialkit.event.LogoutEvent;
import co.aquario.socialkit.handler.ApiBus;
import co.aquario.socialkit.model.User;
import co.aquario.socialkit.widget.EndlessRecyclerOnScrollListener;

/**
 * Created by Mac on 3/10/15.
 */
public class FriendFragment extends BaseFragment {
    private static final String LOAD_TYPE = "TYPE";
    private static final String USER_ID = "USER_ID";

    private String type = "";
    private String userId = "";

    ArrayList<User> list = new ArrayList<>();
    FriendAdapter adapter;
    FriendRecyclerAdapter adapter2;
    ObservableGridView gridView;
    RecyclerView recyclerView;
    boolean refresh = false;

    public static FriendFragment newInstance(String text,String userId){
        FriendFragment mFragment = new FriendFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString(LOAD_TYPE, text);
        mBundle.putString(USER_ID,userId);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefManager = MainApplication.get(getActivity()).getPrefManager();
        if (getArguments() != null) {
            type = getArguments().getString(LOAD_TYPE);
            userId = getArguments().getString(USER_ID);
        }
        if(!type.equals("")) {
            if(userId.equals(""))
                userId = prefManager.userId().getOr("1301");
            ApiBus.getInstance().post(new LoadFriendListEvent(type,Integer.parseInt(userId),1,15));
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recyclerview_autofit, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        adapter2 = new FriendRecyclerAdapter(getActivity(),list);
        recyclerView.setAdapter(adapter2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                Log.e("scrollBottom","laew na");
                refresh = true;
                ApiBus.getInstance().post(new LoadFriendListEvent(type,Integer.parseInt(userId),current_page,15));
            }
        });




        /*
        //gridView = (ObservableGridView) rootView.findViewById(R.id.scroll);
        adapter = new FriendAdapter(getActivity(), list);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), SlidingUpRecyclerViewActivity.class);
                i.putExtra("userId",list.get(position).id);
                i.putExtra("avatar",list.get(position).getAvatarUrl());
                i.putExtra("cover",list.get(position).getCoverUrl());
                i.putExtra("name",list.get(position).name);
                i.putExtra("username",list.get(position).username);
                getActivity().startActivity(i);
            }
        });
        */

        return rootView;
    }

    /**
     * Save Fragment's State here
     */
    @Override
    protected void onSaveState(Bundle outState) {
        super.onSaveState(outState);
        // For example:
        //outState.putString("text", tvSample.getText().toString());
    }

    /**
     * Restore Fragment's State here
     */
    @Override
    protected void onRestoreState(Bundle savedInstanceState) {
        super.onRestoreState(savedInstanceState);
        // For example:
        //tvSample.setText(savedInstanceState.getString("text"));
    }

    @Subscribe public void onLoadFriendListSuccess(LoadFriendListSuccessEvent event) {
        Log.e("MYTYPE",type);
        if(event.getType().equals(type)) {
            if(refresh)
                list.clear();
            list.addAll(event.getFriendListData().users);
            adapter2.updateList(list);
        }
    }

    @Subscribe public void onLogout(LogoutEvent event) {
        MainApplication.logout();
        Intent login = new Intent(getActivity(), LoginActivity.class);
        startActivity(login);
        getActivity().finish();
    }
}