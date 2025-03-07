package com.example.prm392_group2_shoesordersystem.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Account")
public class Account {
    @PrimaryKey(autoGenerate = true)
    public int account_id;
    public String email;
    public String password;
    public String fullname;
    public String phone;
    public int acc_status;
    public String address;
    public String dob;
    public int gender;
    public int role;
    public String update_by;
    public String create_by;

    public Account() {
    }

    public Account(int account_id, String create_by, String update_by, String email, String password, String fullname, String phone, int acc_status, String address, String dob, int gender, int role) {
        this.account_id = account_id;
        this.create_by = create_by;
        this.update_by = update_by;
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.phone = phone;
        this.acc_status = acc_status;
        this.address = address;
        this.dob = dob;
        this.gender = gender;
        this.role = role;
    }




    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public String getUpdate_by() {
        return update_by;
    }

    public void setUpdate_by(String update_by) {
        this.update_by = update_by;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAcc_status() {
        return acc_status;
    }

    public void setAcc_status(int acc_status) {
        this.acc_status = acc_status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
