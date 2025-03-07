package com.example.prm392_group2_shoesordersystem.repository;

import android.content.Context;

import com.example.project.dao.AccountDAO;
import com.example.project.dao.AppDatabase;
import com.example.project.entity.Account;

public class AccountRepository {
    AccountDAO accountDAO ;
    public AccountRepository(Context context){
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        accountDAO = appDatabase.accountDAO();
    }
    public Account login(String email, String password){
        return accountDAO.login(email,password);

    }

    public boolean insertAccount(Account account){
        accountDAO.insertAccount(account);
        return true;
    }

    public Account checkExistAccount(String email){
        return accountDAO.checkExistAccount(email);
    }

}

