package com.example.umusic.category;

import java.util.List;

import music.Music;

public class Category {
    private String nameCategory;

    private String seeAll;
    private List<Music> musics;

    public Category(String nameCategory, String seeAll, List<Music> musics) {
        this.nameCategory = nameCategory;
        this.seeAll = seeAll;
        this.musics = musics;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }
    public String getSeeAll() {
        return seeAll;
    }

    public void setSeeAll(String seeAll) {
        this.seeAll = seeAll;
    }
    public List<Music> getMusics() {
        return musics;
    }

    public void setMusics(List<Music> musics) {
        this.musics = musics;
    }
}
