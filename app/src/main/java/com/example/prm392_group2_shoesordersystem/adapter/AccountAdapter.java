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

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {
    private Context context;
    private List<Account> accounts;
    private AccountRepository accountRepository;

    public AccountAdapter(Context context, List<Account> accounts, AccountRepository accountRepository) {
        this.context = context;
        this.accounts = accounts;
        this.accountRepository = accountRepository;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_account_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvNo.setText(String.valueOf(position + 1));
        holder.tvName.setText(accounts.get(position).fullname);
        holder.tvEmail.setText(accounts.get(position).email);
        // Check status to set button text
        if (accounts.get(position).acc_status == 0) {
            holder.btnStatus.setText("UNBLOCK");
            holder.btnStatus.setSelected(true);
        } else {
            holder.btnStatus.setText("BLOCK");
            holder.btnStatus.setSelected(false);
        }
        holder.btnStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Confirmation");
                builder.setMessage(accounts.get(position).acc_status == 0 ? "Do you want to unblock this user?" : "Do you want to block this user?");
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    int newStatus = (accounts.get(position).acc_status == 0) ? 1 : 0; // Đảo trạng thái
                    try {
                        accountRepository.UpdateAccountStatus(accounts.get(position).account_id, newStatus);
                        accounts.get(position).acc_status = newStatus;
                        notifyItemChanged(position);
                        Toast.makeText(context, "Status updated successfully", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(context, "Error updating status", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvEmail, tvNo;
        Button btnStatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNo = itemView.findViewById(R.id.tvNo);
            tvName = itemView.findViewById(R.id.tvName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            btnStatus = itemView.findViewById(R.id.btnStatus);
        }
    }
}
