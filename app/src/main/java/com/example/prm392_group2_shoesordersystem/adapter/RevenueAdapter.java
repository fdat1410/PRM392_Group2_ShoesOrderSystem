package com.example.prm392_group2_shoesordersystem.adapter;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.entity.MonthSale;

import java.util.List;

public class RevenueAdapter extends RecyclerView.Adapter<RevenueAdapter.ViewHolder> {
    private List<MonthSale> revenueList;

    public RevenueAdapter(List<MonthSale> revenueList) {
        this.revenueList = revenueList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_revenue, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MonthSale data = revenueList.get(position);
        holder.txtMonth.setText(String.valueOf(data.getMonth()));

        holder.txtRevenue.setText(data.getTotalPrice() + "đ");
    }

    @Override
    public int getItemCount() {
        return revenueList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMonth, txtRevenue;

        ViewHolder(View itemView) {
            super(itemView);
            txtMonth = itemView.findViewById(R.id.txtMonth);
            txtRevenue = itemView.findViewById(R.id.txtRevenue);
        }
    }
}
