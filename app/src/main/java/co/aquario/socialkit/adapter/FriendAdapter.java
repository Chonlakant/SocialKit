package co.aquario.socialkit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonRectangle;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import co.aquario.socialkit.R;
import co.aquario.socialkit.model.User;
import de.hdodenhof.circleimageview.CircleImageView;


public class FriendAdapter extends BaseAdapter {

    public Context context;

    public ArrayList<User> list = new ArrayList<User>();

    public FriendAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View row = mInflater.inflate(R.layout.item_friend, parent, false);
        ButtonRectangle btn;
        TextView name;
        CircleImageView avatar;

        User item = list.get(position);
        name = (TextView) row.findViewById(R.id.profile_name);
        avatar = (CircleImageView) row.findViewById(R.id.profile_image);

        name.setText(item.name);
        //date.setText(item.getTxt_friends());

        Picasso.with(context)
                .load(item.getAvatarUrl())
                .fit().centerCrop()
                .into(avatar);

        return row;
    }
}