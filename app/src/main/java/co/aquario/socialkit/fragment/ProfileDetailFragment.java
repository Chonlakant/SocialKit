package co.aquario.socialkit.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.materialdrawer.view.CircularImageView;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import co.aquario.socialkit.R;
import co.aquario.socialkit.adapter.FeedAdapter;
import co.aquario.socialkit.event.GetUserProfileEvent;
import co.aquario.socialkit.event.GetUserProfileSuccessEvent;
import co.aquario.socialkit.event.LoadTimelineEvent;
import co.aquario.socialkit.event.LoadTimelineSuccessEvent;
import co.aquario.socialkit.handler.ApiBus;
import co.aquario.socialkit.model.PostStory;


public class ProfileDetailFragment extends BaseFragment {
    CircularImageView avatar;
    ImageView cover;
    TextView titleTv;
    TextView usernameTv;

    String imageTitle;
    String nameTitle;
    String coverUrl;
    String userId;
    String username;

    public ArrayList<PostStory> list = new ArrayList<>();
    private FeedAdapter adapter;

    private boolean isHomeTimeline = false;
    private static final String TYPE = "";
    private static final int PER_PAGE = 20;

    public static String USER_ID = "userId";

    public static ProfileDetailFragment newInstance(String userId){
        ProfileDetailFragment mFragment = new ProfileDetailFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString(USER_ID, userId);
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
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile_detail, container, false);
        //setContentView(R.layout.fragment_profile_detail);

        //Intent iin = getIntent();
        //Bundle b = iin.getExtras();

        /*
        userId = (String) b.get("userId");
        imageTitle = (String) b.get("avatar");
        nameTitle = (String) b.get("name");
        coverUrl = (String) b.get("cover");
        username = (String) b.get("username");
        */

        titleTv = (TextView) rootView.findViewById(R.id.name_title);
        usernameTv = (TextView) rootView.findViewById(R.id.name_username);
        avatar = (CircularImageView) rootView.findViewById(R.id.avatar);
        cover = (ImageView) rootView.findViewById(R.id.cover);

        /*

        titleTv.setText(nameTitle);
        usernameTv.setText("@" + username);

        Picasso.with(getActivity())
                .load(coverUrl)
                .fit().centerCrop()
                .into(cover);

        Picasso.with(getActivity())
                .load(imageTitle)
                .into(avatar);

                */

        adapter = new FeedAdapter(getActivity(), list);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.scroll);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //adapter.setLockedAnimations(true);
            }
        });

        recyclerView.setAdapter(adapter);
        ApiBus.getInstance().register(this);
        Log.v("profileDetailuserId",userId + "");
        ApiBus.getInstance().post(new LoadTimelineEvent(Integer.parseInt(userId),TYPE,1,PER_PAGE,isHomeTimeline));
        ApiBus.getInstance().post(new GetUserProfileEvent(userId));

        return rootView;
    }

    @Subscribe
    public void onLoadProfile(GetUserProfileSuccessEvent event) {
        /*
        titleTv.setText(event.getUser().name);
        usernameTv.setText("@" + event.getUser().username);

        Picasso.with(getActivity())
                .load(event.getUser().getCoverUrl())
                .fit().centerCrop()
                .into(cover);

        Picasso.with(getActivity())
                .load(event.getUser().getAvatarUrl())
                .into(avatar);
                */

    }

    @Subscribe
    public void onLoadTimelineSuccess(LoadTimelineSuccessEvent event) {
        list.addAll(event.getTimelineData().getPosts());
        adapter.notifyDataSetChanged();
        Log.e("itemCountAfterNotify", adapter.getItemCount() + "");
    }

}

