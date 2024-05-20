package com.example.umusic.category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.umusic.R;

import java.util.ArrayList;
import java.util.List;

import music.BXHMusic;
import music.BXHMusicAdapter;

public class BXHCategoryAdapter extends RecyclerView.Adapter<BXHCategoryAdapter.BXHCategoryViewHolder> {
    private Context mContext;
    private List<BXHCategory> bxhCategories;

    public BXHCategoryAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<BXHCategory> list){
        this.bxhCategories = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BXHCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bxh_category,parent,false);
        return new BXHCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BXHCategoryViewHolder holder, int position) {
        BXHCategory bxhCategory = bxhCategories.get(position);
        if (bxhCategory == null){
            return;
        }

        holder.tvNameBXHCategory.setText(bxhCategory.getNameBXHCategory());
//        holder.imgPlayBtn.setImageResource(bxhCategory.getImgPlay());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext,RecyclerView.VERTICAL,false);
        holder.rvBXHMusics.setLayoutManager(linearLayoutManager);

        BXHMusicAdapter bxhMusicAdapter = new BXHMusicAdapter();
        bxhMusicAdapter.setData((ArrayList<BXHMusic>) bxhCategory.getBxhMusics(), mContext);
        holder.rvBXHMusics.setAdapter(bxhMusicAdapter);
    }

    @Override
    public int getItemCount() {
        if (bxhCategories != null){
            return bxhCategories.size();
        }
        return 0;
    }

    public class BXHCategoryViewHolder extends RecyclerView.ViewHolder{
        private TextView tvNameBXHCategory;
        private ImageView imgPlayBtn;
        private RecyclerView rvBXHMusics;

        public BXHCategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNameBXHCategory = itemView.findViewById(R.id.tv_name_category);
            imgPlayBtn = itemView.findViewById(R.id.btn_play);
            rvBXHMusics = itemView.findViewById(R.id.rv_bxhmusic);
        }
    }
}
