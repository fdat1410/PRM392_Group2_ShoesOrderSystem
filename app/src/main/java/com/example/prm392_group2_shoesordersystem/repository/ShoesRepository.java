package com.example.prm392_group2_shoesordersystem.repository;

import android.content.Context;

import com.example.prm392_group2_shoesordersystem.dao.AppDatabase;
import com.example.prm392_group2_shoesordersystem.dao.ShoesDAO;
import com.example.prm392_group2_shoesordersystem.entity.Shoes;

public class ShoesRepository {
    ShoesDAO shoesDAO;
    public ShoesRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        shoesDAO = appDatabase.shoesDAO();
    }
    public void insertShoe(Shoes shoe) {
        shoesDAO.insertShoe(shoe);
    }
}
