package com.example.prm392_group2_shoesordersystem.dao;

import static android.icu.text.ListFormatter.Type.OR;

import static java.sql.Types.NULL;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.prm392_group2_shoesordersystem.entity.Shoes;

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

    @Query("SELECT * FROM Shoes WHERE " +
            "(:keyword IS NULL OR shoes_name LIKE :keyword) AND " +
            "(:minPrice IS NULL OR price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR price <= :maxPrice) AND " +
            "(:categoryId IS NULL OR category_id = :categoryId)")
    List<Shoes> searchShoes(String keyword, Double minPrice, Double maxPrice, Integer categoryId);
}

