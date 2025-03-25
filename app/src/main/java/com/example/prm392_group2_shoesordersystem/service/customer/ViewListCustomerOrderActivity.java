package com.example.prm392_group2_shoesordersystem.service.customer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.adapter.ListCustomerOrderAdapter;
import com.example.prm392_group2_shoesordersystem.entity.Account;
import com.example.prm392_group2_shoesordersystem.entity.Order;
import com.example.prm392_group2_shoesordersystem.repository.OrderRepository;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ViewListCustomerOrderActivity extends AppCompatActivity {
    private RecyclerView recyclerOrder;
    private ImageView btnBack;
    private OrderRepository orderRepository;
    private List<Order> orderList;
    private ListCustomerOrderAdapter listCustomerOrderAdapter;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_customer_order);
        recyclerOrder = findViewById(R.id.recyclerOrder);
        btnBack = findViewById(R.id.btnBack);
        // Khởi tạo repository
        orderRepository = new OrderRepository(this);
        orderList = new ArrayList<>();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String accountJson = prefs.getString("USER_ACCOUNT", null);
        boolean isLoggedIn = prefs.getBoolean("LOGGED_IN", false);
        Gson gson = new Gson();
        Account account = gson.fromJson(accountJson, Account.class);


            loadOrderData(account.getAccount_id());


        listCustomerOrderAdapter = new ListCustomerOrderAdapter(orderList, this);
        recyclerOrder.setLayoutManager(new LinearLayoutManager(this));
        recyclerOrder.setAdapter(listCustomerOrderAdapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void loadOrderData(int accountId) {
        new Thread(() -> {
            List<Order> orders = orderRepository.getOrderByAccountId(accountId);
            runOnUiThread(() -> {
                orderList.clear();
                orderList.addAll(orders);
                listCustomerOrderAdapter.notifyDataSetChanged();
            });
        }).start();
    }

}