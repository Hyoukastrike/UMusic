package com.example.umusic.category;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.umusic.MainActivity;
import com.example.umusic.R;

import java.util.ArrayList;
import java.util.List;

import music.Music;
import music.MusicAdapter;
import music.MyMediaPlayer;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private Context mContext;
    private List<Category> categories;
    private ArrayList<Music> mMusics;


    public CategoryAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Category> list){
        this.categories = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    @SuppressLint("RecyclerView")
    public void onBindViewHolder(@NonNull CategoryViewHolder holder,int position) {
        Category category = categories.get(position);
        if (category == null){
            return;
        }

        holder.tvNameCategory.setText(category.getNameCategory());
        holder.seeAllCategory.setText(category.getSeeAll());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext,RecyclerView.HORIZONTAL,false);
        holder.rvMusics.setLayoutManager(linearLayoutManager);

        MusicAdapter musicAdapter = new MusicAdapter(mMusics, mContext);
        musicAdapter.setData((ArrayList<Music>) category.getMusics());
        holder.rvMusics.setAdapter(musicAdapter);

    }

    @Override
    public int getItemCount() {
        if (categories != null){
            return categories.size();
        }
        return 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{

        private TextView tvNameCategory;
        private TextView seeAllCategory;
        private RecyclerView rvMusics;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNameCategory = itemView.findViewById(R.id.tv_name_category);
            seeAllCategory = itemView.findViewById(R.id.see_all);
            rvMusics = itemView.findViewById(R.id.rv_music);
        }
    }
}
