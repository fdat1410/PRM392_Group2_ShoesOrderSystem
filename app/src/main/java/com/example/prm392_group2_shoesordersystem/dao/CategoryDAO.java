package com.example.prm392_group2_shoesordersystem.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.prm392_group2_shoesordersystem.entity.Category;

import java.util.List;

@Dao
public interface CategoryDAO {
    @Insert
    void insertCategory(Category category);
    @Query("SELECT * FROM Category")
    List<Category> getAllCategories();
    @Query("SELECT * FROM Category WHERE category_id = :categoryId")
    Category getCategoryById(int categoryId);
}
