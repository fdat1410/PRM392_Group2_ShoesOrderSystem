package com.example.prm392_group2_shoesordersystem.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Embedded;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.prm392_group2_shoesordersystem.entity.Cart;
import com.example.prm392_group2_shoesordersystem.entity.Shoes;

import java.util.List;

@Dao
public interface CartDAO {

    @Insert
    void insert(Cart cart);

    @Update
    void update(Cart cart);

    @Delete
    void delete(Cart cart);

    @Query("SELECT * FROM Cart WHERE account_id = :accountId")
    List<Cart> getCartsByAccountId(int accountId);

    @Query("DELETE FROM Cart WHERE account_id = :accountId")
    void clearCartByAccountId(int accountId);

    @Query("UPDATE Cart SET quantity = :quantity WHERE cart_id = :cartId")
    void updateQuantity(int cartId, int quantity);

    @Transaction
    @Query("SELECT " +
            "Cart.*, " +
            "Shoes.shoes_id AS shoes_shoes_id, " +
            "Shoes.price AS shoes_price, " +
            "Shoes.img AS shoes_img, " +
            "Shoes.description AS shoes_description, " +
            "Shoes.shoes_name AS shoes_shoes_name, " +
            "Shoes.shoes_status AS shoes_shoes_status, " +
            "Shoes.create_by AS shoes_create_by, " +
            "Shoes.update_by AS shoes_update_by, " +
            "Shoes.category_id AS shoes_category_id " +
            "FROM Cart " +
            "INNER JOIN Shoes ON Cart.shoes_id = Shoes.shoes_id " +
            "WHERE Cart.account_id = :accountId")
    List<CartWithShoes> getCartDetailsByAccountId(int accountId);

    class CartWithShoes {
        @Embedded(prefix = "cart_")
        public Cart cart;

        @Embedded(prefix = "shoes_")
        public Shoes shoes;
    }

    @Query("SELECT * FROM Cart WHERE account_id = :account_id and shoes_id = :shoeId and size = :size")
    Cart checkCartExist(int account_id, int shoeId, int size);

    @Query("UPDATE Cart SET quantity = quantity + 1 WHERE cart_id = :account_id")
    void UpdateQuanntityCartExist(int account_id);

    @Query("UPDATE Cart SET quantity = quantity - 1 WHERE cart_id = :account_id")
    void DecreaseQuantity(int account_id);

    @Query("SELECT * FROM Cart WHERE account_id = :account_id")
    List<Cart> listCartByAccount(int account_id);
}
