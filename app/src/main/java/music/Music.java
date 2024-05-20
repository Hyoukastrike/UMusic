package music;

import java.io.Serializable;

public class Music implements Serializable {

    private String duration;
    private String id;

    private String img;
    private String path;
    private String title_author;
    private String title_music;



    public Music(String img, String title_music, String title_author, String duration, String path, String id ) {
        this.img = img;
        this.id = id;
        this.title_music = title_music;
        this.title_author = title_author;
        this.duration = duration;
        this.path = path;
    }
    public Music() {

    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle_music() {
        return title_music;
    }

    public void setTitle_music(String title, String id) {
        this.title_music = title;
        this.id = id;
    }
    public String getTitle_author() {
        return title_author;
    }

    public void setTitle_author(String title_author) {
        this.title_author = title_author;
    }
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
