package com.example.prm392_group2_shoesordersystem.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.entity.Order;
import com.example.prm392_group2_shoesordersystem.service.customer.TrackOrderActivity;

import java.util.List;

public class ListCustomerOrderAdapter extends RecyclerView.Adapter<ListCustomerOrderAdapter.OrderViewHolder> {
    private List<Order> orderList;
    private Context context;

    public ListCustomerOrderAdapter(List<Order> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    //    Tạo một ViewHolder mới khi RecyclerView cần hiển thị một item mới.
    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer_order, parent, false);
        return new OrderViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        Log.d("BIND_VIEW", "Binding item position: " + position);
        holder.tvOrderCode.setText(String.valueOf(order.getOrder_id()));
        holder.tvAddress.setText(order.getAddress());
        if (order.getOrd_status() == 0) {
            holder.tvStatus.setText("Pending Approval");
        } else if (order.getOrd_status() == 1) {
            holder.tvStatus.setText("Approved");
        } else {
            holder.tvStatus.setText("Unknown Status");
        }

        // Xử lý sự kiện click item
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TrackOrderActivity.class);
            intent.putExtra("order_status", order.getOrd_status());
            intent.putExtra("order_id", order.getOrder_id());
            intent.putExtra("address", order.getAddress());
            intent.putExtra("note", order.getNote());
            intent.putExtra("payment_status", order.getPayment_status());
            intent.putExtra("order_date", order.getOrder_date().toString());
            intent.putExtra("total_price", order.getTotalPrice());
            intent.putExtra("account_id", order.getAccount_id());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return orderList != null ? orderList.size() : 0;
    }

    // Lưu trữ các thành phần giao diện của mỗi item trong RecycleView
    public class OrderViewHolder extends RecyclerView.ViewHolder{
        TextView tvOrderCode, tvAddress, tvStatus;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderCode = itemView.findViewById(R.id.tvOrderCode);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}
