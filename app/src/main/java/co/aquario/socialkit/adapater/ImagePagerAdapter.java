package co.aquario.socialkit.adapater;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import co.aquario.socialkit.R;
import co.aquario.socialkit.model.Item;
import co.aquario.socialkit.model.ProductAquery;

/**
 * Created by Joseph on 7/14/15.
 */
public class ImagePagerAdapter extends PagerAdapter {

    Context mContext;
    ArrayList<ProductAquery> mItem;
    LayoutInflater mLayoutInflater;

    public ImagePagerAdapter(Context context,  ArrayList<ProductAquery> mItem) {
        mContext = context;
        this.mItem = mItem;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mItem.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.item_pager, container, false);

        ProductAquery i = mItem.get(position);


        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        Picasso.with(mContext)
                .load(i.getImage())
                .into(imageView);
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
