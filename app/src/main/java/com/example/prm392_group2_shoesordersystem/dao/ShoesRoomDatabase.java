//package com.example.prm392_group2_shoesordersystem.dao;
//
//import android.content.Context;
//
//import androidx.room.Database;
//import androidx.room.Room;
//import androidx.room.RoomDatabase;
//
//import com.example.prm392_group2_shoesordersystem.entity.Shoes;
//
//@Database(entities = {Shoes.class}, version = 1, exportSchema = false)
//public abstract class ShoesRoomDatabase extends RoomDatabase {
//    private static ShoesRoomDatabase instance;
//    public abstract ShoesDAO shoesDAO();
//    public static final String DATABASE_NAME = "shoesRoomDatabase";
//    public static synchronized ShoesRoomDatabase getInstance(Context context) {
//        if(instance == null){
//            instance = Room.databaseBuilder(context.getApplicationContext(),
//                            ShoesRoomDatabase.class, DATABASE_NAME)
//                    .fallbackToDestructiveMigration()
//                    .allowMainThreadQueries()
//                    .build();
//        }
//        return instance;
//    }
//}
