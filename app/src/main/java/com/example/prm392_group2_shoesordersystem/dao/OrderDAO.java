package com.example.prm392_group2_shoesordersystem.dao;

import com.example.prm392_group2_shoesordersystem.entity.Account;
import com.example.prm392_group2_shoesordersystem.entity.Order;
import com.example.prm392_group2_shoesordersystem.entity.MonthSale;
import com.example.prm392_group2_shoesordersystem.entity.ShoesOrderDetail;

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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertOrder(Order order);

    @Query("UPDATE `Order` SET ord_status = 1 WHERE order_id = :orderId")
    void approveOrder(int orderId);



}

