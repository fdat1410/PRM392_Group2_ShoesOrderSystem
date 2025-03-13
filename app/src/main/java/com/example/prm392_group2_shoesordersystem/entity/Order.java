package com.example.prm392_group2_shoesordersystem.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName = "Order", foreignKeys = @ForeignKey(entity = Account.class, parentColumns = "account_id", childColumns = "account_id", onDelete = ForeignKey.CASCADE))
public class Order {
    @PrimaryKey(autoGenerate = true)
    public int order_id;
    public String order_date;
    public String note;
    public double totalPrice;
    public int ord_status;
    public int payment_status;
    public String address;
    public int account_id;
}
