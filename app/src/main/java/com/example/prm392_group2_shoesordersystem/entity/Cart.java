package com.example.prm392_group2_shoesordersystem.entity;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;



@Entity(tableName = "Cart", foreignKeys = {
        @ForeignKey(entity = Account.class, parentColumns = "account_id", childColumns = "account_id", onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Shoes.class, parentColumns = "shoes_id", childColumns = "shoes_id", onDelete = ForeignKey.CASCADE)
})
public class Cart {
    @PrimaryKey(autoGenerate = true)

    private int cart_id;
    private int account_id;
    private int shoes_id;
    private int quantity;
    private int size;

    public Cart() {
    }

    public Cart(int cart_id, int account_id, int shoes_id, int quantity, int size) {
        this.cart_id = cart_id;
        this.account_id = account_id;
        this.shoes_id = shoes_id;
        this.quantity = quantity;
        this.size = size;
    }

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public int getShoes_id() {
        return shoes_id;
    }

    public void setShoes_id(int shoes_id) {
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
}
