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

    public int getFeedback_id() {
        return feedback_id;
    }

    public void setFeedback_id(int feedback_id) {
        this.feedback_id = feedback_id;
    }

    public int getShoes_id() {
        return shoes_id;
    }

    public void setShoes_id(int shoes_id) {
        this.shoes_id = shoes_id;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }
}
