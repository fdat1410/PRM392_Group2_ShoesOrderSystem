package com.example.prm392_group2_shoesordersystem.service;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.adapter.OrderAdapter;
import com.example.prm392_group2_shoesordersystem.entity.Order;
import com.example.prm392_group2_shoesordersystem.repository.OrderRepository;

import java.util.List;

public class ViewOrderListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private OrderRepository orderRepository;
    private List<Order> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_order_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        orderRepository = new OrderRepository(this);

        loadOrders();
    }
    private void loadOrders() {
        new Thread(() -> {
            orderList = orderRepository.getAllOrders(); // Lấy danh sách Order từ database
            runOnUiThread(() -> {
                orderAdapter = new OrderAdapter(this, orderList, orderRepository);
                recyclerView.setAdapter(orderAdapter);
            });
        }).start();
    }
}