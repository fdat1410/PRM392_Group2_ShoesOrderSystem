package com.example.prm392_group2_shoesordersystem.entity;

public class MonthSale {
    private int month;
    private double totalPrice;

    public MonthSale() {
    }

    public MonthSale(int month, double totalPrice) {
        this.month = month;
        this.totalPrice = totalPrice;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
