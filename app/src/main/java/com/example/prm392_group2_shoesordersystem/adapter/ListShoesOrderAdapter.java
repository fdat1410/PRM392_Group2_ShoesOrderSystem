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
import com.example.prm392_group2_shoesordersystem.entity.ShoesOrderDetail;

import java.util.List;

public class ListShoesOrderAdapter extends RecyclerView.Adapter<ListShoesOrderAdapter.ShoesOrderViewHolder>{
    private List<ShoesOrderDetail> shoesOrderList;
    private Context context;

    public ListShoesOrderAdapter(List<ShoesOrderDetail> shoesOrderList, Context context) {
        this.shoesOrderList = shoesOrderList;
        this.context = context;
    }
    @NonNull
    @Override
    public ShoesOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shoes_order, parent, false);
        return new ShoesOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoesOrderViewHolder holder, int position) {
        ShoesOrderDetail shoesOrder = shoesOrderList.get(position);

        holder.tvProductName.setText(shoesOrder.getShoesName());
        holder.tvPrice.setText("$" + shoesOrder.getPrice());
        holder.tvSize.setText("Size: " + shoesOrder.getSize());
        holder.tvQuantity.setText("x" + shoesOrder.getQuantity());

        String imageName = shoesOrder.getImg();


        int imageResId = context.getResources().getIdentifier(imageName.replace(".png", ""), "drawable", context.getPackageName());
        // Load ảnh bằng Picasso
        com.squareup.picasso.Picasso
                .get()
                .load(shoesOrderList.get(position).img) // Đường dẫn ảnh từ bộ nhớ trong
                .placeholder(R.drawable.ic_launcher_background) // Ảnh mặc định nếu chưa có ảnh
                .into(holder.ivShoesImage);

    }

    @Override
    public int getItemCount() {
        return shoesOrderList != null ? shoesOrderList.size() : 0;
    }
    public static class ShoesOrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvPrice, tvSize, tvQuantity;
        ImageView ivShoesImage;

        public ShoesOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvSize = itemView.findViewById(R.id.tvSize);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            ivShoesImage = itemView.findViewById(R.id.ivShoesImage);
        }
    }
}
