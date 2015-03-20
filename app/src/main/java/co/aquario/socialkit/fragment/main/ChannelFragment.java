package co.aquario.socialkit.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.AQUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import co.aquario.socialkit.R;
import co.aquario.socialkit.activity.SlidingUpRecyclerViewActivity;
import co.aquario.socialkit.adapter.ChannelAdapter;
import co.aquario.socialkit.fragment.BaseFragment;
import co.aquario.socialkit.model.Channel;
import co.aquario.socialkit.widget.EndlessListOnScrollListener;


public class ChannelFragment extends BaseFragment
        {
    String channelUrl = "http://api.vdomax.com/search/channel/a+?from=0&limit=20";
    String liveChannelUrl = "https://www.vdomax.com/ajax.php?t=getLiveChannel&user=";

    ArrayList<Channel> list = new ArrayList<Channel>();
    ChannelAdapter channelAdapter;
    GridView mGridView;
    public AQuery aq;
    public SwipeRefreshLayout swipeLayout;

    private static final String USER_ID = "USER_ID";
    public String userId = null;

    ChannelFragment fragment;
    char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    public static ChannelFragment newInstance(String userId){
        ChannelFragment mFragment = new ChannelFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString(USER_ID,userId);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getString(USER_ID);
        } else {
            userId = prefManager.userId().getOr("1301");
        }
        fragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_channel, container, false);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT ));

        aq = new AQuery(getActivity(), rootView);
        return rootView;
    }

    int currentPage;
    boolean isRefresh = false;
    boolean isLoadding = false;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                String loadMoreUrl = "http://api.vdomax.com/search/channel/a?from=0&limit=10";
                aq.ajax(loadMoreUrl, JSONObject.class, fragment, "getJson");
                //Log.e("5555","onRefresh");
            }
        });

        channelAdapter = new ChannelAdapter(getActivity(), list);

        mGridView = (GridView) view.findViewById(R.id.grid);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), SlidingUpRecyclerViewActivity.class);
                i.putExtra("userId", list.get(position).id);
                i.putExtra("avatar",list.get(position).getAvatarUrl());
                i.putExtra("cover",list.get(position).getCoverUrl());
                i.putExtra("name",list.get(position).name);
                i.putExtra("username",list.get(position).username);
                getActivity().startActivity(i);
            }
        });
        mGridView.setAdapter(channelAdapter);
        mGridView.setOnScrollListener(new EndlessListOnScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                currentPage = page;
                isRefresh = false;
                String loadMoreUrl = "http://api.vdomax.com/search/channel/" + alphabet[page] + "?from=" + page + "&limit=10";
                if (!isLoadding)
                    aq.ajax(loadMoreUrl, JSONObject.class, fragment, "getJson");
                isLoadding = true;
                Log.e("5555", loadMoreUrl);
            }
        });

        aq = new AQuery(getActivity());
        aq.ajax(channelUrl, JSONObject.class, this, "getJson");
        Log.e("callme",fragment.channelUrl);
    }

    public void getJson(String url, JSONObject jo, AjaxStatus status)
            throws JSONException {
        AQUtility.debug("jo", jo);
        if (jo != null) {
            if(isRefresh)
                list.clear();
            isRefresh = false;

            JSONArray ja = jo.optJSONArray("result");
            for (int i = 0; i < ja.length(); i++) {
                JSONObject obj = ja.optJSONObject(i);

                String userId = obj.optString("id");
                String name = obj.optString("name").replace("&#039;","'");
                String username = obj.optString("username");
                String avatar = obj.optString("avatar_url");
                String cover = obj.optString("cover_url");
                String liveCover = obj.optString("live_cover");
                String gender = obj.optString("gender");
                boolean liveStatus = obj.optBoolean("status");

                Channel channel = new Channel(userId,name,username,cover,avatar,liveCover,gender,liveStatus);
                list.add(channel);
            }
            channelAdapter.notifyDataSetChanged();
            swipeLayout.setRefreshing(false);
            isLoadding = false;
            AQUtility.debug("done");
            Log.e("araiwa","call?");
            Log.e("sizesize",list.size() + "");

        } else {
            AQUtility.debug("error!");
        }
    }


}
