package com.example.prm392_group2_shoesordersystem.repository;

import android.content.Context;

import com.example.prm392_group2_shoesordersystem.dao.AppDatabase;
import com.example.prm392_group2_shoesordersystem.dao.Order_detailDAO;
import com.example.prm392_group2_shoesordersystem.entity.Order_detail;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderDetailRepository {
    Order_detailDAO orderDetailDAO ;



    private ExecutorService executorService;

    public OrderDetailRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        orderDetailDAO = appDatabase.orderDetailDAO();
    }

    public Order_detail createOrderDetai(Order_detail order) {
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            long orderId = orderDetailDAO.insertOrderDetail(order);
            order.setOrder_id((int) orderId);
        });
        return order;
    }
}
