package co.aquario.socialkit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import co.aquario.socialkit.util.EndpointManager;

/**
 * Created by Mac on 3/10/15.
 */
public class User extends BaseModel {
    @Expose
    public String id;
    @Expose
    public String username;
    @Expose
    public String about;
    @Expose
    public String active;
    @Expose
    public String email;
    @Expose
    public String language;
    @SerializedName("last_logged")
    @Expose
    public String lastLogged;
    @Expose
    public String name;
    @Expose
    public String time;
    @Expose
    public String timestamp;
    @Expose
    public String timezone;
    @Expose
    public String type;
    @Expose
    public String verified;
    @Expose
    public String avatar;
    @Expose
    public String cover;

    public String getAvatarUrl() {
        return EndpointManager.getPath(avatar);
    }

    public String getCoverUrl() {
        return EndpointManager.getPath(cover);
    }
}
