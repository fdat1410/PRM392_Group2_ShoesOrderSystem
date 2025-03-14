package com.example.prm392_group2_shoesordersystem.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.prm392_group2_shoesordersystem.entity.Account;
import com.example.prm392_group2_shoesordersystem.entity.Shoes_Feedback;

import java.util.List;

@Dao
public interface FeedbackDAO {
    @Insert
    void insertFeedback(Shoes_Feedback feedback);
    @Query("SELECT * FROM Shoes_Feedback WHERE shoes_id = :shoesId")
    List<Shoes_Feedback> getFeedbackByShoesId(int shoesId);
}
