package co.aquario.socialkit.util;

/**
 * Created by Mac on 3/3/15.
 */
public class EndpointManager {

    public static String prefix = "https://www.vdomax.com";

    public static String getPath(String path) {
        return prefix + "/" + path;
    }
}
