package com.example.prm392_group2_shoesordersystem.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.entity.Shoes;
import com.example.prm392_group2_shoesordersystem.service.guest.ViewShoesDetailActivity;
import com.example.prm392_group2_shoesordersystem.service.seller.UpdateShoesInformationActivity;

import java.util.List;

public class ShoeCustomerPageAdapter extends RecyclerView.Adapter<ShoeCustomerPageAdapter.ViewHolder> {
    private List<Shoes> shoesList;
    private Context context;

    public ShoeCustomerPageAdapter(List<Shoes> shoesList, Context context) {
        this.shoesList = shoesList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shoes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return shoesList.size();
    }

    public void updateData(List<Shoes> newShoesList) {
        this.shoesList = newShoesList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgShoe;
        TextView txtShoeName, txtShoePrice;
        Button btnAddToCart;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgShoe = itemView.findViewById(R.id.imgShoe);
            txtShoeName = itemView.findViewById(R.id.txtShoeName);
            txtShoePrice = itemView.findViewById(R.id.txtShoePrice);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
            cardView = itemView.findViewById(R.id.card);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Shoes shoe = shoesList.get(position);
        holder.txtShoeName.setText(shoe.shoes_name);
        holder.txtShoePrice.setText("Price: $" + shoe.price);
        holder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewShoesDetailActivity.class);
                intent.putExtra("SHOES_ID", shoesList.get(position).shoes_id);  // Truyền ID sản phẩm
                intent.putExtra("SHOES_NAME", shoesList.get(position).shoes_name);
                intent.putExtra("SHOES_PRICE", shoesList.get(position).price);
                intent.putExtra("SHOES_IMG", shoesList.get(position).img);
                intent.putExtra("SHOES_DESCRIPTION", shoesList.get(position).description);
                intent.putExtra("SHOES_CATEGORY_ID", shoesList.get(position).category_id);
                context.startActivity(intent);
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewShoesDetailActivity.class);
                intent.putExtra("SHOES_ID", shoesList.get(position).shoes_id);  // Truyền ID sản phẩm
                intent.putExtra("SHOES_NAME", shoesList.get(position).shoes_name);
                intent.putExtra("SHOES_PRICE", shoesList.get(position).price);
                intent.putExtra("SHOES_IMG", shoesList.get(position).img);
                intent.putExtra("SHOES_DESCRIPTION", shoesList.get(position).description);
                intent.putExtra("SHOES_CATEGORY_ID", shoesList.get(position).category_id);
                context.startActivity(intent);
            }
        });

        com.squareup.picasso.Picasso
                .get()
                .load(shoesList.get(position).img) // Đường dẫn ảnh từ bộ nhớ trong
                .placeholder(R.drawable.ic_launcher_background) // Ảnh mặc định nếu chưa có ảnh
                .into(holder.imgShoe);

    }
}
