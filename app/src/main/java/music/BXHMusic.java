package music;

public class BXHMusic {
    private int resourceId;
    private String title_music;
    private String nums;
    private String title_author;

    public BXHMusic(int resourceId, String title_music, String nums, String title_author) {
        this.resourceId = resourceId;
        this.title_music = title_music;
        this.nums = nums;
        this.title_author = title_author;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getTitle_music() {
        return title_music;
    }

    public void setTitle_music(String title_music) {
        this.title_music = title_music;
    }

    public String getNums() {
        return nums;
    }

    public void setNums(String nums) {
        this.nums = nums;
    }

    public String getTitle_author() {
        return title_author;
    }

    public void setTitle_author(String title_author) {
        this.title_author = title_author;
    }
}
