package co.aquario.socialkit.adapater;

import android.content.Context;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import co.aquario.socialkit.R;
import co.aquario.socialkit.model.ProductAquery;
import co.aquario.socialkit.model.ShoppingCartHelper;


public class ItemListAdapter2 extends RecyclerView.Adapter<ItemListAdapter2.ViewHolder> {

    private OnItemClickListener mItemClickListener;

    public static final String EXTRA_NAME = "Subcategory name";
    List<ProductAquery> productses = new ArrayList<>();
    public Context context;
    private boolean mShowQuantity;

    public ItemListAdapter2(List<ProductAquery> productses, boolean showQuantity, Context context) {
        this.productses = productses;
        this.context = context;
        mShowQuantity = showQuantity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        final View sView = mInflater.inflate(R.layout.list_item_holder, parent, false);
        return new ViewHolder(sView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ProductAquery i = productses.get(position);
        holder.nameTv.setText(i.getName());
        holder.itemPrice.setText(i.getPrice() + "");

        Picasso.with(context)
                .load(i.getImage())
//                .resize(200, 250)
                .into(holder.image);

        holder.heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the heart status
                if (i.ismHeart()) {
                    holder.heart.setImageResource(android.R.color.transparent);
                    i.setmHeart(false);
                } else {
                    holder.heart.setImageResource(R.drawable.heart_filled);
                    i.setmHeart(true);
                }
            }
        });

        if (i.ismHeart()) {
            holder.heart.setImageResource(R.drawable.heart_filled);
        } else {
            holder.heart.setImageResource(android.R.color.transparent);
        }
    }

    @Override
    public int getItemCount() {
        return productses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView nameTv;
        TextView itemPrice;
        ImageView image;
        ImageButton heart;


        public ViewHolder(View v) {
            super(v);
            nameTv = (TextView) v.findViewById(R.id.item_name);
            itemPrice = (TextView) v.findViewById(R.id.item_price);
            image = (ImageView) v.findViewById(R.id.item_picture);
            heart = (ImageButton) v.findViewById(R.id.heart_icon);

            image.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.item_picture:
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(v, getPosition());
                    }
            }

        }

    }


    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public OnItemClickListener getmItemClickListener() {
        return mItemClickListener;
    }

    /*
         * Snippet from http://stackoverflow.com/a/363692/1008278
         */
    public static int randInt(int min, int max) {
        final Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    /* ==========This Part is not necessary========= */

}