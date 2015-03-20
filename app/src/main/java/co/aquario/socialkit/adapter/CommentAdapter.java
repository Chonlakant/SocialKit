package co.aquario.socialkit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import co.aquario.socialkit.R;
import co.aquario.socialkit.model.CommentStory;
import co.aquario.socialkit.widget.RoundedTransformation;


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ContactViewHolder>{
    public static Context context;
    public static ArrayList<CommentStory> list = new ArrayList<CommentStory>();

    public CommentAdapter(Context context, ArrayList<CommentStory> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);



        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        CommentStory item = list.get(position);

        holder.name.setText(item.user.name);

        holder.txtComment.setText(Html.fromHtml("<strong><em>" + item.text + "</em></strong>"));
        holder.time.setText(item.timestamp);
//        holder.loveCount.setText(item.loveCount);

        Picasso.with(context)
                .load(item.user.getAvatarUrl())
                .centerCrop()
                .resize(100, 100)
                .transform(new RoundedTransformation(40, 4))
                .into(holder.imageProfileUrl);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        TextView txtComment;
        TextView time;
        TextView loveCount;

        ImageView imageProfileUrl;

        public ContactViewHolder(View v) {
            super(v);

            name = (TextView) v.findViewById(R.id.txt_profile);
            txtComment = (TextView) v.findViewById(R.id.txt_comment);
            time = (TextView) v.findViewById(R.id.txt_time);
            loveCount = (TextView) v.findViewById(R.id.txt_love_count);
            imageProfileUrl = (ImageView) v.findViewById(R.id.image_profile);

        }

        @Override
        public void onClick(View v) {

        }
    }

}
