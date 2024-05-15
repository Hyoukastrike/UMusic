package com.example.umusic.category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.umusic.R;

import java.util.List;

public class AllBXHCategoryAdapter extends RecyclerView.Adapter<AllBXHCategoryAdapter.AllBXHCategoryViewHolder>{
    private Context mContext;
    private List<AllBXHCategory> allBXHCategories;

    public AllBXHCategoryAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<AllBXHCategory> list){
        this.allBXHCategories = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AllBXHCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_allbxh_category,parent,false);
        return new AllBXHCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllBXHCategoryViewHolder holder, int position) {
        AllBXHCategory allBXHCategory = allBXHCategories.get(position);
        if (allBXHCategory == null){
            return;
        }

        holder.tvNameAllBXHCategory.setText(allBXHCategory.getNameAllBXHCategory());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext,RecyclerView.HORIZONTAL,false);
        holder.rvAllBXHMusics.setLayoutManager(linearLayoutManager);

        BXHCategoryAdapter bxhCategoryAdapter = new BXHCategoryAdapter(mContext);
        bxhCategoryAdapter.setData(allBXHCategory.getAllBXHMusics());
        holder.rvAllBXHMusics.setAdapter(bxhCategoryAdapter);
    }

    @Override
    public int getItemCount() {
        if (allBXHCategories != null){
            return allBXHCategories.size();
        }
        return 0;
    }

    public class AllBXHCategoryViewHolder extends RecyclerView.ViewHolder{
        private TextView tvNameAllBXHCategory;
        private RecyclerView rvAllBXHMusics;

        public AllBXHCategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNameAllBXHCategory = itemView.findViewById(R.id.tv_name_allcategory);
            rvAllBXHMusics = itemView.findViewById(R.id.rv_allmusic);
        }
    }
}
