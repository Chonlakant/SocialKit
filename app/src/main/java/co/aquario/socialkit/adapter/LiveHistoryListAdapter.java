package co.aquario.socialkit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.aquario.socialkit.R;
import co.aquario.socialkit.model.Live;

public class LiveHistoryListAdapter extends BaseAdapter {
	private final Context context;
	// private final List<String> urls = new ArrayList<String>();
	private List<Live> artistList = new ArrayList<Live>();

	public LiveHistoryListAdapter(Context context, List<Live> artistList) {
		this.context = context;
		this.artistList = artistList;

		// Collections.addAll(urls, Data.URLS);
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder holder;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(
					R.layout.item_live_history, parent, false);
			holder = new ViewHolder();
            holder.title_live_name = (TextView) view.findViewById(R.id.title_live_name);
            holder.title_date_name = (TextView) view.findViewById(R.id.title_date_name);
            holder.text_time_name = (TextView) view.findViewById(R.id.text_time_name);
            holder.text_duration_name = (TextView) view.findViewById(R.id.text_duration_name);
            holder.image_live = (ImageView) view.findViewById(R.id.image_live);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

        Live item = artistList.get(position);

        PrettyTime p = new PrettyTime();
        long agoLong = Integer.parseInt(item.getTimestamp());
        Date timeAgo = new java.util.Date((long) agoLong * 1000);
        String mydate = p.format(timeAgo);

        String string = item.getNameLive();
        String[] parts = string.split("-");
        String name = "";
        String timeDate = "";
        String date = "";
        String time = "";
        if(parts.length == 2) {
            name = parts[0]; // 004
            timeDate = parts[1]; // 034556
            String[] parts2 = timeDate.split("_");
            date = parts2[0];
            time = parts2[1];
        } else {
            name = parts[0]; // 004
            date = parts[1] + "-" + parts[2] + "-" + parts[3]; // 034556
            time = parts[4];
        }



        String durationStr = item.getHours()+":"+item.getMinutes()+":"+item.getSeconds();

        holder.title_live_name.setText(name);
        holder.title_date_name.setText(mydate);
        holder.text_time_name.setText(time);
        holder.text_duration_name.setText(durationStr);

        Picasso.with(context)
                .load(item.getPhotoLive())
                .fit().centerCrop()
                .into(holder.image_live);


		return view;
	}

	@Override
	public int getCount() {
		return artistList.size();
	}

	@Override
	public String getItem(int position) {
		return artistList.get(position).getNameLive();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	static class ViewHolder {
        TextView title_live_name;
        TextView title_date_name;
        TextView text_time_name;
        TextView text_duration_name;
        ImageView image_live;
	}
}
