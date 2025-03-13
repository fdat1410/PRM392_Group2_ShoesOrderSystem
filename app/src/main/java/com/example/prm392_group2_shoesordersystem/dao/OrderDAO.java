package com.example.prm392_group2_shoesordersystem.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.prm392_group2_shoesordersystem.entity.Order;

import java.util.List;

@Dao
public interface OrderDAO {
    @Query("SELECT * FROM `Order`")
    List<Order> getAllOrders();
}
