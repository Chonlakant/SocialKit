package co.aquario.socialkit.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonRectangle;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import co.aquario.socialkit.R;
import co.aquario.socialkit.activity.SlidingUpRecyclerViewActivity;
import co.aquario.socialkit.model.User;
import de.hdodenhof.circleimageview.CircleImageView;


public class FriendRecyclerAdapter extends RecyclerView.Adapter<FriendRecyclerAdapter.ViewHolder> {

    public Context context;

    public ArrayList<User> list = new ArrayList<User>();

    public OnItemClickListener mItemClickListener;

    public FriendRecyclerAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        final View sView = mInflater.inflate(R.layout.item_friend, parent, false);
        return new ViewHolder(sView,new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(context, SlidingUpRecyclerViewActivity.class);
                i.putExtra("userId",list.get(position).id);
                i.putExtra("avatar",list.get(position).getAvatarUrl());
                i.putExtra("cover",list.get(position).getCoverUrl());
                i.putExtra("name",list.get(position).name);
                i.putExtra("username",list.get(position).username);
                context.startActivity(i);
            }

            @Override
            public void onFollowClick(View view, int position) {

            }
        });
    }

    public void updateList(ArrayList<User> data) {
        list = data;
        notifyDataSetChanged();
    }

    public void addItem(int position, User data) {
        list.add(position, data);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        User user = list.get(position);
        holder.name.setText(user.name);

        Picasso.with(context)
                .load(user.getAvatarUrl())
                .fit().centerCrop()
                .into(holder.avatar);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        CircleImageView avatar;
        ButtonRectangle btnFollow;

        OnItemClickListener listener;

        public ViewHolder(View view, OnItemClickListener listener) {
            super(view);

            this.listener = listener;

            name = (TextView) view.findViewById(R.id.profile_name);
            avatar = (CircleImageView) view.findViewById(R.id.profile_image);
            btnFollow = (ButtonRectangle) view.findViewById(R.id.btn_follow);

            avatar.setOnClickListener(this);
            btnFollow.setOnClickListener(this);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.profile_image:
                    listener.onItemClick(v,getPosition());
                    break;
                case R.id.btn_follow:
                    listener.onFollowClick(v, getPosition());
                    break;


            }
        }

    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
        public void onFollowClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}