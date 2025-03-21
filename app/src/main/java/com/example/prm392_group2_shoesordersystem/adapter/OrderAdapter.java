package com.example.prm392_group2_shoesordersystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.entity.Order;
import com.example.prm392_group2_shoesordersystem.entity.Order_detail;
import com.example.prm392_group2_shoesordersystem.entity.Shoes;
import com.example.prm392_group2_shoesordersystem.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<Order> orders;
    private OrderRepository orderRepository;
    private Context context;

    public OrderAdapter(Context context, List<Order> orders, OrderRepository orderRepository) {
        this.context = context;
        this.orders = orders;
        this.orderRepository = orderRepository;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.tvOrderId.setText("Order ID: #" + order.order_id);
        //holder.tvOrderStatus.setText((order.ord_status == 1 ? "Done" : "Processing"));
        if (order.ord_status == 1) {
            holder.tvOrderStatus.setText("Done");
            holder.tvOrderStatus.setSelected(true);  // Áp dụng màu xanh
        } else {
            holder.tvOrderStatus.setText("Processing");
            holder.tvOrderStatus.setSelected(false); // Áp dụng màu vàng
        }
        holder.tvTotalPrice.setText("Total: $" + order.totalPrice);

        new Thread(() -> {
            List<Order_detail> details = orderRepository.getOrderDetails(order.order_id);
            List<Order_detail> detail_top1 = orderRepository.getOrderDetailTop1(order.order_id);
            List<Shoes> shoesList = new ArrayList<>();

            for (Order_detail detail : details) {
                Shoes shoe = orderRepository.getShoeById(detail.shoes_id);
                shoesList.add(shoe);
            }
            holder.recyclerView.post(() -> {
                OrderDetailAdapter adapter = new OrderDetailAdapter(holder.itemView.getContext(), detail_top1);
                holder.recyclerView.setAdapter(adapter);
                holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
            });
            OrderDetailAdapter adapter = new OrderDetailAdapter(holder.itemView.getContext(), details);

            // Cập nhật sự kiện khi bấm nút "See More"
            holder.btnSeeMore.post(() -> {
                holder.btnSeeMore.setOnClickListener(v -> {
                    if(holder.recyclerView.getVisibility() == View.VISIBLE){
                        holder.recyclerView.post(() -> {
                            //OrderDetailAdapter adapterFull = new OrderDetailAdapter(holder.itemView.getContext(), details);
                            holder.recyclerView.setAdapter(adapter);
                            holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
                        });
                        holder.recyclerView.setVisibility(View.VISIBLE);
                        holder.btnSeeMore.setText("");
                    }
                });
            });

        }).start();
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId, tvTotalPrice, tvOrderStatus;
        RecyclerView recyclerView;
        TextView btnSeeMore;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tvId);
            tvTotalPrice = itemView.findViewById(R.id.tv_total_price);
            tvOrderStatus = itemView.findViewById(R.id.tv_order_status);
            recyclerView = itemView.findViewById(R.id.recycler_view_order_items);
            btnSeeMore = itemView.findViewById(R.id.tv_see_more);
        }
    }
}

