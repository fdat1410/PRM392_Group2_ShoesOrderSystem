package com.example.prm392_group2_shoesordersystem.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Shoes_Feedback", foreignKeys = {
        @ForeignKey(entity = Shoes.class, parentColumns = "shoes_id", childColumns = "shoes_id", onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Account.class, parentColumns = "account_id", childColumns = "account_id", onDelete = ForeignKey.CASCADE)
})
public class Shoes_Feedback {
    @PrimaryKey(autoGenerate = true)
    public int feedback_id;
    public int shoes_id;
    public int star;
    public String comment;
    public int account_id;
}
