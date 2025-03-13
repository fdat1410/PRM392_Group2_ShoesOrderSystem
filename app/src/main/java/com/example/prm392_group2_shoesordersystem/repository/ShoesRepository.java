package com.example.prm392_group2_shoesordersystem.repository;

import android.content.Context;

import com.example.prm392_group2_shoesordersystem.dao.AppDatabase;
import com.example.prm392_group2_shoesordersystem.dao.ShoesDAO;
import com.example.prm392_group2_shoesordersystem.entity.Shoes;

import java.util.List;

public class ShoesRepository {
    ShoesDAO shoesDAO;
    public ShoesRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        shoesDAO = appDatabase.shoesDAO();
    }
    public void insertShoe(Shoes shoe) {
        shoesDAO.insertShoe(shoe);
    }
    public Shoes insertShoes(Shoes shoes) {
        long shoesId = shoesDAO.insertShoes(shoes); // Lấy ID vừa thêm

        if (shoesId > 0) {
            return shoesDAO.getShoesById((int) shoesId); // Lấy đối tượng Shoes đầy đủ
        } else {
            return null; // Trả về null nếu thêm thất bại
        }
    }
    public List<Shoes> getAllShoes() {
        return shoesDAO.getAllShoes();
    }
    public void updateShoes(Shoes shoe) {
        shoesDAO.updateShoes(shoe);
    }
    public void updateShoesById(int id, String name, Double price, String img, String description, int category_id) {
        shoesDAO.updateShoesById(id, name, price, img, description, category_id);
    }
}
