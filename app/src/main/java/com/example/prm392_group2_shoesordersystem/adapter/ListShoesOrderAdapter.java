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
import java.util.Map;

public class ListShoesOrderAdapter extends RecyclerView.Adapter<ListShoesOrderAdapter.ShoesOrderViewHolder>{
    private List<ShoesOrderDetail> shoesOrderList;
    private Context context;

    public ListShoesOrderAdapter(List<ShoesOrderDetail> shoesOrderList, Context context) {
        this.shoesOrderList = shoesOrderList;
        this.context = context;
    }
    @NonNull
    @Override
    public ListShoesOrderAdapter.ShoesOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shoes_order, parent, false);
        return new ShoesOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListShoesOrderAdapter.ShoesOrderViewHolder holder, int position) {
        ShoesOrderDetail shoesOrder = shoesOrderList.get(position);

        holder.tvProductName.setText(shoesOrder.getShoesName());
        holder.tvPrice.setText("$" + shoesOrder.getPrice());
        holder.tvSize.setText("Size: " + shoesOrder.getSize());
        holder.tvQuantity.setText("x" + shoesOrder.getQuantity());
        // Lấy tên file ảnh từ database (ví dụ: "img1.png")
        String imageName = shoesOrder.getImg();

        // Chuyển tên file thành ID trong drawable
        int imageResId = context.getResources().getIdentifier(imageName.replace(".png", ""), "drawable", context.getPackageName());

        // Kiểm tra nếu ảnh tồn tại, hiển thị ảnh; nếu không, hiển thị ảnh mặc định
        if (imageResId != 0) {
            holder.ivShoesImage.setImageResource(imageResId);
        } else {
            holder.ivShoesImage.setImageResource(R.drawable.img_shoes); // Ảnh mặc định nếu không tìm thấy ảnh
        }
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
