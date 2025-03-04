package com.example.prm392_group2_shoesordersystem.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Order_detail", foreignKeys = {
        @ForeignKey(entity = Order.class, parentColumns = "order_id", childColumns = "order_id", onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Shoes.class, parentColumns = "shoes_id", childColumns = "shoes_id", onDelete = ForeignKey.CASCADE)
})
public class Order_detail {
    @PrimaryKey(autoGenerate = true)
    public int detail_id;
    public int shoes_id;
    public int order_id;
    public int size;
    public int quantity;
}

