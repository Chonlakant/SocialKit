package co.aquario.socialkit.model;

import com.google.gson.annotations.Expose;

/**
 * Created by Mac on 3/3/15.
 */
public class UserProfile {
    @Expose
    public String id;
    @Expose
    public String name;
    @Expose
    public String avatar;
    @Expose
    public String cover;
}
