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

    public int getShoes_id() {
        return shoes_id;
    }

    public void setShoes_id(int shoes_id) {
        this.shoes_id = shoes_id;
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

    public String getShoes_name() {
        return shoes_name;
    }

    public void setShoes_name(String shoes_name) {
        this.shoes_name = shoes_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public int getShoes_status() {
        return shoes_status;
    }

    public void setShoes_status(int shoes_status) {
        this.shoes_status = shoes_status;
    }

    public String getUpdate_by() {
        return update_by;
    }

    public void setUpdate_by(String update_by) {
        this.update_by = update_by;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }
}