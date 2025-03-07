package com.example.prm392_group2_shoesordersystem.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.entity.Account;
import com.example.prm392_group2_shoesordersystem.repository.AccountRepository;

import java.util.List;

public class AccountSellerAdapter extends RecyclerView.Adapter<AccountSellerAdapter.ViewHolder> {
    private List<Account> sellerList;
    private AccountRepository accountRepository;
    private Context context;

    public AccountSellerAdapter(List<Account> sellerList, Context context) {
        this.sellerList = sellerList;
        this.context = context;
        this.accountRepository = new AccountRepository(context); // Đảm bảo repository luôn được khởi tạo
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seller, parent, false);
        return new ViewHolder(view);
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
        TextView txtSellerName, txtSellerEmail, txtStatus, txtDob, txtGender, txtAddress;
        Button btnChangeStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSellerName = itemView.findViewById(R.id.txtSellerName);
            txtSellerEmail = itemView.findViewById(R.id.txtSellerEmail);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtDob = itemView.findViewById(R.id.txtDob);
            txtGender = itemView.findViewById(R.id.txtGender);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            btnChangeStatus = itemView.findViewById(R.id.btnChangeStatus);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Account seller = sellerList.get(position);
        holder.txtSellerName.setText(seller.getFullname());
        holder.txtSellerEmail.setText(seller.getEmail());
        if (seller.acc_status == 0) {
            holder.txtStatus.setText("Unactive");
            holder.txtStatus.setTextColor(Color.RED);
        } else {
            holder.txtStatus.setText("Active");
            holder.txtStatus.setTextColor(Color.GREEN);
        }
        holder.txtDob.setText(seller.getDob());
        if (seller.getGender() == 0) {
            holder.txtStatus.setText("Female");

        } else {
            holder.txtStatus.setText("Male");

        }
        holder.txtAddress.setText(seller.getAddress());

        holder.btnChangeStatus.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Warning")
                    .setMessage("Are you sure you want to change status?")
                    .setPositiveButton("Yes", (dialog, which) -> changeSellerStatus(seller))
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                    .show();
        });
    }

    private void changeSellerStatus(Account seller) {
        int newStatus = (seller.acc_status == 1) ? 0 : 1;
        accountRepository.changeSellerAccountStatus(seller.account_id, newStatus);
        seller.acc_status = newStatus;
        notifyDataSetChanged();
        Toast.makeText(context, "Change Status successful!", Toast.LENGTH_SHORT).show();

    }
}
