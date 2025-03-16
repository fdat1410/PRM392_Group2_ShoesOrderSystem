package com.example.prm392_group2_shoesordersystem.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.prm392_group2_shoesordersystem.entity.Category;
import com.example.prm392_group2_shoesordersystem.entity.CategorySale;


import java.util.List;

@Dao
public interface CategoryDAO {
    @Insert
    void insertCategory(Category category);
    @Query("SELECT * FROM Category")
    List<Category> getAllCategories();
    @Query("SELECT * FROM Category WHERE category_id = :categoryId")
    Category getCategoryById(int categoryId);

    @Query("SELECT c.category_id, COUNT(DISTINCT od.order_id) * od.quantity AS totalQuantity, c.category_name\n" +
            "FROM Order_detail od\n" +
            "INNER JOIN Shoes s ON od.shoes_id = s.shoes_id\n" +
            "INNER JOIN Category c ON c.category_id = s.category_id\n" +
            "GROUP BY c.category_id, c.category_name\n" +
            "ORDER BY totalQuantity DESC\n" +
            "LIMIT 5;\n")
    List<CategorySale> ListCategorySale();
}
