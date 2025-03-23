package com.example.prm392_group2_shoesordersystem.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.prm392_group2_shoesordersystem.entity.Order;
import com.example.prm392_group2_shoesordersystem.entity.ShoesOrderDetail;
import com.example.prm392_group2_shoesordersystem.entity.Shoes_Feedback;

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

    @Query("DELETE FROM Order_detail WHERE order_id = :orderId")
    void deleteOrderDetailsByOrderId(int orderId);

    @Query("DELETE FROM `Order` WHERE order_id = :orderId")
    void deleteOrderById(int orderId);

    default void deleteOrderWithDetails(int orderId) {
        deleteOrderDetailsByOrderId(orderId);
        deleteOrderById(orderId);
    }
}
