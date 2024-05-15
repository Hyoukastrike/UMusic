package com.example.umusic.category;

import java.util.List;

public class AllBXHCategory {

    private String nameAllBXHCategory;
    private List<BXHCategory> AllBXHMusics;

    public AllBXHCategory(String nameAllBXHCategory, List<BXHCategory> allBXHMusics) {
        this.nameAllBXHCategory = nameAllBXHCategory;
        AllBXHMusics = allBXHMusics;
    }

    public String getNameAllBXHCategory() {
        return nameAllBXHCategory;
    }

    public void setNameAllBXHCategory(String nameAllBXHCategory) {
        this.nameAllBXHCategory = nameAllBXHCategory;
    }

    public List<BXHCategory> getAllBXHMusics() {
        return AllBXHMusics;
    }

    public void setAllBXHMusics(List<BXHCategory> allBXHMusics) {
        AllBXHMusics = allBXHMusics;
    }
}
