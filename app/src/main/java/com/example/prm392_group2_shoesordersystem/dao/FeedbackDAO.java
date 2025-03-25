package com.example.prm392_group2_shoesordersystem.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.prm392_group2_shoesordersystem.entity.Shoes_Feedback;

import java.util.List;

@Dao
public interface FeedbackDAO {
    @Insert
    void insertFeedback(Shoes_Feedback feedback);
    @Query("SELECT * FROM Shoes_Feedback WHERE shoes_id = :shoesId")
    List<Shoes_Feedback> getFeedbackByShoesId(int shoesId);
    @Query("SELECT COUNT(*) FROM Order_detail od JOIN [Order] o ON od.order_id = o.order_id WHERE o.account_id = :accountId AND od.shoes_id = :shoesId")
    int hasPurchasedProduct(int accountId, int shoesId);

    // Kiểm tra xem tài khoản đã đánh giá sản phẩm này chưa
    @Query("SELECT COUNT(*) FROM Shoes_Feedback WHERE account_id = :accountId AND shoes_id = :shoesId")
    int hasAlreadyReviewed(int accountId, int shoesId);
    @Query("SELECT role FROM Account WHERE account_id = :accountId")
    int getUserRole(int accountId);
}
