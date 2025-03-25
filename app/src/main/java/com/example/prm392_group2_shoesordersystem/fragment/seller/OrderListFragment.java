package com.example.prm392_group2_shoesordersystem.fragment.seller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.adapter.OrderAdapter;
import com.example.prm392_group2_shoesordersystem.entity.Order;
import com.example.prm392_group2_shoesordersystem.repository.OrderRepository;

import java.util.List;

public class OrderListFragment extends Fragment {
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private OrderRepository orderRepository;
    private List<Order> orderList;
    Context context = getContext();
    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_account_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        orderRepository = new OrderRepository(context);
        loadOrders();
        return view;
    }
    private void loadOrders() {
        new Thread(() -> {
            orderList = orderRepository.getAllOrders(); // Lấy danh sách Order từ database
            getActivity().runOnUiThread(() -> {
                orderAdapter = new OrderAdapter(context, orderList, orderRepository);
                recyclerView.setAdapter(orderAdapter);
            });
        }).start();
    }
}
