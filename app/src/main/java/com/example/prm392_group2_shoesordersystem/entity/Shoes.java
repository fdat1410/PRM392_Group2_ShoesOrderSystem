package com.example.prm392_group2_shoesordersystem.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Shoes", foreignKeys = @ForeignKey(entity = Category.class, parentColumns = "category_id", childColumns = "category_id", onDelete = ForeignKey.CASCADE))
public class Shoes {
    @PrimaryKey(autoGenerate = true)
    public int shoes_id;
    public double price;
    public String img;
    public String description;
    public String shoes_name;
    public int shoes_status;
    public String create_by;
    public String update_by;
    public int category_id;

    public Shoes(double price, String img, String description, String shoes_name, int shoes_status, String create_by, String update_by, int category_id) {
        this.price = price;
        this.img = img;
        this.description = description;
        this.shoes_name = shoes_name;
        this.shoes_status = shoes_status;
        this.create_by = create_by;
        this.update_by = update_by;
        this.category_id = category_id;
    }
    public Shoes() {
    }

}