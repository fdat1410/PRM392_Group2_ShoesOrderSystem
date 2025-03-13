package com.example.prm392_group2_shoesordersystem.repository;

import android.content.Context;

import com.example.prm392_group2_shoesordersystem.dao.AppDatabase;
import com.example.prm392_group2_shoesordersystem.dao.CategoryDAO;
import com.example.prm392_group2_shoesordersystem.dao.ShoesDAO;
import com.example.prm392_group2_shoesordersystem.entity.Category;
import com.example.prm392_group2_shoesordersystem.entity.Shoes;

import java.util.List;

public class ShoesRepository {
    ShoesDAO shoesDAO;
    CategoryDAO categoryDAO;
    public ShoesRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        shoesDAO = appDatabase.shoesDAO();
        categoryDAO = appDatabase.categoryDAO();
    }

    public void insertShoe(Shoes shoe) {
        shoesDAO.insertShoe(shoe);
    }

    public Shoes insertShoes(Shoes shoes) {
        long shoesId = shoesDAO.insertShoes(shoes);
        if (shoesId > 0) {
            return shoesDAO.getShoesById((int) shoesId);
        } else {
            return null;
        }
    }

    public List<Shoes> getAllShoes() {
        return shoesDAO.getAllShoes();
    }

    public List<Shoes> searchShoes(String keyword, Double minPrice, Double maxPrice, String categoryName) {
        Integer categoryId = null;
        if (categoryName != null && !categoryName.trim().isEmpty() && !categoryName.equals("All")) {
            Category category = categoryDAO.getCategoryByName(categoryName);
            if(category != null){
                categoryId = category.category_id;
            }
        }

        if (keyword != null && !keyword.trim().isEmpty()) {
            keyword = "%" + keyword + "%";
        } else {
            keyword = null;
        }

        if (minPrice != null && minPrice == 0) minPrice = null;
        if (maxPrice != null && maxPrice == 0) maxPrice = null;

        return shoesDAO.searchShoes(keyword, minPrice, maxPrice, categoryId);
    }
}