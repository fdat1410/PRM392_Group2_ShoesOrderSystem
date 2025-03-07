package com.example.prm392_group2_shoesordersystem.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.prm392_group2_shoesordersystem.entity.Shoes;
import com.example.prm392_group2_shoesordersystem.entity.Size;

import java.util.List;

@Dao
public interface ShoesDAO {
    @Insert
    long insertShoe(Shoes shoe);

    @Insert
    void insertSizes(List<Size> sizes);
}

