package com.example.prm392_group2_shoesordersystem.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.repository.ShoesRepository;
import com.example.prm392_group2_shoesordersystem.service.UpdateShoesInformationActivity;
import com.example.prm392_group2_shoesordersystem.entity.Shoes;

import java.util.List;

public class ShoesAdapter extends RecyclerView.Adapter<ShoesAdapter.ViewHolder>{
    private Context context;
    private List<Shoes> shoesList;
    private ShoesRepository shoesRepository;
    public ShoesAdapter(Context context, List<Shoes> shoesList) {
        this.context = context;
        this.shoesList = shoesList;
        this.shoesRepository = new ShoesRepository(context);
    }


    @NonNull
    @Override
    public ShoesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shoes_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoesAdapter.ViewHolder holder, int position) {
        holder.tvId.setText(String.valueOf(position+1));
        holder.tvName.setText(shoesList.get(position).shoes_name);
        holder.tvPrice.setText("$"+String.valueOf(shoesList.get(position).price));
        if(shoesList.get(position).shoes_status == 0) {
            holder.tvStatus.setText("Unactive");
            holder.tvStatus.setTextColor(Color.RED);
        } else {
            holder.tvStatus.setText("Active");
            holder.tvStatus.setTextColor(Color.GREEN);
        }

        holder.btnDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Warning")
                    .setMessage("Are you sure you want to change status?")
                    .setPositiveButton("Yes", (dialog, which) -> UpdateShoesStatus(shoesList.get(position)))
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                    .show();
        });


        // Load ảnh bằng Picasso
        com.squareup.picasso.Picasso
                .get()
                .load(shoesList.get(position).img) // Đường dẫn ảnh từ bộ nhớ trong
                .placeholder(R.drawable.ic_launcher_background) // Ảnh mặc định nếu chưa có ảnh
                .into(holder.img);
        if (holder.btnUpdate != null) {
            holder.btnUpdate.setOnClickListener(v -> {
                Intent intent = new Intent(context, UpdateShoesInformationActivity.class);
                intent.putExtra("SHOES_ID", shoesList.get(position).shoes_id);  // Truyền ID sản phẩm
                intent.putExtra("SHOES_NAME", shoesList.get(position).shoes_name);
                intent.putExtra("SHOES_PRICE", shoesList.get(position).price);
                intent.putExtra("SHOES_IMG", shoesList.get(position).img);
                intent.putExtra("SHOES_DESCRIPTION", shoesList.get(position).description);
                intent.putExtra("SHOES_CATEGORY_ID", shoesList.get(position).category_id);
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
        TextView tvId, tvName, tvPrice, tvStatus;
        ImageView img;
        Button btnUpdate, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgProduct);
            tvId = itemView.findViewById(R.id.tvId);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
    private void UpdateShoesStatus(Shoes shoes) {
        int newStatus = (shoes.getShoes_status() == 1) ? 0 : 1;
        shoesRepository.UpdateShoesStatus(shoes.getShoes_id(), newStatus);
        shoes.setShoes_status(newStatus);
        notifyDataSetChanged();
        Toast.makeText(context, "Change Status successful!", Toast.LENGTH_SHORT).show();

    }

}
