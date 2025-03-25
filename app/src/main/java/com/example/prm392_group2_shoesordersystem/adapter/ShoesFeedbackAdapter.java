package com.example.prm392_group2_shoesordersystem.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.entity.Account;
import com.example.prm392_group2_shoesordersystem.entity.Shoes_Feedback;
import com.google.gson.Gson;

import java.util.List;

public class ShoesFeedbackAdapter extends RecyclerView.Adapter<ShoesFeedbackAdapter.FeedbackViewHolder> {
    private List<Shoes_Feedback> feedbackList;
    Context context;

    public ShoesFeedbackAdapter(List<Shoes_Feedback> feedbackList) {
        this.feedbackList = feedbackList;
    }
    public ShoesFeedbackAdapter(Context context,List<Shoes_Feedback> feedbackList) {
        this.context = context;
        this.feedbackList = feedbackList;
    }

    //    Tạo một ViewHolder mới khi RecyclerView cần hiển thị một item mới.
    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feedback, parent, false);
        return new FeedbackViewHolder(view);
    }

    //    Liên kết dữ liệu từ danh sách feedback với ViewHolder.
    @Override
    public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {
        Shoes_Feedback feedback = feedbackList.get(position);
        Log.d("BIND_VIEW", "Binding item position: " + position);

        String email; // Cần lấy ID của user đăng nhập thực tế
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String accountJson = prefs.getString("USER_ACCOUNT", null);
        boolean isLoggedIn = prefs.getBoolean("LOGGED_IN", false);
        Gson gson = new Gson();
        Account account = gson.fromJson(accountJson, Account.class);
        email = account.fullname;
        holder.tvUserName.setText(email);
        holder.tvFeedbackContent.setText(feedback.comment);
        holder.ratingFeedback.setRating(feedback.star);
    }

    @Override
    public int getItemCount() {
        return feedbackList != null ? feedbackList.size() : 0;

    }

    //    Lưu trữ các thành phần giao diện của mỗi item trong RecyclerView.
    public static class FeedbackViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserName, tvFeedbackContent;
        RatingBar ratingFeedback;

        public FeedbackViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvFeedbackContent = itemView.findViewById(R.id.tvFeedbackContent);
            ratingFeedback = itemView.findViewById(R.id.ratingBar);
        }
    }

    public void updateData(List<Shoes_Feedback> newList) {
        this.feedbackList.clear();
        this.feedbackList.addAll(newList);
        notifyDataSetChanged();
    }
}
