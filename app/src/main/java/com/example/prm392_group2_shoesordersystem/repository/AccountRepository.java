package com.example.prm392_group2_shoesordersystem.repository;

import android.content.Context;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.prm392_group2_shoesordersystem.entity.Account;
import com.example.prm392_group2_shoesordersystem.dao.AccountDAO;
import com.example.prm392_group2_shoesordersystem.dao.AppDatabase;

public class AccountRepository {


    AccountDAO accountDAO ;
    private ExecutorService executorService;

    public AccountRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        accountDAO = database.accountDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public List<Account> ViewListAccountSeller() {
        return accountDAO.ViewListAccountSeller();
    }

    public void changeSellerAccountStatus(int accountId, int accStatus) {
        executorService.execute(() -> accountDAO.changeSellerAccountStatus(accStatus, accountId));
    }

    public Account login(String email, String password){
        return accountDAO.login(email,password);

    }

    public Account checkExistAccount(String email){
        return accountDAO.checkExistAccount(email);
    }




}

