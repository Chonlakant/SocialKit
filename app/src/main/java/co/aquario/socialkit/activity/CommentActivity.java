package co.aquario.socialkit.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import co.aquario.socialkit.R;
import co.aquario.socialkit.adapter.CommentAdapter;
import co.aquario.socialkit.event.GetStoryEvent;
import co.aquario.socialkit.event.GetStorySuccessEvent;
import co.aquario.socialkit.handler.ApiBus;
import co.aquario.socialkit.model.CommentStory;
import co.aquario.socialkit.model.PostStory;
import co.aquario.socialkit.widget.RoundedTransformation;


public class CommentActivity extends ActionBarActivity {

    private ArrayList<CommentStory> list = new ArrayList<CommentStory>();
    private CommentAdapter adapterComment;

    private Context context;
    private ImageView thumb;
    private ImageView avatar;
    private View feedButton;
    private View feedDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        context = this;
        adapterComment = new CommentAdapter(this, list);

        feedButton = findViewById(R.id.feed_button_group);
        feedDetail = findViewById(R.id.feed_detail_group);

        thumb = (ImageView) feedDetail.findViewById(R.id.thumb);
        avatar = (ImageView) feedDetail.findViewById(R.id.profile_avatar);

        String postId = getIntent().getExtras().getString("postId");

        RecyclerView recList = (RecyclerView) findViewById(R.id.comment_list);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        recList.setAdapter(adapterComment);
        ApiBus.getInstance().register(this);
        ApiBus.getInstance().post(new GetStoryEvent(postId));

        thumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Subscribe
    public void onGetCommentData(GetStorySuccessEvent event) {
        //Log.e("DEBUGCOMMENT",event.getPost().comment.get(0).user.name);
        PostStory item = event.getPost();
        if(event.getPost().commentCount > 0) {
            Log.e("555","in>");
            list.addAll(event.getPost().comment);
            Log.e("itemCount",adapterComment.getItemCount() + "");
            adapterComment.notifyDataSetChanged();
            Log.e("itemCountAfterNotify",adapterComment.getItemCount() + "");
        }

        Log.e("yes",item.toJson());
        //event.getPost().author.name;

        Log.e("yes2",item.postId);
        Log.e("yes3",item.author.avatarPath);


        Log.e("avatar",item.author.getAvatarPath());
        //Log.e("thumb",item.media.getThumbUrl());

        if(checkNull(item.media))
            Picasso.with(context)
                    .load(item.media.getThumbUrl())
                    .into(thumb);
        else if(checkNull(item.youtube))
            Picasso.with(context)
                    .load(item.youtube.thumbnail)
                    .into(thumb);
        else if(checkNull(item.clip))
            Picasso.with(context)
                    .load(item.clip.thumb)
                    .into(thumb);
        else
            thumb.setVisibility(View.GONE);

        Picasso.with(context)
                .load(item.author.getAvatarPath())
                //.resize(100, 100)
                .transform(new RoundedTransformation(50, 50))

                .into(avatar);

    }

    private boolean checkNull(Object obj) {
        return obj != null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


}
