package co.aquario.socialkit.activity;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import co.aquario.socialkit.MainApplication;
import co.aquario.socialkit.R;
import co.aquario.socialkit.util.PrefManager;
import de.hdodenhof.circleimageview.CircleImageView;
import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class VideoViewActivity extends Activity {

    /**
     * TODO: Set the path variable to a streaming video URL or a local media file
     * path.
     */
    private String path = "";
    private VideoView mVideoView;
    private EditText mEditText;
    private Button button1;
    private Button start;

    private CircleImageView mProfileImageView;
    private ImageView mImageView;
    private TextView mProfileNameTextView;
    private ImageView mLogo;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        if (!LibsChecker.checkVitamioLibs(this))
            return;

        if(getIntent() != null)
            path = getIntent().getExtras().getString("url");
        else
            path = "";

        setContentView(R.layout.activity_video_view);
        //mEditText = (EditText) findViewById(R.id.url);
        mVideoView = (VideoView) findViewById(R.id.surface_view);
        //button1 = (Button) findViewById(R.id.button1);
        //start = (Button) findViewById(R.id.start);

        mImageView = (ImageView) findViewById(R.id.image);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //slideOnClick();
            }
        });
        mProfileImageView = (CircleImageView) findViewById(R.id.profile_image);
        mProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //slideOnClick();
            }
        });
        mProfileNameTextView = (TextView) findViewById(R.id.profile_name);
        mLogo = (ImageView) findViewById(R.id.logo);

        /*
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openVideo("http://server-a.vdomax.com:8080/record/Nuchiko-260115_20:55:13.flv");
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openVideo("rtmp://150.107.31.12:1935/live/korrio");
            }
        });
        */

        PrefManager pref = MainApplication.get(this).getPrefManager();

        String userId = getIntent().getExtras().getString("userId");
        String avatarUrl = getIntent().getExtras().getString("avatar");
        String coverUrl = getIntent().getExtras().getString("cover");
        String name = getIntent().getExtras().getString("name");
        String username = getIntent().getExtras().getString("username");

        if(userId == null) {
            userId = pref.userId().getOr("6");
        }

        if(name == null) {
            name = pref.name().getOr("");
        }

        if(username == null) {
            username = pref.username().getOr("");
        }

        if(avatarUrl == null) {
            avatarUrl = pref.avatar().getOr("");
        }

        if(coverUrl == null) {
            coverUrl = pref.cover().getOr("");
        }

        setTitle(name);
        mProfileNameTextView.setText(name);
        Picasso.with(this).load(coverUrl).into(mImageView);
        Picasso.with(this).load(avatarUrl).into(mProfileImageView);



        if (path == "") {
            // Tell the user to provide a media file URL/path.
            path = "http://server-a.vdomax.com:8080/record/Nuchiko-260115_20:55:13.flv";
            //Toast.makeText(VideoViewActivity.this, "Please edit VideoViewDemo Activity, and set path" + " variable to your media file URL/path", Toast.LENGTH_LONG).show();
            mVideoView.setVideoPath(path);
            mVideoView.setMediaController(new MediaController(this));
            mVideoView.requestFocus();

            mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    // optional need Vitamio 4.0
                    mediaPlayer.setPlaybackSpeed(1.0f);
                }
            });

            //return;
        } else {
			/*
			 * Alternatively,for streaming media you can use
			 * mVideoView.setVideoURI(Uri.parse(URLstring));
			 */
            mVideoView.setVideoPath(path);
            mVideoView.setMediaController(new MediaController(this));
            mVideoView.requestFocus();

            //mVideoView.addTimedTextSource(Environment.getExternalStorageDirectory() + "/12.srt");

            mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    // optional need Vitamio 4.0
                    mediaPlayer.setPlaybackSpeed(1.0f);
                }
            });
        }

    }

    private LinearLayout.LayoutParams paramsNotFullscreen; //if you're using RelativeLatout

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);


        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) //To fullscreen
        {
            paramsNotFullscreen = (LinearLayout.LayoutParams) mVideoView.getLayoutParams();
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(paramsNotFullscreen);
            params.setMargins(0, 0, 0, 0);
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            mVideoView.setLayoutParams(params);

            mLogo.setVisibility(View.GONE);
            mProfileImageView.setVisibility(View.GONE);
            mProfileNameTextView.setVisibility(View.GONE);


        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            mVideoView.setLayoutParams(paramsNotFullscreen);
            mLogo.setVisibility(View.VISIBLE);
            mProfileImageView.setVisibility(View.VISIBLE);
            mProfileNameTextView.setVisibility(View.VISIBLE);
        }
    }

    public void startPlay() {
        String url = mEditText.getText().toString();
        path = url;
        if (!TextUtils.isEmpty(url)) {
            mVideoView.setVideoPath(url);
        }
    }

    public void openVideo(String mypath) {
        mVideoView.setVideoPath(mypath);
    }

}