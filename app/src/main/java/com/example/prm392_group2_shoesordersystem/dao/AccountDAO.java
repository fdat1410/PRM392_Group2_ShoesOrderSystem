package com.example.prm392_group2_shoesordersystem.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.prm392_group2_shoesordersystem.entity.Account;

import java.util.List;

@Dao
public interface AccountDAO {
    @Query("SELECT * FROM Account WHERE role = 2 ")
    List<Account> ViewListAccountSeller();


}
