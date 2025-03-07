package com.example.prm392_group2_shoesordersystem.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.entity.Account;

import java.util.List;

public class AccountSellerAdapter extends RecyclerView.Adapter<AccountSellerAdapter.ViewHolder> {
    private List<Account> sellerList;

    public AccountSellerAdapter(List<Account> sellerList) {
        this.sellerList = sellerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seller, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Account seller = sellerList.get(position);
        holder.txtSellerName.setText(seller.getFullname());
        holder.txtSellerEmail.setText(seller.getEmail());
        holder.btnChangeStatus.setOnClickListener(v -> {
            // Xử lý sự kiện đổi trạng thái
        });
    }

    @Override
    public int getItemCount() {
        return sellerList.size();
    }

    public void updateData(List<Account> newSellerList) {
        this.sellerList = newSellerList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtSellerName, txtSellerEmail;
        Button btnChangeStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSellerName = itemView.findViewById(R.id.txtSellerName);
            txtSellerEmail = itemView.findViewById(R.id.txtSellerEmail);
            btnChangeStatus = itemView.findViewById(R.id.btnChangeStatus);
        }
    }
}
