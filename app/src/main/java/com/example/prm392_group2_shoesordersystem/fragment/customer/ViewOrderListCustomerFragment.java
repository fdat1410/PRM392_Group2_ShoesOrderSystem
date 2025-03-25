package com.example.prm392_group2_shoesordersystem.fragment.customer;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

public class ViewOrderListCustomerFragment extends Fragment {
    private RecyclerView recyclerOrder;
    private OrderRepository orderRepository;
    private List<Order> orderList;
    private ListCustomerOrderAdapter listCustomerOrderAdapter;
    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_order_customer_fragment, container, false);
        recyclerOrder = view.findViewById(R.id.recyclerOrder);
        // Khởi tạo repository
        orderRepository = new OrderRepository(getContext());
        orderList = new ArrayList<>();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String accountJson = prefs.getString("USER_ACCOUNT", null);
        boolean isLoggedIn = prefs.getBoolean("LOGGED_IN", false);
        Gson gson = new Gson();
        Account account = gson.fromJson(accountJson, Account.class);

        loadOrderData(account.getAccount_id());

        listCustomerOrderAdapter = new ListCustomerOrderAdapter(orderList, getContext());
        recyclerOrder.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerOrder.setAdapter(listCustomerOrderAdapter);

        return view;
    }
    public void loadOrderData(int accountId) {
        new Thread(() -> {
            List<Order> orders = orderRepository.getOrderByAccountId(accountId);
            getActivity().runOnUiThread(() -> {
                orderList.clear();
                orderList.addAll(orders);
                listCustomerOrderAdapter.notifyDataSetChanged();
            });
        }).start();
    }

}
