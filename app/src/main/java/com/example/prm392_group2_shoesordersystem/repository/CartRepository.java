package com.example.prm392_group2_shoesordersystem.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.prm392_group2_shoesordersystem.dao.AppDatabase;
import com.example.prm392_group2_shoesordersystem.dao.CartDAO;
import com.example.prm392_group2_shoesordersystem.entity.Cart;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CartRepository {
    private final CartDAO cartDAO;
    private final ExecutorService executorService;

    public CartRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        cartDAO = appDatabase.cartDAO();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void addCartItem(Cart cart) {
        executorService.execute(() -> {
            try {
                cartDAO.insert(cart);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void updateCartItem(Cart cart) {
        executorService.execute(() -> {
            try {
                cartDAO.update(cart);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void removeCartItem(Cart cart) {
        executorService.execute(() -> {
            try {
                cartDAO.delete(cart);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public LiveData<List<CartDAO.CartWithShoes>> getCartDetails(int accountId) {
        MutableLiveData<List<CartDAO.CartWithShoes>> data = new MutableLiveData<>();
        executorService.execute(() -> {
            try {
                data.postValue(cartDAO.getCartDetailsByAccountId(accountId));
            } catch (Exception e) {
                e.printStackTrace();
                data.postValue(null);
            }
        });
        return data;
    }

    public void clearCart(int accountId) {
        executorService.execute(() -> {
            try {
                cartDAO.clearCartByAccountId(accountId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void updateCartQuantity(int cartId, int quantity) {
        executorService.execute(() -> {
            try {
                cartDAO.updateQuantity(cartId, quantity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Cart checkCartExist(int account_id, int shoe_id, int size){
        return cartDAO.checkCartExist(account_id, shoe_id, size);
    }

    public void UpdateQuanntityCartExist(int account_id) {
        executorService.execute(() -> {
            try {
                cartDAO.UpdateQuanntityCartExist(account_id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    public void DecreaseQuantity(int account_id) {
        executorService.execute(() -> {
            try {
                cartDAO.DecreaseQuantity(account_id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    public List<Cart> listCartByAccount(int accountId) {
        try {
            return cartDAO.listCartByAccount(accountId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
