package com.example.prm392_group2_shoesordersystem.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;


import com.example.prm392_group2_shoesordersystem.entity.Account;
import com.example.prm392_group2_shoesordersystem.entity.CustomerSale;
import com.example.prm392_group2_shoesordersystem.entity.Shoes;


import java.sql.Date;
import java.util.List;

@Dao
public interface AccountDAO {
    @Query("SELECT * FROM Account WHERE role = 3")
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
        newAccount.setRole(3);
        newAccount.setPassword(password);

        return insertAccount(newAccount) > 0;
    }

    @Query("select a.account_id, a.fullName,a.fullname , SUM(o.totalPrice) as total_amount, \n" +
            "COUNT(DISTINCT od.order_id) * od.quantity AS item_bought, a.email\n" +
            "from [Order] o INNER JOIN [Order_detail] od\n" +
            "on o.order_id = od.order_id Inner join Account a \n" +
            "on o.account_id = a.account_id \n" +
            "INNER JOIN Shoes s ON od.shoes_id = s.shoes_id\n" +
            "where a.role=0 GROUP BY a.account_id, a.fullname, od.quantity order by total_amount DESC")
    List<CustomerSale> ListCustomerSale();


    @Query("SELECT * FROM Account WHERE role = 0")
    List<Account> getAllCustomerAccounts();
    @Query("UPDATE Account SET acc_status = :status WHERE account_id = :id")
    void UpdateAccountStatus(int id, int status);

    @Query("SELECT * FROM Account WHERE account_id = :accountId")
    Account getAccountById(int accountId);

    @Update
    void updateAccount(Account account);

    @Query("Update Account set password = :password where email = :email")
    void changePassword(String email, String password);

    @Query("UPDATE Account SET password = :password WHERE email = :email")
    void ForgotPassword(String email, String password);
    @Query("SELECT * FROM Account WHERE email = :email")
    Account getAccountByEmail(String email);




}
