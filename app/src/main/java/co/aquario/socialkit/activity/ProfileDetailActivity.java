package co.aquario.socialkit.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.materialdrawer.view.CircularImageView;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import co.aquario.socialkit.R;
import co.aquario.socialkit.adapter.FeedAdapter;
import co.aquario.socialkit.event.LoadTimelineEvent;
import co.aquario.socialkit.event.LoadTimelineSuccessEvent;
import co.aquario.socialkit.handler.ApiBus;
import co.aquario.socialkit.model.PostStory;


public class ProfileDetailActivity extends Activity {
    CircularImageView image_title_detail;
    ImageView cover1;
    TextView nameTitleDeatil;
    String imageTitle;
    String nameTitle;
    String cover;
    String userId;
    public ArrayList<PostStory> list = new ArrayList<>();
    private FeedAdapter adapter;

    private boolean isHomeTimeline = false;
    private static final String TYPE = "";
    private static final int PER_PAGE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);

        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        userId = (String) b.get("userId");
        imageTitle = (String) b.get("avatar");
        nameTitle = (String) b.get("name");
        cover = (String) b.get("cover");

        nameTitleDeatil = (TextView) findViewById(R.id.name_title);
        image_title_detail = (CircularImageView) findViewById(R.id.image_title_detail);
        cover1 = (ImageView) findViewById(R.id.cover);

        nameTitleDeatil.setText(nameTitle);

        Picasso.with(getApplication())
                .load(cover)
                .fit().centerCrop()
                .into(cover1);

        Picasso.with(getApplication())
                .load(imageTitle)
                .into(image_title_detail);
        adapter = new FeedAdapter(this, list);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.scroll);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new FeedAdapter(this, list);
        recyclerView.setAdapter(adapter);
        ApiBus.getInstance().register(this);
        Log.v("profileDetailuserId",userId + "");
        ApiBus.getInstance().post(new LoadTimelineEvent(Integer.parseInt(userId),TYPE,1,PER_PAGE,isHomeTimeline));
    }

    @Subscribe
    public void onLoadTimelineSuccess(LoadTimelineSuccessEvent event) {
        list.addAll(event.getTimelineData().getPosts());
        adapter.notifyDataSetChanged();
        Log.e("itemCountAfterNotify", adapter.getItemCount() + "");
    }

}

