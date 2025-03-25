package com.example.prm392_group2_shoesordersystem.repository;

import android.app.Application;

import com.example.prm392_group2_shoesordersystem.dao.AppDatabase;
import com.example.prm392_group2_shoesordersystem.dao.BestSellerDAO;

import java.util.List;

public class BestSellerRepository {

    private BestSellerDAO bestSellerDAO;

    public BestSellerRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        bestSellerDAO = database.bestSellerDAO();
    }

    public List<BestSellerDAO.BestSellerShoes> getBestSellerShoes() {
        return bestSellerDAO.getBestSellerShoes();
    }
}