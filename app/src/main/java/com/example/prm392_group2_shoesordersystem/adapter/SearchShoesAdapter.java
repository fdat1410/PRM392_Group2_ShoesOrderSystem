package com.example.prm392_group2_shoesordersystem.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.activity.ViewShoesDetailActivity;
import com.example.prm392_group2_shoesordersystem.entity.Shoes;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchShoesAdapter extends RecyclerView.Adapter<SearchShoesAdapter.ViewHolder> {

    private Context context;
    private List<Shoes> shoesList;

    public SearchShoesAdapter(Context context, List<Shoes> shoesList) {
        this.context = context;
        this.shoesList = shoesList;
    }

    @NonNull
    @Override
    public SearchShoesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shoes_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchShoesAdapter.ViewHolder holder, int position) {
        Shoes shoe = shoesList.get(position);

        holder.tvName.setText(shoe.shoes_name);
        holder.tvPrice.setText(String.format("%,.0f", shoe.price));

        Picasso.get()
                .load(shoe.img)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.img);

        holder.tvName.setOnClickListener(v -> {
            Intent intent = new Intent(context, ViewShoesDetailActivity.class);
            intent.putExtra("shoes_name", shoe.shoes_name);
            intent.putExtra("shoes_price", shoe.price);
            intent.putExtra("shoes_description", shoe.description);
            intent.putExtra("shoes_img", shoe.img);
            context.startActivity(intent);
        });
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
