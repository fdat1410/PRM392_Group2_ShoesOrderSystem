package com.example.prm392_group2_shoesordersystem.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.prm392_group2_shoesordersystem.entity.Size;

import java.util.List;

@Dao
public interface SizeDAO {
    @Insert
    void insertSize(Size size);
    @Insert
    void insertSizes(List<Size> sizes);
}
