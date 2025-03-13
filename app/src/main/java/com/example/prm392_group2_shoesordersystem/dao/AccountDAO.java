package com.example.prm392_group2_shoesordersystem.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.prm392_group2_shoesordersystem.entity.Account;

import java.util.List;

@Dao
public interface AccountDAO {
    @Query("SELECT * FROM Account WHERE role = 2 ")
    List<Account> ViewListAccountSeller();

    @Transaction
    @Query("UPDATE Account SET acc_status = :accStatus WHERE account_id = :accountId")
    void changeSellerAccountStatus(int accStatus, int accountId);


    @Query("SELECT * FROM Account WHERE email = :email AND password = :password")
    Account login(String email, String password);

    @Query("SELECT * FROM Account WHERE email = :email")
    Account checkExistAccount(String email);




}
