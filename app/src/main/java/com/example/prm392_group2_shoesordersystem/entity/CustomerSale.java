package com.example.prm392_group2_shoesordersystem.entity;

public class CustomerSale {
    private int account_id;
    private String email;
    private String fullname;
    private int item_bought;
    private double total_amount;

    public CustomerSale() {
    }

    public CustomerSale(int account_id, double total_amount, int item_bought, String fullname, String email) {
        this.account_id = account_id;
        this.total_amount = total_amount;
        this.item_bought = item_bought;
        this.fullname = fullname;
        this.email = email;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public int getItem_bought() {
        return item_bought;
    }

    public void setItem_bought(int item_bought) {
        this.item_bought = item_bought;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
