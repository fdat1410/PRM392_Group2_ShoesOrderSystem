package com.example.prm392_group2_shoesordersystem.repository;

import android.content.Context;
import android.util.Log;

import com.example.prm392_group2_shoesordersystem.dao.AppDatabase;
import com.example.prm392_group2_shoesordersystem.dao.OrderDAO;
import com.example.prm392_group2_shoesordersystem.dao.Order_detailDAO;
import com.example.prm392_group2_shoesordersystem.dao.ShoesDAO;
import com.example.prm392_group2_shoesordersystem.entity.Order;
import com.example.prm392_group2_shoesordersystem.entity.Order_detail;
import com.example.prm392_group2_shoesordersystem.entity.Shoes;

import java.util.ArrayList;
import java.util.List;

public class OrderRepository {
    private OrderDAO orderDAO;
    private Order_detailDAO orderDetailDao;
    private ShoesDAO shoesDao;
    public OrderRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        orderDAO = appDatabase.orderDAO();
        orderDetailDao = appDatabase.orderDetailDAO();
        shoesDao = appDatabase.shoesDAO();
    }
    public List<Order> getAllOrders() {
        return orderDAO.getAllOrders();
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
}
