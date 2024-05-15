package com.example.umusic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.umusic.PlaylistMusic;
import com.example.umusic.R;

import java.util.ArrayList;
import java.util.List;

import com.example.umusic.category.AllBXHCategory;
import com.example.umusic.category.AllBXHCategoryAdapter;
import com.example.umusic.category.BXHCategory;
import com.example.umusic.category.Category;
import com.example.umusic.category.CategoryAdapter;
import music.BXHMusic;
import music.Music;

public class HomeFragment extends Fragment {
    private ImageView btnMusicPlaylist;
    private RecyclerView rvCategory;
    private CategoryAdapter categoryAdapter;
    private RecyclerView rvBXH;
    private AllBXHCategoryAdapter allBXHCategoryAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        btnMusicPlaylist = view.findViewById(R.id.music_playlist_dowload);
        btnMusicPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PlaylistMusic.class);
                startActivity(intent);
            }
        });

        //category
        rvCategory = view.findViewById(R.id.rv_category);
        categoryAdapter = new CategoryAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        rvCategory.setLayoutManager(linearLayoutManager);

        categoryAdapter.setData(getListCategory());
        rvCategory.setAdapter(categoryAdapter);

        //bxh
        rvBXH = view.findViewById(R.id.rv_bxhcategory);
        allBXHCategoryAdapter = new AllBXHCategoryAdapter(getContext());
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        rvBXH.setLayoutManager(new LinearLayoutManager(getActivity()));

        allBXHCategoryAdapter.setData(getListAllBXHCategory());
        rvBXH.setAdapter(allBXHCategoryAdapter);

        return view;
    }

    private List<Category> getListCategory(){
        List<Category> listCategory = new ArrayList<>();

        List<Music> listMusic = new ArrayList<>();
        listMusic.add(new Music(R.drawable.a_loi, "À lôi","Double2T x Masew"));
        listMusic.add(new Music(R.drawable.bongphuhoa, "Bóng phù hoa","Phương Mỹ Chi"));
        listMusic.add(new Music(R.drawable.chungtacuahientai, "Chúng ta của hiện tại", "Sơn Tùng MTP"));
        listMusic.add(new Music(R.drawable.a_loi, "À lôi","Double2T x Masew"));
        listMusic.add(new Music(R.drawable.bongphuhoa, "Bóng phù hoa","Phương Mỹ Chi"));

        listCategory.add(new Category("Mùa giáng sinh", "Xem tất cả", listMusic));
        listCategory.add(new Category("Nghe gần đây", "Xem tất cả",listMusic));
        listCategory.add(new Category("Hit cũ mãi đỉnh", "Xem tất cả",listMusic));


        return listCategory;
    }

    private List<AllBXHCategory> getListAllBXHCategory(){
        List<AllBXHCategory> listallBXHCategory = new ArrayList<>();

        List<BXHMusic> listBXHMusic = new ArrayList<>();
        listBXHMusic.add(new BXHMusic(R.drawable.a_loi,"À lôi","1","Double2T x Masew"));
        listBXHMusic.add(new BXHMusic(R.drawable.bongphuhoa,"Bóng phù hoa","2","Phương Mỹ Chi"));
        listBXHMusic.add(new BXHMusic(R.drawable.chungtacuahientai,"Chúng ta của hiện tại","3","Sơn Tùng MTP"));

        List<BXHCategory> listBXHCategory = new ArrayList<>();
        listBXHCategory.add(new BXHCategory("Nhạc Việt",R.drawable.ic_play_black,listBXHMusic));
        listBXHCategory.add(new BXHCategory("Nhạc Âu Mỹ",R.drawable.ic_play_black,listBXHMusic));

        listallBXHCategory.add(new AllBXHCategory("Bảng xếp hạng",listBXHCategory));

        return listallBXHCategory;
    }



}