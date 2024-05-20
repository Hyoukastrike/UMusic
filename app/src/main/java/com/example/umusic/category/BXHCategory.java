package com.example.umusic.category;

import android.widget.ImageView;

import java.util.List;

import music.BXHMusic;

public class BXHCategory {


    private String nameBXHCategory;
    private int imgPlay;
    private List<BXHMusic> bxhMusics;

    public BXHCategory(String nameBXHCategory, int imgPlay, List<BXHMusic> bxhMusics) {
        this.nameBXHCategory = nameBXHCategory;
        this.imgPlay = imgPlay;
        this.bxhMusics = bxhMusics;
    }

    public BXHCategory(){

    }

    public String getNameBXHCategory() {
        return nameBXHCategory;
    }

    public void setNameBXHCategory(String nameBXHCategory) {
        this.nameBXHCategory = nameBXHCategory;
    }

    public int getImgPlay() {
        return imgPlay;
    }

    public void setImgPlay(int imgPlay) {
        this.imgPlay = imgPlay;
    }

    public List<BXHMusic> getBxhMusics() {
        return bxhMusics;
    }

    public void setBxhMusics(List<BXHMusic> bxhMusics) {
        this.bxhMusics = bxhMusics;
    }
}
