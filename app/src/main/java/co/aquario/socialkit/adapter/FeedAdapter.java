package co.aquario.socialkit.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonFlat;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.Date;

import co.aquario.socialkit.R;
import co.aquario.socialkit.activity.CommentActivity;
import co.aquario.socialkit.activity.PhotoActivity;
import co.aquario.socialkit.activity.VideoViewActivity;
import co.aquario.socialkit.activity.YoutubeActivity;
import co.aquario.socialkit.event.PhotoLoadEvent;
import co.aquario.socialkit.event.TimelineDataEvent;
import co.aquario.socialkit.handler.ApiBus;
import co.aquario.socialkit.model.PostStory;
import co.aquario.socialkit.widget.RoundedTransformation;


public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private ArrayList<PostStory> list = new ArrayList<>();
    private static Activity context;

    private OnItemClickListener mItemClickListener;
    private OnItemClickListener mItemLove;
    private OnItemClickListener mItemShare;

    public FeedAdapter(Activity context, ArrayList<PostStory> list) {
        this.context = context;
        this.list = list;

        ApiBus.getInstance().register(this);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("FeedAdapter.viewType", viewType + "");
        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        final View sView = mInflater.inflate(R.layout.item_feed, parent, false);
        return new ViewHolder(sView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PostStory item = list.get(position);

        PrettyTime p = new PrettyTime();
        long agoLong = Integer.parseInt(item.time);
        Date timeAgo = new java.util.Date(agoLong * 1000);
        String ago = p.format(timeAgo);

        holder.name.setText(item.author.name);
        holder.ago.setText(item.timestamp);
        holder.nLove.setText(item.loveCount + "");
        holder.nComment.setText(item.commentCount + "");
        holder.nShare.setText(item.shareCount + "");
        holder.ago.setText(ago);

        if(item.text != null) {
            if(item.text.trim().length() < 200)
                holder.msg.setText(Html.fromHtml("" + item.text + ""));
            else
                holder.msg.setText(Html.fromHtml("" + item.text.substring(0, 200) + " ..." + ""));
        } else {
            holder.msg.setVisibility(View.GONE);
        }

        Picasso.with(context)
                .load(item.author.getAvatarPath())
                .centerCrop()
                .resize(100, 100)
                .transform(new RoundedTransformation(50, 4))
                .into(holder.avatar);

        if(checkNull(item.media)) {
            Picasso.with(context)
                    .load(item.media.getThumbUrl())

                    .error(R.drawable.offline)
                    .fit().centerCrop()
                    .into(holder.thumb);
            Picasso.with(context).load(R.drawable.ic_photo).into(holder.typeIcon);
            holder.nView.setText(item.view + " views");
            //holder.nView.setVisibility(View.GONE);
            //holder.typeIcon.setVisibility(View.GONE);
        }
        else if(checkNull(item.youtube)) {
            Picasso.with(context)
                    .load(item.youtube.thumbnail)

                    .error(R.drawable.offline)
                    .fit().centerCrop()
                    .into(holder.thumb);
            Picasso.with(context).load(R.drawable.ic_yt).into(holder.typeIcon);
            holder.nView.setText(item.view + " views");
        }
        else if(checkNull(item.clip)) {
            Picasso.with(context)
                    .load(item.clip.thumb)

                    .error(R.drawable.offline)
                    .fit().centerCrop()
                    .into(holder.thumb);
            Picasso.with(context).load(R.drawable.ic_clip).into(holder.typeIcon);
            holder.nView.setText(item.view + " views");

        }
        else {
            holder.nView.setVisibility(View.GONE);
            holder.thumb.setVisibility(View.GONE);
        }

    }

    private boolean checkNull(Object obj) {
        return obj != null;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        TextView ago;
        TextView nLove;
        TextView nComment;
        TextView nShare;
        TextView msg;
        ImageView avatar;
        ImageView thumb;
        TextView nView;
        ImageView typeIcon;

        ButtonFlat btnLove;
        ButtonFlat btnComment;
        ButtonFlat btnShare;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.profile_name);
            ago = (TextView) view.findViewById(R.id.ago);
            nLove = (TextView) view.findViewById(R.id.number1);
            nComment = (TextView) view.findViewById(R.id.number2);
            nShare = (TextView) view.findViewById(R.id.number3);
            msg = (TextView) view.findViewById(R.id.text);
            avatar = (ImageView) view.findViewById(R.id.profile_avatar);
            thumb = (ImageView) view.findViewById(R.id.thumb);
            nView = (TextView) view.findViewById(R.id.view);
            typeIcon = (ImageView) view.findViewById(R.id.ic_type);

            btnComment = (ButtonFlat) view.findViewById(R.id.btn_comment);
            btnLove = (ButtonFlat) view.findViewById(R.id.btn_love);
            btnShare = (ButtonFlat) view.findViewById(R.id.btn_share);

            thumb.setOnClickListener(this);
            avatar.setOnClickListener(this);
            btnComment.setOnClickListener(this);
            btnLove.setOnClickListener(this);
            btnShare.setOnClickListener(this);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            PostStory post = list.get(getPosition());
            String postType = post.type;


            switch (v.getId()) {
                case R.id.thumb:
                    if(postType.equals("photo")) {
                        String url = list.get(getPosition()).media.getThumbUrl();
                        //PhotoZoomFragment fragment = new PhotoZoomFragment();
                        /*
                        PhotoZoomFragment fragment = new PhotoZoomFragment().newInstance(url);
                        FragmentManager manager = ((ActionBarActivity) context).getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.replace(R.id.sub_container, fragment);
                        transaction.commit();
                        */

                        Intent i = new Intent(context, PhotoActivity.class);
                        i.putExtra("url",url);
                        context.startActivity(i);

                        PhotoLoadEvent event = new PhotoLoadEvent(url);

                        ApiBus.getInstance().post(event);
                    } else if(postType.equals("clip")) {
                        Intent i = new Intent(context ,VideoViewActivity.class);
                        i.putExtra("url",post.clip.url);
                        context.startActivity(i);
                    } else if(postType.equals("youtube")) {

                        Intent i = new Intent(context, YoutubeActivity.class);
                        i.putExtra("id",post.youtube.id);
                        i.putExtra("title",post.youtube.title);
                        i.putExtra("desc",post.youtube.desc);
                        i.putExtra("name",post.author.name);
                        i.putExtra("avatar",post.author.getAvatarPath());
                        i.putExtra("ago",post.getAgoText());
                        context.startActivity(i);

                    } else if(postType.equals("soundcloud")) {

                    }
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(v, getPosition());
                    }
                    break;
                case R.id.btn_comment:

                    TimelineDataEvent event = new TimelineDataEvent(list.get(getPosition()));
                    ApiBus.getInstance().post(event);

                    Intent i = new Intent(context, CommentActivity.class);
                    i.putExtra("postId", list.get(getPosition()).postId);
                    context.startActivity(i);
                    break;
                case R.id.profile_name:
                case R.id.avatar:
                    //Intent intent = new Intent(context, MainProfileFriends.class);
                    //context.startActivity(intent);
                    break;
                case R.id.btn_love:
                    if (mItemLove != null) {
                        mItemLove.onItemClick(v, getPosition());
                    }
                    break;
                case R.id.btn_share:
                    if (mItemShare != null) {
                        mItemShare.onItemClick(v, getPosition());
                    }
                    break;


            }
        }

    }


    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }
    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemLoveClick {
        public void onItemClick(View view, int position);
    }
    public void OnItemLoveClick(final OnItemClickListener mItemLove) {

        this.mItemLove = mItemLove;
    }


    public interface OnItemShareClick {
        public void onItemClick(View view, int position);
    }
    public void OnItemShareClick(final OnItemClickListener mItemShare) {
        this.mItemShare = mItemShare;
    }
}