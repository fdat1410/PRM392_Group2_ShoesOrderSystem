package com.example.prm392_group2_shoesordersystem.dao;

import androidx.room.Dao;
import androidx.room.Query;
import com.example.prm392_group2_shoesordersystem.entity.Shoes;

@Dao
public interface ShoesDAO {
    @Query("SELECT * FROM Shoes WHERE shoes_id = :shoesId")
    Shoes getShoesById(int shoesId);
}
