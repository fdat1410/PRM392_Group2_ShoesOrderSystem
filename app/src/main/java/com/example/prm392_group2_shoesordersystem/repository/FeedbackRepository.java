package com.example.prm392_group2_shoesordersystem.repository;

import android.content.Context;
import android.os.Looper;

import com.example.prm392_group2_shoesordersystem.dao.AppDatabase;
import com.example.prm392_group2_shoesordersystem.dao.FeedbackDAO;
import com.example.prm392_group2_shoesordersystem.entity.Shoes_Feedback;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FeedbackRepository {
    private FeedbackDAO feedbackDao;
    private ExecutorService executorService;
    public FeedbackRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        feedbackDao = database.feedbackDao();
        executorService = Executors.newSingleThreadExecutor();
    }
    // Hàm lấy feedbacks theo ShoesID(chạy trên background thread)
    public void getFeedbackByShoesId(int shoesId, OnFeedbackFetchListener listener) {
        executorService.execute(() -> {
            List<Shoes_Feedback> feedbackList = feedbackDao.getFeedbackByShoesId(shoesId);
            listener.onFeedbackFetched(feedbackList);
        });
    }

    // Interface callback để trả kết quả về UI thread
    public interface OnFeedbackFetchListener {
        void onFeedbackFetched(List<Shoes_Feedback> feedbackList);
    }

    // Insert feedback
    public void insertFeedback(Shoes_Feedback feedback) {
        executorService.execute(() -> feedbackDao.insertFeedback(feedback));
    }
    public void getUserRole(int accountId, OnRoleCheckListener listener) {
        new Thread(() -> {
            int role = feedbackDao.getUserRole(accountId);
            new android.os.Handler(Looper.getMainLooper()).post(() -> listener.onRoleChecked(role));
        }).start();
    }

    public void checkUserPurchasedShoes(int accountId, int shoesId, OnPurchaseCheckListener listener) {
        new Thread(() -> {
            int hasPurchased = feedbackDao.hasPurchasedProduct(accountId, shoesId);
            new android.os.Handler(Looper.getMainLooper()).post(() -> listener.onCheckCompleted(hasPurchased));
        }).start();
    }

    // Interface để trả kết quả role về UI Thread
    public interface OnRoleCheckListener {
        void onRoleChecked(int role);
    }

    public interface OnPurchaseCheckListener {
        void onCheckCompleted(int hasPurchased);
    }

    public void checkIfUserReviewed(int accountId, int shoesId, OnReviewCheckListener listener) {
        new Thread(() -> {
            int hasReviewed = feedbackDao.hasAlreadyReviewed(accountId, shoesId);
            new android.os.Handler(Looper.getMainLooper()).post(() -> listener.onReviewChecked(hasReviewed));
        }).start();
    }

    // Interface để trả kết quả về UI Thread
    public interface OnReviewCheckListener {
        void onReviewChecked(int hasReviewed);
    }
}
