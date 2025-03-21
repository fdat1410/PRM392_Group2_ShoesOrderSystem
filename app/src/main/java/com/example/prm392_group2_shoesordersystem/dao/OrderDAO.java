package com.example.prm392_group2_shoesordersystem.dao;

import com.example.prm392_group2_shoesordersystem.entity.Account;
import com.example.prm392_group2_shoesordersystem.entity.Order;
import com.example.prm392_group2_shoesordersystem.entity.MonthSale;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface OrderDAO {
    @Query("SELECT \n" +
            "    m.Month AS month, \n" +  // Đổi tên cột để trùng với trường trong MonthSale
            "    COALESCE(a.totalPrice, 0) AS totalPrice\n" +
            "FROM \n" +
            "    (SELECT 1 AS Month UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 \n" +
            "     UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 \n" +
            "     UNION ALL SELECT 9 UNION ALL SELECT 10 UNION ALL SELECT 11 UNION ALL SELECT 12) AS m\n" +
            "LEFT JOIN \n" +
            "    (SELECT \n" +
            "        SUM(totalPrice) AS totalPrice, \n" +
            "        CAST(strftime('%m', order_date) AS INTEGER) AS Month \n" +
            "     FROM `Order`\n" +
            "     GROUP BY strftime('%m', order_date)) AS a \n" +
            "ON m.Month = a.Month\n" +
            "ORDER BY m.Month")
    List<MonthSale> ListMonthSale();


    @Query("SELECT * FROM `Order`")
    List<Order> getAllOrders();


}

