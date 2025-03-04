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
}