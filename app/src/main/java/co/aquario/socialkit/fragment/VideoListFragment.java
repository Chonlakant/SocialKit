package co.aquario.socialkit.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.AQUtility;
import com.github.ksoichiro.android.observablescrollview.ObservableListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import co.aquario.socialkit.MainApplication;
import co.aquario.socialkit.R;
import co.aquario.socialkit.activity.YoutubeActivity;
import co.aquario.socialkit.activity.YoutubeSampleActivity;
import co.aquario.socialkit.adapter.VideoAdapter;
import co.aquario.socialkit.model.Video;
import co.aquario.socialkit.util.PrefManager;

public class VideoListFragment extends BaseFragment {

    public static final String TEXT_FRAGMENT = "TEXT_FRAGMENT";
    private AQuery aq;
    private String url = "http://api.vdomax.com/search/video/bodyslam?from=0&limit=20";
    private ArrayList<Video> list = new ArrayList<Video>();

    private VideoAdapter adapterVideos;
    private ObservableListView listview;

    public VideoListFragment() {

    }

    public static VideoListFragment newInstance(String text){
        VideoListFragment mFragment = new VideoListFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    private PrefManager pref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = MainApplication.get(getActivity().getApplicationContext()).getPrefManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_listview, container, false);

        aq = new AQuery(getActivity());
        adapterVideos = new VideoAdapter(getActivity(), list);
        listview = (ObservableListView) rootView.findViewById(R.id.scroll);
        listview.setAdapter(adapterVideos);

        /*
        adapterVideos.SetOnItemClickListener(new VideoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {

            }
        });
        */



        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Video post = list.get(position);
                String idYoutube = post.getYoutubeId();
                String title = post.getTitle();
                String description = post.getDesc();
                String userProfile = post.getpAvatar();
                String profileName = post.getpName();

                Intent i = new Intent(getActivity(),YoutubeActivity.class);
                i.putExtra("id",idYoutube);
                i.putExtra("title",title);
                i.putExtra("desc",description);
                i.putExtra("avatar",userProfile);
                i.putExtra("name",profileName);
                //startActivity(i);

                Intent i2 = new Intent(getActivity(), YoutubeSampleActivity.class);
                startActivity(i2);

                Log.e("clicked", post.toJson());
            }
        });



        aq.ajax(url, JSONObject.class, this, "getjson");

        return rootView;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public void getjson(String url, JSONObject jo, AjaxStatus status)
            throws JSONException {
        AQUtility.debug("jo", jo);

        if (jo != null) {
            JSONArray ja = jo.getJSONArray("result");
            for (int i = 0; i < ja.length(); i++) {
                JSONObject obj = ja.getJSONObject(i);

                String mediaType = obj.optString("media_type");

                if(mediaType.equals("youtube")) {
                    String postId = obj.optString("post_id");
                    String title = obj.optString("youtube_title");
                    String desc = obj.optString("youtube_description");
                    String youtubeId = obj.optString("youtube_video_id");
                    String text = obj.optString("text");
                    String timestamp = obj.optString("time");
                    String view = obj.optString("view");

                    JSONObject publisher = obj.optJSONObject("publisher");
                    String pUserId = publisher.optString("id");
                    String pName = publisher.optString("username");
                    //JSONObject cover = publisher.optJSONObject("cover");
                    String pAvatar = publisher.optString("avatar_url");

                    Video item = new Video(postId,title,desc,youtubeId,text,timestamp,view,pUserId,pName,pAvatar);
                    list.add(item);
                }
            }
            adapterVideos.notifyDataSetChanged();
            setListViewHeightBasedOnChildren(listview);

            AQUtility.debug("done");

        } else {
            AQUtility.debug("error!");
        }
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

}
