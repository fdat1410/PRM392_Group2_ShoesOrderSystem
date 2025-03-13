package com.example.prm392_group2_shoesordersystem.repository;

import android.content.Context;

import com.example.prm392_group2_shoesordersystem.dao.AccountDAO;
import com.example.prm392_group2_shoesordersystem.dao.AppDatabase;
import com.example.prm392_group2_shoesordersystem.entity.Account;

import java.util.List;

public class AccountRepository {
    private AccountDAO accountDAO;
    public AccountRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        accountDAO = appDatabase.accountDAO();
    }
    public List<Account> getAllCustomerAccounts() {
        return accountDAO.getAllCustomerAccounts();
    }
}
