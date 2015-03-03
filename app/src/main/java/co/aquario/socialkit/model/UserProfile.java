package co.aquario.socialkit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mac on 3/3/15.
 */
public class UserProfile {
    @Expose
    public String id;
    @Expose
    public String name;
    @SerializedName("avatar_url")
    @Expose
    public String avatar;
    @SerializedName("cover_url")
    @Expose
    public String cover;
    @Expose
    public String username;
    @Expose
    public String email;
}
