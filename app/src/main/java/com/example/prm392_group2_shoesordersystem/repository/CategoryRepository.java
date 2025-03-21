package com.example.prm392_group2_shoesordersystem.repository;

import android.content.Context;

import com.example.prm392_group2_shoesordersystem.dao.AppDatabase;
import com.example.prm392_group2_shoesordersystem.dao.CategoryDAO;
import com.example.prm392_group2_shoesordersystem.entity.Category;

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
    public Category getCategoryById(int categoryId) {
        return categoryDAO.getCategoryById(categoryId);
    }
    public void insertCategory(Category category) {
        categoryDAO.insertCategory(category);
    }
    public void updateCategory(int categoryId, String categoryName) {
        categoryDAO.updateCategory(categoryId, categoryName);
    }
    public void deleteCategory(int categoryId) {
        categoryDAO.deleteCategory(categoryId);
    }
}
