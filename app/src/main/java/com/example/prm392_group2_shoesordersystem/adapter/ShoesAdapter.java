package com.example.prm392_group2_shoesordersystem.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.activity.UpdateShoesInformationActivity;
import com.example.prm392_group2_shoesordersystem.entity.Shoes;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ShoesAdapter extends RecyclerView.Adapter<ShoesAdapter.ViewHolder>{
    private Context context;
    private List<Shoes> shoesList;
    public ShoesAdapter(Context context, List<Shoes> shoesList) {
        this.context = context;
        this.shoesList = shoesList;
    }

    @NonNull
    @Override
    public ShoesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shoes_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoesAdapter.ViewHolder holder, int position) {
        holder.tvId.setText(String.valueOf(shoesList.get(position).shoes_id));
        holder.tvName.setText(shoesList.get(position).shoes_name);
        holder.tvPrice.setText(String.valueOf(shoesList.get(position).price));
        // Load ảnh bằng Picasso
        com.squareup.picasso.Picasso
                .get()
                .load(shoesList.get(position).img) // Đường dẫn ảnh từ bộ nhớ trong
                .placeholder(R.drawable.ic_launcher_background) // Ảnh mặc định nếu chưa có ảnh
                .into(holder.img);
        if (holder.btnUpdate != null) {
            holder.btnUpdate.setOnClickListener(v -> {
                Intent intent = new Intent(context, UpdateShoesInformationActivity.class);
                intent.putExtra("USER_ID", shoesList.get(position).shoes_id);  // Truyền ID sản phẩm
                intent.putExtra("USER_NAME", shoesList.get(position).shoes_name);
                intent.putExtra("USER_EMAIL", shoesList.get(position).price);
                intent.putExtra("USER_PASSWORD", shoesList.get(position).img);
                context.startActivity(intent);
            });
        }else {
            Log.e("UserAdapter", "btnUpdate is null for position: " + position);
        }
    }

    @Override
    public int getItemCount() {
        return shoesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvName, tvPrice;
        ImageView img;
        Button btnUpdate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgProduct);
            tvId = itemView.findViewById(R.id.tvId);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
        }
    }
}
