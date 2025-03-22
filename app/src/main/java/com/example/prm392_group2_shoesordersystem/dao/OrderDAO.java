package com.example.prm392_group2_shoesordersystem.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.prm392_group2_shoesordersystem.entity.Order;
import com.example.prm392_group2_shoesordersystem.entity.ShoesOrderDetail;

import java.util.List;
import java.util.Map;

@Dao
public interface OrderDAO {
    @Query("SELECT * FROM [Order] WHERE account_id = :accountId")
    List<Order> getOrderByAccountId(int accountId);

    @Query("SELECT s.shoes_name, s.price, s.img, od.size, od.quantity " +
            "FROM Shoes s " +
            "JOIN Order_detail od ON s.shoes_id = od.shoes_id " +
            "WHERE od.order_id = :orderId")
    List<ShoesOrderDetail> getShoesOrderDetails(int orderId);

    @Query("SELECT * FROM [Order]")
    List<Order> getAllOrders();
}
