package com.example.prm392_group2_shoesordersystem.repository;

import android.content.Context;

import com.example.prm392_group2_shoesordersystem.dao.AppDatabase;
import com.example.prm392_group2_shoesordersystem.dao.OrderDAO;
import com.example.prm392_group2_shoesordersystem.entity.Order;

import java.util.List;

public class OrderRepository {
    private OrderDAO orderDAO;
    public OrderRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        orderDAO = appDatabase.orderDAO();
    }
    public List<Order> getAllOrders() {
        return orderDAO.getAllOrders();
    }
}
