package com.example.prm392_group2_shoesordersystem.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.project.entity.Account;
@Dao
public interface AccountDAO {
    @Query("SELECT * FROM Account WHERE email = :email AND password = :password")
    Account login(String email, String password);

    @Query("SELECT * FROM Account WHERE email = :email")
    Account checkExistAccount(String email);

    @Insert
    void insertAccount(Account account);

    @Query("Update Account set password = :password where email = :email")
    void changePassword(String email, String password);
    @Query("UPDATE Account SET password = :password WHERE email = :email")
    void ForgotPassword(String email, String password);



}
