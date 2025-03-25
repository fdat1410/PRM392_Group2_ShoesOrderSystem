package com.example.prm392_group2_shoesordersystem.repository;


import android.content.Context;
import android.util.Log;

import com.example.prm392_group2_shoesordersystem.dao.AppDatabase;
import com.example.prm392_group2_shoesordersystem.dao.OrderDAO;

import com.example.prm392_group2_shoesordersystem.dao.Order_detailDAO;
import com.example.prm392_group2_shoesordersystem.dao.ShoesDAO;
import com.example.prm392_group2_shoesordersystem.entity.MonthSale;
import com.example.prm392_group2_shoesordersystem.entity.Order;
import com.example.prm392_group2_shoesordersystem.entity.Order_detail;
import com.example.prm392_group2_shoesordersystem.entity.Shoes;
import com.example.prm392_group2_shoesordersystem.entity.ShoesOrderDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class OrderRepository {
    OrderDAO orderDAO ;

    private Order_detailDAO orderDetailDao;
    private ShoesDAO shoesDao;
    private ExecutorService executorService;

    public OrderRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        orderDAO = appDatabase.orderDAO();
        orderDetailDao = appDatabase.orderDetailDAO();
        shoesDao = appDatabase.shoesDAO();
        executorService = Executors.newSingleThreadExecutor();
    }

    public List<MonthSale> ListMonthSale() {
        return orderDAO.ListMonthSale();
    }

    public List<Order_detail> getOrderDetails(int orderId) {
        if (orderDetailDao == null) {
            Log.e("OrderRepository", "order_detailDAO is NULL");
            return new ArrayList<>(); // Tránh NullPointerException
        }
        return orderDetailDao.getOrderDetailsByOrderId(orderId);
    }
    public Shoes getShoeById(int shoesId) {
        return shoesDao.getShoeById(shoesId);
    }

    public List<Order_detail> getOrderDetailTop1(int orderId) {
        if (orderDetailDao == null) {
            Log.e("OrderRepository", "order_detailDAO is NULL");
            return new ArrayList<>(); // Tránh NullPointerException
        }
        return orderDetailDao.getOrderDetailsByOrderIdTop1(orderId);
    }

    public List<Order> getAllOrders() {
        return orderDAO.getAllOrders();
    }

    public List<Order> getOrderByAccountId(int accountId) {
        return orderDAO.getOrderByAccountId(accountId);
    }

    public List<ShoesOrderDetail> getShoesOrderDetails(int orderId) {
        return orderDAO.getShoesOrderDetails(orderId);
    }
    public void deleteOrderById(int orderId) {
        executorService.execute(() -> orderDAO.deleteOrderWithDetails(orderId));
    }

    public Order createOrder(Order order) {
        executorService = Executors.newSingleThreadExecutor();
        Future<Long> future = executorService.submit(() -> orderDAO.insertOrder(order));

        try {
            long orderId = future.get();
            order.setOrder_id((int) orderId);
            return order;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void approveOrder(int orderId) {
        executorService.execute(() -> orderDAO.approveOrder(orderId));
    }



}
