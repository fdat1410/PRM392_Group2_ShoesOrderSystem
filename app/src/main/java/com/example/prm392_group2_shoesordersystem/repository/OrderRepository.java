package com.example.prm392_group2_shoesordersystem.repository;

import android.content.Context;

import com.example.prm392_group2_shoesordersystem.dao.AppDatabase;
import com.example.prm392_group2_shoesordersystem.dao.OrderDAO;
import com.example.prm392_group2_shoesordersystem.dao.ShoesDAO;
import com.example.prm392_group2_shoesordersystem.entity.Order;
import com.example.prm392_group2_shoesordersystem.entity.Shoes;
import com.example.prm392_group2_shoesordersystem.entity.ShoesOrderDetail;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderRepository {
    private OrderDAO orderDao;
    private ExecutorService executorService;
    public OrderRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        orderDao = database.orderDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public List<Order> getOrderByAccountId(int accountId) {
        return orderDao.getOrderByAccountId(accountId);
    }

    public List<ShoesOrderDetail> getShoesOrderDetails(int orderId) {
        return orderDao.getShoesOrderDetails(orderId);
    }
}
