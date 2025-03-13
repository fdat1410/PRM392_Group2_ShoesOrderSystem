package com.example.prm392_group2_shoesordersystem.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;


import com.example.prm392_group2_shoesordersystem.entity.Account;

import java.sql.Date;
import java.util.List;

@Dao
public interface AccountDAO {
    @Query("SELECT * FROM Account WHERE role = 2 ")
    List<Account> ViewListAccountSeller();

    default boolean CreateSellerAccount(String email, String password,
                                        String fullname, String phone, String address, Date dob,
                                        int gender) {
        return false;
    }

    @Transaction
    @Query("UPDATE Account SET acc_status = :accStatus WHERE account_id = :accountId")
    void changeSellerAccountStatus(int accStatus, int accountId);


    @Query("SELECT * FROM Account WHERE email = :email AND password = :password")
    Account login(String email, String password);

    @Query("SELECT * FROM Account WHERE email = :email")
    Account checkExistAccount(String email);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertAccount(Account account);

    @Transaction
    default boolean CreateSellerAccount(String email, String password, String fullname, String phone, String address, String dob, int gender) {
        Account newAccount = new Account();
        newAccount.setAcc_status(1);
        newAccount.setAddress(address);
        newAccount.setDob(dob);
        newAccount.setEmail(email);
        newAccount.setFullname(fullname);
        newAccount.setGender(gender);
        newAccount.setPhone(phone);
        newAccount.setRole(2);
        newAccount.setPassword(password);

        return insertAccount(newAccount) > 0;
    }







}
