package com.example.prm392_group2_shoesordersystem.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.prm392_group2_shoesordersystem.entity.Account;

@Dao
public interface AccountDAO {
    @Insert
    void insertAccount(Account account);

    @Query("SELECT * FROM Account WHERE account_id = :accountId")
    Account getAccountById(int accountId);

    @Update
    void updateAccount(Account account);
}