package com.example.prm392_group2_shoesordersystem.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.prm392_group2_shoesordersystem.entity.Shoes;
import com.example.prm392_group2_shoesordersystem.entity.Size;

import java.util.List;

@Dao
public interface ShoesDAO {
    @Insert
    void insertShoe(Shoes shoe);

    @Insert
    long insertShoes(Shoes shoes);

    @Query("SELECT * FROM Shoes WHERE shoes_id = :shoesId")
    Shoes getShoesById(int shoesId);
    @Query("SELECT * FROM Shoes")
    List<Shoes> getAllShoes();
}

