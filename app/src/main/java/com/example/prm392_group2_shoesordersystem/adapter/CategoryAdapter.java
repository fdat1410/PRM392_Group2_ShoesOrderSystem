package com.example.prm392_group2_shoesordersystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.entity.Category;
import com.example.prm392_group2_shoesordersystem.repository.CategoryRepository;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private Context context;
    private List<Category> categories;
    private CategoryRepository categoryRepository;

    public CategoryAdapter(Context context, List<Category> categories, CategoryRepository categoryRepository) {
        this.context = context;
        this.categories = categories;
        this.categoryRepository = categoryRepository;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.tvNo.setText(String.valueOf(position + 1));
        holder.tvCategory.setText(categories.get(position).category_name);
        holder.btnEdit.setOnClickListener(v -> {
            // Xử lý khi người dùng nhấn nút "Edit"
        });
        holder.btnDelete.setOnClickListener(v -> {
            // Xử lý khi người dùng nhấn nút "Delete"
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategory, tvNo;
        ImageView btnEdit, btnDelete;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvNo = itemView.findViewById(R.id.tvNo);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
