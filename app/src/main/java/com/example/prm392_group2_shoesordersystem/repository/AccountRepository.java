package com.example.prm392_group2_shoesordersystem.repository;

import android.content.Context;

import com.example.prm392_group2_shoesordersystem.dao.AccountDAO;
import com.example.prm392_group2_shoesordersystem.dao.AppDatabase;
import com.example.prm392_group2_shoesordersystem.entity.Account;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AccountRepository {
    private AccountDAO accountDao;
    private ExecutorService executorService;

    public AccountRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        accountDao = database.accountDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    // Hàm lấy account theo ID(chạy trên background thread)
    public void getAccountById(int accountId, OnAccountFetchListener listener) {
        executorService.execute(() -> {
            Account account = accountDao.getAccountById(accountId);
            listener.onAccountFetched(account);
        });
    }

    // Interface callback để trả kết quả về UI thread
    public interface OnAccountFetchListener {
        void onAccountFetched(Account account);
    }

    public void updateAccount(Account account, OnAccountUpdateListener listener) {
        executorService.execute(() -> {
            try {
                accountDao.updateAccount(account);
                listener.onAccountUpdated(true);
            } catch (Exception e) {
                e.printStackTrace();
                listener.onAccountUpdated(false);
            }
        });
    }

    public interface OnAccountUpdateListener {
        void onAccountUpdated(boolean success);
    }

}
