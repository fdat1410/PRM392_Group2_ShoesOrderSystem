package com.example.prm392_group2_shoesordersystem.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.prm392_group2_shoesordersystem.entity.Account;
import com.example.prm392_group2_shoesordersystem.entity.Cart;
import com.example.prm392_group2_shoesordersystem.entity.Category;
import com.example.prm392_group2_shoesordersystem.entity.Order;
import com.example.prm392_group2_shoesordersystem.entity.Order_detail;
import com.example.prm392_group2_shoesordersystem.entity.Shoes;
import com.example.prm392_group2_shoesordersystem.entity.Shoes_Feedback;
import com.example.prm392_group2_shoesordersystem.entity.Size;

@Database(entities = {Account.class, Category.class, Shoes.class, Cart.class, Order.class, Order_detail.class, Shoes_Feedback.class, Size.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract AccountDAO accountDao();

    public abstract ShoesDAO shoesDAO();
    public abstract SizeDAO sizeDAO();

    public abstract CategoryDAO categoryDAO();



    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
