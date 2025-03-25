package com.example.prm392_group2_shoesordersystem.entity;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "Cart",
        foreignKeys = {
                @ForeignKey(entity = Account.class, parentColumns = "account_id", childColumns = "account_id", onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = Shoes.class, parentColumns = "shoes_id", childColumns = "shoes_id", onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)
        })
public class Cart implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cart_id")
    private int cart_id;

    @ColumnInfo(name = "account_id")
    @NonNull
    private int account_id;

    @ColumnInfo(name = "shoes_id")
    @NonNull
    private int shoes_id;

    @ColumnInfo(name = "quantity")
    @NonNull
    private int quantity;

    @ColumnInfo(name = "size")
    @NonNull
    private int size;

    @Ignore
    private Shoes shoes;

    public Cart() {}

    public Cart(int cart_id, int account_id, int shoes_id, int quantity, int size, Shoes shoes) {
        this.cart_id = cart_id;
        this.account_id = account_id;
        this.shoes_id = shoes_id;
        this.quantity = quantity;
        this.size = size;
        this.shoes = shoes;
    }

    public Shoes getShoes() {
        return shoes;
    }

    public void setShoes(Shoes shoes) {
        this.shoes = shoes;
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

    public int getShoes_id() {
        return shoes_id;
    }

    public void setShoes_id(int shoes_id) {
        this.shoes_id = shoes_id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    // Getters và Setters...

    protected Cart(Parcel in) {
        cart_id = in.readInt();
        account_id = in.readInt();
        shoes_id = in.readInt();
        quantity = in.readInt();
        size = in.readInt();
    }

    public static final Creator<Cart> CREATOR = new Creator<Cart>() {
        @Override
        public Cart createFromParcel(Parcel in) {
            return new Cart(in);
        }

        @Override
        public Cart[] newArray(int size) {
            return new Cart[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(cart_id);
        dest.writeInt(account_id);
        dest.writeInt(shoes_id);
        dest.writeInt(quantity);
        dest.writeInt(size);
    }
}
