package co.aquario.socialkit.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import co.aquario.socialkit.MainApplication;
import co.aquario.socialkit.R;
import co.aquario.socialkit.activity.LoginActivity;
import co.aquario.socialkit.activity.MainActivity;
import co.aquario.socialkit.activity.SearchYoutubeActivity;
import co.aquario.socialkit.activity.TakePhotoActivity;
import co.aquario.socialkit.adapter.FeedAdapter;
import co.aquario.socialkit.event.LoadTimelineEvent;
import co.aquario.socialkit.event.LoadTimelineSuccessEvent;
import co.aquario.socialkit.event.LogoutEvent;
import co.aquario.socialkit.handler.ApiBus;
import co.aquario.socialkit.model.PostStory;
import co.aquario.socialkit.util.PrefManager;
import co.aquario.socialkit.widget.EndlessRecyclerOnScrollListener;


public class FeedFragment extends BaseFragment {

    private boolean mSearchCheck;
    public static final String USER_ID = "USER_ID";

    public ArrayList<PostStory> list = new ArrayList<>();
    public FeedAdapter adapter;
    public RelativeLayout layoutMenu;
    private int currentPage = 1;
    private boolean isRefresh = false;
    private boolean isLoadding = false;

    public static FeedFragment newInstance(String userId){
        FeedFragment mFragment = new FeedFragment();
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

    private SwipeRefreshLayout swipeLayout;

    private FloatingActionButton postPhotoBtn;
    private FloatingActionButton postVideoBtn;
    private FloatingActionButton postYoutubeBtn;
    String userId;

    // home_timeline = including others post
    // user_timeline = only the user's post
    private boolean isHomeTimeline = true;
    private static final String TYPE = "";
    private static final int PER_PAGE = 20;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        /*
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                ApiBus.getInstance().post(new LoadTimelineEvent(Integer.parseInt(userId),TYPE,1,PER_PAGE,isHomeTimeline));
                //String loadMoreUrl = "http://api.vdomax.com/search/channel/a?from=0&limit=10";
                //aq.ajax(loadMoreUrl, JSONObject.class, fragment, "getJson");
                //Log.e("5555","onRefresh");
            }
        });
        */

    }

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub		
		View rootView = inflater.inflate(R.layout.fragment_feed, container, false);

        PrefManager pref = MainApplication.get(getActivity()).getPrefManager();
        userId = pref.userId().getOr("6");

        Log.e("userId",userId);

        layoutMenu = (RelativeLayout) rootView.findViewById(R.id.layoutMenu);

        postPhotoBtn = (FloatingActionButton) layoutMenu.findViewById(R.id.action_photo);
        postVideoBtn = (FloatingActionButton) layoutMenu.findViewById(R.id.action_video);
        postYoutubeBtn = (FloatingActionButton) layoutMenu.findViewById(R.id.action_youtube);

        postYoutubeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SearchYoutubeActivity.class);
                startActivity(i);

            }
        });

        postPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((MainActivity) getActivity()).selectImage();
                int[] startingLocation = new int[2];
                v.getLocationOnScreen(startingLocation);
                startingLocation[0] += v.getWidth() / 2;
                TakePhotoActivity.startCameraFromLocation(startingLocation, getActivity());
                //overridePendingTransition(0, 0);
            }
        });
        postVideoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).selectVideo();
            }
        });

        //aq = new AQuery(getActivity());

        adapter = new FeedAdapter(getActivity(), list);

        adapter.SetOnItemClickListener(new FeedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });


        adapter.OnItemLoveClick(new FeedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(),"Love",Toast.LENGTH_SHORT).show();
            }
        });

        adapter.OnItemShareClick(new FeedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(),"Share",Toast.LENGTH_SHORT).show();
            }
        });

        RecyclerView recList = (RecyclerView) rootView.findViewById(R.id.scroll);
        recList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        // load more
        recList.setOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page) {
                currentPage = page;
                isRefresh = false;
                if (!isLoadding)
                    ApiBus.getInstance().post(new LoadTimelineEvent(Integer.parseInt(userId), TYPE, page, PER_PAGE, isHomeTimeline));
                isLoadding = true;
                Log.e("thispageis",page + "");
            }
        });

        recList.setLayoutManager(linearLayoutManager);
        recList.setAdapter(adapter);

        recList.setOnTouchListener(new View.OnTouchListener() {

            final int DISTANCE = 3;

            float startY = 0;
            float dist = 0;
            boolean isMenuHide = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getAction();

                if (action == MotionEvent.ACTION_DOWN) {
                    startY = event.getY();
                } else if (action == MotionEvent.ACTION_MOVE) {
                    dist = event.getY() - startY;

                    if ((pxToDp((int) dist) <= -DISTANCE) && !isMenuHide) {
                        isMenuHide = true;
                        hideFloatingButton();
                    } else if ((pxToDp((int) dist) > DISTANCE) && isMenuHide) {
                        isMenuHide = false;
                        showFloatingButton();
                    }

                    if ((isMenuHide && (pxToDp((int) dist) <= -DISTANCE))
                            || (!isMenuHide && (pxToDp((int) dist) > 0))) {
                        startY = event.getY();
                    }
                } else if (action == MotionEvent.ACTION_UP) {
                    startY = 0;
                }

                return false;
            }
        });


        ApiBus.getInstance().post(new LoadTimelineEvent(Integer.parseInt(userId),TYPE,1,PER_PAGE,isHomeTimeline));
        //ApiBus.getInstance().post(new LoadTimelineEvent(32,"photo",1,50));
        //aq.ajax(urlMain, JSONObject.class, this, "getJson");

		rootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
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

    @Subscribe public void onLoadTimelineSuccess(LoadTimelineSuccessEvent event) {
        if(isRefresh)
            list.clear();
        isRefresh = false;
        list.addAll(event.getTimelineData().getPosts());
        adapter.notifyDataSetChanged();
        //swipeLayout.setRefreshing(false);
        isLoadding = false;

    }

    @Subscribe public void onLogout(LogoutEvent event) {
        MainApplication.logout();
        Intent login = new Intent(getActivity(), LoginActivity.class);
        startActivity(login);
        getActivity().finish();
    }

    /*

    @Subscribe void onLoadTimelineFailed() {

    }

    @Subscribe void onLoadTimelineFailedNetwork() {

    }

    */
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);

		inflater.inflate(R.menu.menu, menu);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.menu_search));
        searchView.setQueryHint("Search friends,tags,videos");

        ((EditText)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text))
                .setHintTextColor(getResources().getColor(android.R.color.white));
        searchView.setOnQueryTextListener(onQuerySearchView);

		//menu.findItem(R.id.menu_add).setVisible(true);
		menu.findItem(R.id.menu_search).setVisible(true);
		mSearchCheck = false;

	}	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_search:
			mSearchCheck = true;
			break;
		}		
		return true;
	}

   private SearchView.OnQueryTextListener onQuerySearchView = new SearchView.OnQueryTextListener() {
       @Override
       public boolean onQueryTextSubmit(String s) {
           return false;
       }

       @Override
       public boolean onQueryTextChange(String s) {
           if (mSearchCheck){
               // implement your search here
           }
           return false;
       }
   };

    public int pxToDp(int px) {
        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        int dp = Math.round(px / (dm.densityDpi
                / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    public void showFloatingButton() {
        AnimatorSet animSet = new AnimatorSet();

        ObjectAnimator anim1 = ObjectAnimator.ofFloat(layoutMenu
                , View.TRANSLATION_Y, 0);

        animSet.playTogether(anim1);
        animSet.setDuration(300);
        animSet.start();
    }

    public void hideFloatingButton() {
        AnimatorSet animSet = new AnimatorSet();

        ObjectAnimator anim1 = ObjectAnimator.ofFloat(layoutMenu
                , View.TRANSLATION_Y, layoutMenu.getHeight());

        animSet.playTogether(anim1);
        animSet.setDuration(300);
        animSet.start();
    }
}
