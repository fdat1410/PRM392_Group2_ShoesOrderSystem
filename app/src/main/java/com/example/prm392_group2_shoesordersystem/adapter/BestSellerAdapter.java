package com.example.prm392_group2_shoesordersystem.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.dao.BestSellerDAO;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BestSellerAdapter extends RecyclerView.Adapter<BestSellerAdapter.BestSellerViewHolder> {

    private List<BestSellerDAO.BestSellerShoes> bestSellerShoes;

    public BestSellerAdapter(List<BestSellerDAO.BestSellerShoes> bestSellerShoes) {
        this.bestSellerShoes = bestSellerShoes;
    }

    @NonNull
    @Override
    public BestSellerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_best_seller, parent, false);
        return new BestSellerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BestSellerViewHolder holder, int position) {
        BestSellerDAO.BestSellerShoes shoe = bestSellerShoes.get(position);
        holder.txtShoeName.setText(shoe.shoes_name);
        holder.txtTotalSold.setText("Total Sold: " + shoe.total_quantity);
        holder.ratingBar.setRating((float) shoe.average_rating);

        // Load ảnh bằng Picasso
        Picasso.get()
                .load(shoe.img)
                .placeholder(R.drawable.ic_launcher_background)             .into(holder.imgShoe);
    }

    @Override
    public int getItemCount() {
        return bestSellerShoes.size();
    }

    static class BestSellerViewHolder extends RecyclerView.ViewHolder {
        TextView txtShoeName;
        TextView txtTotalSold;
        RatingBar ratingBar;
        ImageView imgShoe;

        public BestSellerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtShoeName = itemView.findViewById(R.id.txtShoeName);
            txtTotalSold = itemView.findViewById(R.id.txtTotalSold);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            imgShoe = itemView.findViewById(R.id.imgShoe);
        }
    }
}