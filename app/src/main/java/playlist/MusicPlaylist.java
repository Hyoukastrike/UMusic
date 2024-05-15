package playlist;

public class MusicPlaylist {

    private String imageurl;
    private String nameMusicPlaylist;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getNameMusicPlaylist() {
        return nameMusicPlaylist;
    }

    public void setNameMusicPlaylist(String nameMusicPlaylist, String id) {
        this.nameMusicPlaylist = nameMusicPlaylist;
        this.id = id;
    }
}
