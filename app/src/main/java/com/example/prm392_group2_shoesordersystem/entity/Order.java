package com.example.prm392_group2_shoesordersystem.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName = "Order", foreignKeys = @ForeignKey(entity = Account.class, parentColumns = "account_id", childColumns = "account_id", onDelete = ForeignKey.CASCADE))
public class Order {
    @PrimaryKey(autoGenerate = true)
    public int order_id;
    @NonNull
    public String order_date;

    @NonNull
    public String note;

    @NonNull
    public double totalPrice;

    @NonNull
    public int ord_status;
    @NonNull
    public int payment_status;

    @NonNull
    public String address;

    @NonNull
    public int account_id;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getOrd_status() {
        return ord_status;
    }

    public void setOrd_status(int ord_status) {
        this.ord_status = ord_status;
    }

    public int getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(int payment_status) {
        this.payment_status = payment_status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }


}
