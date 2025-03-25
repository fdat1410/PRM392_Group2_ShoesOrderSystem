package com.example.prm392_group2_shoesordersystem.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.entity.CategorySale;


import java.util.List;

public class CategoryQuantityAdapter extends RecyclerView.Adapter<CategoryQuantityAdapter.ViewHolder> {
    private List<CategorySale> categorySaleList;

    public CategoryQuantityAdapter(List<CategorySale> categorySaleList) {
        this.categorySaleList = categorySaleList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_sale, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategorySale data = categorySaleList.get(position);
        holder.txtCategory.setText(String.valueOf(data.getCategory_name()));



        holder.txtQuantity.setText(String.valueOf(data.getTotalQuantity()));
    }

    @Override
    public int getItemCount() {
        return categorySaleList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCategory, txtQuantity;

        ViewHolder(View itemView) {
            super(itemView);
            txtCategory = itemView.findViewById(R.id.txtCategory);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
        }
    }
}

