package com.example.prm392_group2_shoesordersystem.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Order_detail", foreignKeys = {
        @ForeignKey(entity = Order.class, parentColumns = "order_id", childColumns = "order_id", onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Shoes.class, parentColumns = "shoes_id", childColumns = "shoes_id", onDelete = ForeignKey.CASCADE)
})
public class Order_detail {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int detail_id;
    @NonNull
    public int shoes_id;
    @NonNull
    public int order_id;
    @NonNull
    public int size;
    @NonNull
    public int quantity;

    public Order_detail() {
    }

    public Order_detail(int detail_id, int quantity, int size, int order_id, int shoes_id) {
        this.detail_id = detail_id;
        this.quantity = quantity;
        this.size = size;
        this.order_id = order_id;
        this.shoes_id = shoes_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getShoes_id() {
        return shoes_id;
    }

    public void setShoes_id(int shoes_id) {
        this.shoes_id = shoes_id;
    }

    public int getDetail_id() {
        return detail_id;
    }

    public void setDetail_id(int detail_id) {
        this.detail_id = detail_id;
    }
}

