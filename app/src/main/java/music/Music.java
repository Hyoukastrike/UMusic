package music;

public class Music {
    private int resourceId;
    private String title_music;
    private String title_author;

    public Music(int resourceId, String title_music, String title_author ) {
        this.resourceId = resourceId;
        this.title_music = title_music;
        this.title_author = title_author;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getTitle() {
        return title_music;
    }

    public void setTitle(String title) {
        this.title_music = title;
    }
    public String getTitle_author() {
        return title_author;
    }

    public void setTitle_author(String title_author) {
        this.title_author = title_author;
    }
}
