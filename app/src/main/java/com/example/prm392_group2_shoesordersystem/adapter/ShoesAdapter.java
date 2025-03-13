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
import com.example.prm392_group2_shoesordersystem.entity.Shoes;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ShoesAdapter extends RecyclerView.Adapter<ShoesAdapter.ViewHolder> {

    private Context context;
    private List<Shoes> shoesList;

    public ShoesAdapter(Context context, List<Shoes> shoesList) {
        this.context = context;
        this.shoesList = shoesList;
    }

    @NonNull
    @Override
    public ShoesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shoes_search, parent, false); // Sử dụng layout search
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoesAdapter.ViewHolder holder, int position) {
        Shoes shoe = shoesList.get(position);

        holder.tvName.setText(shoe.shoes_name);
        holder.tvPrice.setText(String.valueOf(shoe.price));

        Picasso.get()
                .load(shoe.img)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return shoesList != null ? shoesList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgShoes);
            tvName = itemView.findViewById(R.id.txtShoesName);
            tvPrice = itemView.findViewById(R.id.txtPrice);
        }
    }
}