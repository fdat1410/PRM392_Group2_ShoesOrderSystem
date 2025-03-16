package com.example.prm392_group2_shoesordersystem.repository;

import android.content.Context;

import com.example.prm392_group2_shoesordersystem.dao.AppDatabase;
import com.example.prm392_group2_shoesordersystem.dao.CategoryDAO;
import com.example.prm392_group2_shoesordersystem.entity.Category;
import com.example.prm392_group2_shoesordersystem.entity.CategorySale;

import java.util.List;

public class CategoryRepository {
    CategoryDAO categoryDAO;
    public CategoryRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        categoryDAO = appDatabase.categoryDAO();
    }
    public List<Category> getAllCategories() {
        return categoryDAO.getAllCategories();
    }

    public List<CategorySale> ListCategorySale() {
        return categoryDAO.ListCategorySale();
    }
}
