package com.example.prm392_group2_shoesordersystem.fragment.customer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.adapter.CartAdapter;
import com.example.prm392_group2_shoesordersystem.adapter.ShoeCustomerPageAdapter;
import com.example.prm392_group2_shoesordersystem.entity.Account;
import com.example.prm392_group2_shoesordersystem.entity.Cart;
import com.example.prm392_group2_shoesordersystem.entity.Shoes;
import com.example.prm392_group2_shoesordersystem.repository.CartRepository;
import com.example.prm392_group2_shoesordersystem.repository.ShoesRepository;
import com.example.prm392_group2_shoesordersystem.service.customer.CheckOutActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment implements CartAdapter.OnCartItemChangeListener{
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<Cart> cartItemList;
    private TextView tvTotalPrice;
    private Button btnProceedCheckout;
    private CartRepository cartRepository;
    private ShoesRepository shoesRepository;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cart_fragment, container, false);
        shoesRepository = new ShoesRepository(getContext());
        cartRepository = new CartRepository(getContext());
        recyclerView = view.findViewById(R.id.recycler_cart);
        tvTotalPrice = view.findViewById(R.id.tv_total_price);
        btnProceedCheckout = view.findViewById(R.id.btn_proceed_checkout);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String accountJson = prefs.getString("USER_ACCOUNT", null);
        Account account = new Gson().fromJson(accountJson, Account.class);

        cartItemList = cartRepository.listCartByAccount(account.getAccount_id());
        cartAdapter = new CartAdapter(getContext(), cartItemList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(cartAdapter);

        updateTotalPrice();

        btnProceedCheckout.setOnClickListener(v -> proceedToCheckout());

        return view;
    }
    @Override
    public void onCartUpdated() {
        refreshCartData();
    }

    private void refreshCartData() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
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
        Intent intent = new Intent(getActivity(), CheckOutActivity.class);
        intent.putParcelableArrayListExtra("cart_list", (ArrayList<Cart>) cartItemList);
        startActivity(intent);
    }

}
