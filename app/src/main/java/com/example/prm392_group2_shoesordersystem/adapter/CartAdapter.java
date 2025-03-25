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
import com.example.prm392_group2_shoesordersystem.entity.Cart;
import com.example.prm392_group2_shoesordersystem.entity.Shoes;
import com.example.prm392_group2_shoesordersystem.repository.CartRepository;
import com.example.prm392_group2_shoesordersystem.repository.ShoesRepository;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Cart> cartItems;
    private OnCartItemChangeListener listener;

    private ShoesRepository shoesRepository;

    private CartRepository cartRepository;

    public interface OnCartItemChangeListener {
        void onCartUpdated();
    }

    public CartAdapter(Context context, List<Cart> cartItems, OnCartItemChangeListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
        this.shoesRepository = new ShoesRepository(context);
        this.cartRepository = new CartRepository(context);
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Cart item = cartItems.get(position);
        Shoes shoe = shoesRepository.getShoeById(item.getShoes_id());
        holder.tvProductName.setText(shoe.getShoes_name());
        holder.tvProductPrice.setText(shoe.getPrice() + " $");
        holder.tvQuantity.setText(String.valueOf(item.getQuantity()));
        String imgUrl = shoe.getImg();

        com.squareup.picasso.Picasso
                .get()
                .load(imgUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.imgProduct);



        holder.btnIncrease.setOnClickListener(v -> {
            cartRepository.UpdateQuanntityCartExist(item.getCart_id());
            notifyItemChanged(position);
            listener.onCartUpdated();
        });

        holder.btnDecrease.setOnClickListener(v -> {
            if (item.getQuantity() > 1) {
                cartRepository.DecreaseQuantity(item.getCart_id());
                notifyItemChanged(position);
                listener.onCartUpdated();
            }
        });

        holder.btnDelete.setOnClickListener(v -> {
            cartRepository.removeCartItem(item);
            notifyDataSetChanged();
            listener.onCartUpdated();
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct, btnIncrease, btnDecrease, btnDelete;
        TextView tvProductName, tvProductPrice, tvQuantity;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.img_product);
            btnIncrease = itemView.findViewById(R.id.btn_increase);
            btnDecrease = itemView.findViewById(R.id.btn_decrease);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            tvProductPrice = itemView.findViewById(R.id.tv_product_price);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
        }
    }
}
