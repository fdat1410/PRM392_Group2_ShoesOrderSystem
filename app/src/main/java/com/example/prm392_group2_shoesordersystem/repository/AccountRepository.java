package com.example.prm392_group2_shoesordersystem.repository;

import android.content.Context;

import java.sql.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.prm392_group2_shoesordersystem.entity.Account;
import com.example.prm392_group2_shoesordersystem.dao.AccountDAO;
import com.example.prm392_group2_shoesordersystem.dao.AppDatabase;
import com.example.prm392_group2_shoesordersystem.entity.CustomerSale;
import com.example.prm392_group2_shoesordersystem.entity.MonthSale;

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

    public boolean CreateSellerAccount(String email, String password, String fullname, String phone, String address, String dob, int gender) {
        boolean[] result = new boolean[1];
        Thread thread = new Thread(() -> {
            result[0] = accountDAO.CreateSellerAccount(email, password, fullname, phone, address, dob, gender);
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return result[0];
    }

    public List<CustomerSale> ListCustomerSale() {
        return accountDAO.ListCustomerSale();
    }

    public void UpdateAccountStatus(int id, int status) {
        accountDAO.UpdateAccountStatus(id, status);
    }

    public List<Account> getAllCustomerAccounts() {
        return accountDAO.getAllCustomerAccounts();
    }




}

