package music;

public class BXHMusic {
    private String duration;
    private String id;

    private String img;
    private String path;
    private String title_author;
    private String title_music;

    public BXHMusic(String duration, String id, String img, String path, String title_author, String title_music) {
        this.duration = duration;
        this.id = id;
        this.img = img;
        this.path = path;
        this.title_author = title_author;
        this.title_music = title_music;
    }

    public BXHMusic(){

    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle_author() {
        return title_author;
    }

    public void setTitle_author(String title_author) {
        this.title_author = title_author;
    }

    public String getTitle_music() {
        return title_music;
    }

    public void setTitle_music(String title_music, String id) {
        this.title_music = title_music;
        this.id = id;
    }
}