package co.aquario.socialkit.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import co.aquario.socialkit.R;

/**
 * Fragment implementation created to show a poster inside an ImageView widget.
 *
 * @author Pedro Vicente Gómez Sánchez.
 */
public class MoviePosterFragment extends Fragment implements ObservableScrollViewCallbacks {

    @InjectView(R.id.iv_thumbnail) ImageView thumbnailImageView;


    private String videoPosterThumbnail;
    private String posterTitle;

    /**
     * Override method used to initialize the fragment.
     */
    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_youtube_detail, container, false);
        ButterKnife.inject(this, view);
        Picasso.with(getActivity())
                .load(videoPosterThumbnail)
                .into(thumbnailImageView);

        ObservableScrollView scrollView = (ObservableScrollView) view.findViewById(R.id.scroll);
        scrollView.setScrollViewCallbacks(this);

        VideoListFragment fragment = new VideoListFragment();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.suggestion_container, fragment);
        transaction.commit();



        return view;
    }



    /**
     * Show the poster image in the thumbnailImageView widget.
     */
    public void setPoster(String videoPosterThumbnail) {
        this.videoPosterThumbnail = videoPosterThumbnail;
    }

    /**
     * Store the poster title to show it when the thumbanil view is clicked.
     */
    public void setPosterTitle(String posterTitle) {
        this.posterTitle = posterTitle;
    }

    /**
     * Method triggered when the iv_thumbnail widget is clicked. This method shows a toast with the
     * poster information.
     */
    @OnClick(R.id.iv_thumbnail) void onThubmnailClicked() {
        Toast.makeText(getActivity(), posterTitle, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onScrollChanged(int i, boolean b, boolean b2) {

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }
}
