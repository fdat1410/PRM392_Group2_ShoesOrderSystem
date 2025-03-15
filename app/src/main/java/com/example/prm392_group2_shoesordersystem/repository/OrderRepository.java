package com.example.prm392_group2_shoesordersystem.repository;


import android.content.Context;

import com.example.prm392_group2_shoesordersystem.dao.AppDatabase;
import com.example.prm392_group2_shoesordersystem.dao.OrderDAO;

import com.example.prm392_group2_shoesordersystem.entity.MonthSale;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderRepository {
    OrderDAO orderDAO ;
    private ExecutorService executorService;

    public OrderRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        orderDAO = database.orderDAO();
        executorService = Executors.newSingleThreadExecutor();
    }

    public List<MonthSale> ListMonthSale() {
        return orderDAO.ListMonthSale();
    }


}
