package com.example.prm392_group2_shoesordersystem.dao;

import androidx.room.Dao;
import androidx.room.Query;
import java.util.List;

@Dao
public interface BestSellerDAO {

    @Query("SELECT s.shoes_name, SUM(od.quantity) AS total_quantity, AVG(sf.star) AS average_rating, s.img " +
            "FROM Shoes s JOIN Order_detail od ON s.shoes_id = od.shoes_id " +
            "LEFT JOIN Shoes_Feedback sf ON s.shoes_id = sf.shoes_id " +
            "GROUP BY s.shoes_id " +
            "ORDER BY total_quantity DESC " +
            "LIMIT 5")
    List<BestSellerShoes> getBestSellerShoes();

    public class BestSellerShoes {
        public String shoes_name;
        public int total_quantity;
        public double average_rating;
        public String img;
    }
}