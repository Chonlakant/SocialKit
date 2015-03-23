package co.aquario.socialkit.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import co.aquario.socialkit.R;
import co.aquario.socialkit.event.PhotoLoadEvent;
import it.sephiroth.android.library.imagezoom.ImageViewTouch;

public class PhotoZoomFragment extends BaseFragment {
    Context context;
    ImageViewTouch imageFeedBig;

    public static final String LOAD_URL = "LOAD_URL";
    private String mUrl = "";

    public static PhotoZoomFragment newInstance(String text){
        PhotoZoomFragment mFragment = new PhotoZoomFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString(LOAD_URL, text);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUrl = getArguments().getString(LOAD_URL);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_photo_zoom, container, false);

        context = getActivity();
        imageFeedBig = (ImageViewTouch) rootView.findViewById(R.id.image);

        if(!mUrl.equals(""))
            Picasso.with(context)
                    .load(mUrl)
                    .into(imageFeedBig);

        return rootView;
    }

    @Subscribe public void onPhotoLoad(PhotoLoadEvent event) {
        Picasso.with(context)
                .load(event.getUrl())
                .into(imageFeedBig);
    }
}
