package com.example.prm392_group2_shoesordersystem.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Size", foreignKeys = @ForeignKey(entity = Shoes.class, parentColumns = "shoes_id", childColumns = "shoe_id", onDelete = ForeignKey.CASCADE))
public class Size {
    @PrimaryKey(autoGenerate = true)
    public int size_id;
    public int size;
    public int quantity;
    public int shoe_id;

    public Size() {
    }



    public Size(int size, int quantity, int shoe_id) {
        this.size = size;
        this.quantity = quantity;
        this.shoe_id = shoe_id;
    }

    public int getSize_id() {
        return size_id;
    }

    public void setSize_id(int size_id) {
        this.size_id = size_id;
    }

    public int getShoe_id() {
        return shoe_id;
    }

    public void setShoe_id(int shoe_id) {
        this.shoe_id = shoe_id;
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
}
