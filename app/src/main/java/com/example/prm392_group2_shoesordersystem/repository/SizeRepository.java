package com.example.prm392_group2_shoesordersystem.repository;

import android.content.Context;

import com.example.prm392_group2_shoesordersystem.dao.AppDatabase;
import com.example.prm392_group2_shoesordersystem.dao.SizeDAO;
import com.example.prm392_group2_shoesordersystem.entity.Size;

public class SizeRepository {
    SizeDAO sizeDAO;
    public SizeRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        sizeDAO = appDatabase.sizeDAO();
    }
    public void insertSize(Size size) {
        sizeDAO.insertSize(size);
    }
}
