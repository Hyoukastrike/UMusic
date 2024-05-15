package playlist;

import java.util.HashMap;
import java.util.Map;

public class PlaylistPlaylist {
    private String id;

    private String namePlaylistPlaylist;
    public PlaylistPlaylist(){

    }

    public PlaylistPlaylist(String id, String namePlaylistPlaylist) {
        this.id = id;
        this.namePlaylistPlaylist = namePlaylistPlaylist;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamePlaylistPlaylist() {
        return namePlaylistPlaylist;
    }

    public void setNamePlaylistPlaylist(String namePlaylistPlaylist, String id) {
        this.namePlaylistPlaylist = namePlaylistPlaylist;
        this.id = id;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", namePlaylistPlaylist);

        return result;
    }
}
