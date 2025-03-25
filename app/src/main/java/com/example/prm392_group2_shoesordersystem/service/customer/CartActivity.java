package com.example.prm392_group2_shoesordersystem.service.customer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.adapter.CartAdapter;
import com.example.prm392_group2_shoesordersystem.entity.Account;
import com.example.prm392_group2_shoesordersystem.entity.Cart;
import com.example.prm392_group2_shoesordersystem.entity.Shoes;
import com.example.prm392_group2_shoesordersystem.repository.CartRepository;
import com.example.prm392_group2_shoesordersystem.repository.ShoesRepository;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnCartItemChangeListener {
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<Cart> cartItemList;
    private TextView tvTotalPrice;
    private Button btnProceedCheckout;
    private CartRepository cartRepository;
    private ShoesRepository shoesRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        shoesRepository = new ShoesRepository(this);
        cartRepository = new CartRepository(this);
        recyclerView = findViewById(R.id.recycler_cart);
        tvTotalPrice = findViewById(R.id.tv_total_price);
        btnProceedCheckout = findViewById(R.id.btn_proceed_checkout);


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String accountJson = prefs.getString("USER_ACCOUNT", null);
        Account account = new Gson().fromJson(accountJson, Account.class);


        cartItemList = cartRepository.listCartByAccount(account.getAccount_id());

        cartAdapter = new CartAdapter(this, cartItemList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cartAdapter);


        updateTotalPrice();


        btnProceedCheckout.setOnClickListener(v -> proceedToCheckout());
    }

    @Override
    public void onCartUpdated() {

        refreshCartData();
    }

    private void refreshCartData() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String accountJson = prefs.getString("USER_ACCOUNT", null);
        Account account = new Gson().fromJson(accountJson, Account.class);


        cartItemList.clear();
        cartItemList.addAll(cartRepository.listCartByAccount(account.getAccount_id()));

        cartAdapter.notifyDataSetChanged();
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        double totalAmount = 0;
        for (Cart cart : cartItemList) {
            Shoes shoe = shoesRepository.getShoeById(cart.getShoes_id());
            totalAmount += cart.getQuantity() * shoe.getPrice();
        }
        tvTotalPrice.setText(String.format("Total: %,d $", (int) totalAmount));
    }

    private void proceedToCheckout() {
        Intent intent = new Intent(CartActivity.this, CheckOutActivity.class);
        intent.putParcelableArrayListExtra("cart_list", (ArrayList<Cart>) cartItemList);
        startActivity(intent);
    }
}
