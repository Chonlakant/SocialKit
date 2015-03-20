package co.aquario.socialkit.model;

/**
 * Created by root1 on 3/5/15.
 */
public class Live {

    String urlLive;
    String photoLive;
    String nameLive;
    String hours;
    String minutes;
    String seconds;
    String timestamp;

    public Live(String urlLive, String photoLive, String nameLive, String hours, String minutes, String seconds, String timestamp) {
        this.urlLive = urlLive;
        this.photoLive = photoLive;
        this.nameLive = nameLive;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.timestamp = timestamp;
    }

    public String getUrlLive() {
        return urlLive;
    }

    public String getPhotoLive() {
        return photoLive;
    }

    public String getNameLive() {
        return nameLive;
    }

    public String getHours() {
        return hours;
    }

    public String getMinutes() {
        return minutes;
    }

    public String getSeconds() {
        return seconds;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
