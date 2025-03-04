package com.example.prm392_group2_shoesordersystem.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Category")
public class Category {
    @PrimaryKey(autoGenerate = true)
    public int category_id;
    public String category_name;
}