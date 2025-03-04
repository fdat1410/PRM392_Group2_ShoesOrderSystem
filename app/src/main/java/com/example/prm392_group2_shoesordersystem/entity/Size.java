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
}
