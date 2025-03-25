package com.example.prm392_group2_shoesordersystem.entity;

import androidx.room.ColumnInfo;

public class ShoesOrderDetail {
    @ColumnInfo(name = "shoes_name")
    public String shoesName;

    @ColumnInfo(name = "price")
    public double price;

    @ColumnInfo(name = "img")
    public String img;

    @ColumnInfo(name = "size")
    public int size;

    @ColumnInfo(name = "quantity")
    public int quantity;

    public String getShoesName() {
        return shoesName;
    }

    public void setShoesName(String shoesName) {
        this.shoesName = shoesName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
