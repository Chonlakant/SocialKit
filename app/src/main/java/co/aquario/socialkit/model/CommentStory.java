package co.aquario.socialkit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mac on 3/10/15.
 */
public class CommentStory {
    @Expose
    public String id;
    @Expose
    public String text;
    @Expose
    public User user;
    @Expose
    public String timestamp;
    @Expose
    @SerializedName("love_count")
    public int loveCount;
}
