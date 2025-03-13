package com.example.prm392_group2_shoesordersystem.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group2_shoesordersystem.R;

import java.util.List;

public class ManagePageAdapter extends RecyclerView.Adapter<ManagePageAdapter.ViewHolder> {
    private Context context;
    private List<String> items;
    private List<Integer> icons;

    public ManagePageAdapter(Context context, List<String> items, List<Integer> icons) {
        this.context = context;
        this.items = items;
        this.icons = icons;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_manage_page, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(items.get(position));
        holder.icon.setImageResource(icons.get(position));
        holder.title.setText(items.get(position));
        holder.icon.setImageResource(icons.get(position));


        holder.itemView.setBackgroundColor(Color.parseColor("#fef7ff"));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textTitle);
            icon = itemView.findViewById(R.id.iconImage);
        }
    }
}
