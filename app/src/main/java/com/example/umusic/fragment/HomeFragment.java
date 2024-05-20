package com.example.umusic.fragment;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import music.BXHMusic;
import music.Music;

public class HomeFragment extends Fragment {
    private ImageView btnMusicPlaylist;
    private RecyclerView rvCategory;
    private CategoryAdapter categoryAdapter;
    private RecyclerView rvBXH;
    private AllBXHCategoryAdapter allBXHCategoryAdapter;
    MediaPlayer mediaPlayer;
    List<AllBXHCategory> listallBXHCategory = new ArrayList<>();
    List<Music> listMusic = new ArrayList<>();
    List<BXHMusic> listBXHMusicViet = new ArrayList<>();
    List<BXHMusic> listBXHMusicAuMy = new ArrayList<>();

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

    private List<AllBXHCategory> getListAllBXHCategoryFromRealtimeDatabase(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("songs_online");
        DatabaseReference refBXH = ref.child("BXHCategory");

        refBXH.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    BXHCategory bxhCategory = dataSnapshot.getValue(BXHCategory.class);
                    List<BXHCategory> bxhCategories = new ArrayList<>();
                    bxhCategories.add(bxhCategory);
                    listallBXHCategory.add(new AllBXHCategory("Bảng xếp hạng",bxhCategories));
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return listallBXHCategory;
    }

    private List<Category> getListCategory(){
        List<Category> listCategory = new ArrayList<>();

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("songs_online");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (listMusic != null){
                    listMusic.clear();
                }

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Music music = dataSnapshot.getValue(Music.class);
                    if (dataSnapshot.exists()){
                        String name = dataSnapshot.child("title_music").getValue().toString();
                        String author = dataSnapshot.child("title_author").getValue().toString();
                        String id = dataSnapshot.child("id").getValue().toString();
                        music.setImg(dataSnapshot.child("img").getValue().toString());
                        music.setTitle_music(name, id);
                        music.setTitle_author(author);
                        music.setPath(dataSnapshot.child("path").getValue().toString());
                        music.setDuration(dataSnapshot.child("duration").getValue().toString());

                        listMusic.add(music);
                    }

                    categoryAdapter.notifyDataSetChanged();
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"No data",Toast.LENGTH_SHORT).show();
            }
        });


//        listMusic.add(new Music("", "À lôi","Double2T x Masew","3:12",""));
//        listMusic.add(new Music(R.drawable.bongphuhoa, "Bóng phù hoa","Phương Mỹ Chi","3:12",""));
//        listMusic.add(new Music(R.drawable.chungtacuahientai, "Chúng ta của hiện tại", "Sơn Tùng MTP","3:12",""));
//        listMusic.add(new Music(R.drawable.a_loi, "À lôi","Double2T x Masew","3:12",""));
//        listMusic.add(new Music(R.drawable.bongphuhoa, "Bóng phù hoa","Phương Mỹ Chi","3:12",""));

        listCategory.add(new Category("Mùa giáng sinh", "Xem tất cả", listMusic));
        listCategory.add(new Category("Nghe gần đây", "Xem tất cả",listMusic));
        listCategory.add(new Category("Hit cũ mãi đỉnh", "Xem tất cả",listMusic));


        return listCategory;
    }

    private List<AllBXHCategory> getListAllBXHCategory(){
        List<AllBXHCategory> listallBXHCategory = new ArrayList<>();

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("songs_BXH");
        DatabaseReference dbrefViet = ref.child("Nhac_Viet");
        DatabaseReference dbrefAuMy = ref.child("Nhac_Au_My");

        dbrefViet.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (listBXHMusicViet != null){
                    listBXHMusicViet.clear();
                }

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    BXHMusic bxhMusic = dataSnapshot.getValue(BXHMusic.class);
                    if (dataSnapshot.exists()){
                        String name = dataSnapshot.child("title_music").getValue().toString();
                        String author = dataSnapshot.child("title_author").getValue().toString();
                        String id = dataSnapshot.child("id").getValue().toString();
                        bxhMusic.setImg(dataSnapshot.child("img").getValue().toString());
                        bxhMusic.setTitle_music(name, id);
                        bxhMusic.setTitle_author(author);
                        bxhMusic.setPath(dataSnapshot.child("path").getValue().toString());
                        bxhMusic.setDuration(dataSnapshot.child("duration").getValue().toString());

                        listBXHMusicViet.add(bxhMusic);
                    }

                    allBXHCategoryAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"No data",Toast.LENGTH_SHORT).show();
            }
        });

        dbrefAuMy.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (listBXHMusicAuMy != null){
                    listBXHMusicAuMy.clear();
                }

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    BXHMusic bxhMusic = dataSnapshot.getValue(BXHMusic.class);
                    if (dataSnapshot.exists()){
                        String name = dataSnapshot.child("title_music").getValue().toString();
                        String author = dataSnapshot.child("title_author").getValue().toString();
                        String id = dataSnapshot.child("id").getValue().toString();
                        bxhMusic.setImg(dataSnapshot.child("img").getValue().toString());
                        bxhMusic.setTitle_music(name, id);
                        bxhMusic.setTitle_author(author);
                        bxhMusic.setPath(dataSnapshot.child("path").getValue().toString());
                        bxhMusic.setDuration(dataSnapshot.child("duration").getValue().toString());

                        listBXHMusicAuMy.add(bxhMusic);
                    }

                    allBXHCategoryAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"No data",Toast.LENGTH_SHORT).show();
            }
        });
//        listBXHMusic.add(new BXHMusic(R.drawable.a_loi,"À lôi","1","Double2T x Masew"));
//        listBXHMusic.add(new BXHMusic(R.drawable.bongphuhoa,"Bóng phù hoa","2","Phương Mỹ Chi"));
//        listBXHMusic.add(new BXHMusic(R.drawable.chungtacuahientai,"Chúng ta của hiện tại","3","Sơn Tùng MTP"));

        List<BXHCategory> listBXHCategory = new ArrayList<>();
        listBXHCategory.add(new BXHCategory("Nhạc Việt", R.drawable.ic_play_black,listBXHMusicViet));
        listBXHCategory.add(new BXHCategory("Nhạc Âu Mỹ", R.drawable.ic_play_black,listBXHMusicAuMy));

        listallBXHCategory.add(new AllBXHCategory("Bảng xếp hạng",listBXHCategory));

        return listallBXHCategory;
    }



}