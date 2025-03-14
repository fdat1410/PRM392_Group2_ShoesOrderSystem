package com.example.prm392_group2_shoesordersystem.repository;

import android.content.Context;

import com.example.prm392_group2_shoesordersystem.dao.AppDatabase;
import com.example.prm392_group2_shoesordersystem.dao.ShoesDAO;
import com.example.prm392_group2_shoesordersystem.entity.Shoes;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShoesRepository {
    private ShoesDAO shoesDao;
    private ExecutorService executorService;

    public ShoesRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        shoesDao = database.shoesDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    // Hàm lấy shoes theo ID(chạy trên background thread)
    public void getShoesById(int shoesId, ShoesRepository.OnShoesFetchListener listener) {
        executorService.execute(() -> {
            Shoes shoes = shoesDao.getShoesById(shoesId);
            listener.onShoesFetched(shoes);
        });
    }

    // Interface callback để trả kết quả về UI thread
    public interface OnShoesFetchListener {
        void onShoesFetched(Shoes shoes);
    }

}
