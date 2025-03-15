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
import com.example.prm392_group2_shoesordersystem.entity.Order_detail;
import com.example.prm392_group2_shoesordersystem.entity.Shoes;
import com.example.prm392_group2_shoesordersystem.repository.ShoesRepository;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder> {
    private List<Order_detail> orderDetails;
    private ShoesRepository shoesRepository;
    private Context context;
    public OrderDetailAdapter(Context context, List<Order_detail> orderDetails) {
        this.context = context;
        this.orderDetails = orderDetails;
        this.shoesRepository = new ShoesRepository(context);
    }

    @NonNull
    @Override
    public OrderDetailAdapter.OrderDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_detail_item, parent, false);
        return new OrderDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailAdapter.OrderDetailViewHolder holder, int position) {
        Shoes shoe = shoesRepository.getShoeById(orderDetails.get(position).shoes_id);
        holder.tvProductName.setText(shoe.shoes_name);
        holder.tvProductQuantity.setText("x" +String.valueOf(orderDetails.get(position).quantity));
        holder.tvProductPrice.setText(String.valueOf("$"+shoe.price * orderDetails.get(position).quantity));
        holder.tvProductSize.setText(String.valueOf("Size "+orderDetails.get(position).size));
        // Load ảnh bằng Picasso
        com.squareup.picasso.Picasso
                .get()
                .load(shoe.img) // Đường dẫn ảnh từ bộ nhớ trong
                .placeholder(R.drawable.ic_launcher_background) // Ảnh mặc định nếu chưa có ảnh
                .into(holder.imgProduct);
    }

    @Override
    public int getItemCount() {
        return orderDetails.size();
    }

    public class OrderDetailViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvProductSize, tvProductPrice, tvProductQuantity;
        ImageView imgProduct;
        public OrderDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            tvProductPrice = itemView.findViewById(R.id.tv_product_price);
            tvProductQuantity = itemView.findViewById(R.id.tv_product_quantity);
            imgProduct = itemView.findViewById(R.id.img_product);
            tvProductSize = itemView.findViewById(R.id.tv_product_size);
        }
    }
}

