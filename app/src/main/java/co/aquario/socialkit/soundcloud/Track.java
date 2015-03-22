package co.aquario.socialkit.soundcloud;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kevin on 2/22/15.
 */
public class Track {
    @SerializedName("title")
    private String mTitle;

    @SerializedName("stream_url")
    private String mStreamURL;

    @SerializedName("id")
    private int mID;

    @SerializedName("artwork_url")
    private String artworkURL;

    private String uri;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getStreamURL() {
        return mStreamURL;
    }

    public String getArtworkURL() {
        return artworkURL;
    }

    public String getUri() {
        return uri;
    }

    public String getAvatarURL(){
        String avatarURL = getAvatarURL();
        if (avatarURL != null){
            return artworkURL.replace("large","tiny");
        } else {
            return avatarURL;
        }

    }
}