package com.example.prm392_group2_shoesordersystem.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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

    @Update
    void updateShoes(Shoes shoe);
    @Query("UPDATE Shoes SET shoes_name = :name, price = :price, img = :img, description = :description, category_id = :category_id WHERE shoes_id = :id")
    void updateShoesById(int id, String name, Double price, String img, String description, int category_id);

    @Query("SELECT * FROM Shoes WHERE shoes_id = :shoesId")
    Shoes getShoeById(int shoesId);
}

