package com.example.prm392_group2_shoesordersystem.service.customer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.adapter.ShoesFeedbackAdapter;
import com.example.prm392_group2_shoesordersystem.entity.Account;
import com.example.prm392_group2_shoesordersystem.entity.Shoes;
import com.example.prm392_group2_shoesordersystem.entity.Shoes_Feedback;
import com.example.prm392_group2_shoesordersystem.repository.FeedbackRepository;
import com.example.prm392_group2_shoesordersystem.repository.ShoesRepository;
import com.example.prm392_group2_shoesordersystem.service.customer.SendFeedbackActivity;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ViewListFeedbackActivity extends AppCompatActivity {
    private RecyclerView recyclerFeedback;
    private TextView tvNoFeedback;
    private ShoesFeedbackAdapter feedbackAdapter;
    private List<Shoes_Feedback> feedbackList;
    private Button btnSendFeedback;
    private FeedbackRepository repository;
    private ShoesRepository shoesRepository;
    private int shoesId;
    private ImageView imgShoes;
    private TextView tvShoesName;
    private TextView tvShoesPrice;
    private RatingBar ratingBar;
    private String imgUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list_feedback);

        recyclerFeedback = findViewById(R.id.recyclerFeedback);
        tvNoFeedback = findViewById(R.id.tvNoFeedback);
        btnSendFeedback = findViewById(R.id.btnSendFeedback);
        feedbackList = new ArrayList<>();
        //
        imgShoes = findViewById(R.id.imgProduct);
        tvShoesName = findViewById(R.id.tvProductName);
        tvShoesPrice = findViewById(R.id.tvProductPrice);
        ratingBar = findViewById(R.id.ratingBar);

        // Khởi tạo repository
        repository = new FeedbackRepository(this);
        shoesRepository = new ShoesRepository(this);
        Intent intent = getIntent();
        shoesId = intent.getIntExtra("shoes_id", -1);
        imgUrl = intent.getStringExtra("shoes_img");
        com.squareup.picasso.Picasso
                .get()
                .load(imgUrl) // Đường dẫn ảnh từ bộ nhớ trong
                .placeholder(R.drawable.ic_launcher_background) // Ảnh mặc định nếu chưa có ảnh
                .into(imgShoes);

        if (shoesId != -1) {
            loadFeedbackData(shoesId);
            loadShoesData(shoesId);
        }

        checkIfUserCanSendFeedback();
        feedbackAdapter = new ShoesFeedbackAdapter(this, feedbackList);
        recyclerFeedback.setLayoutManager(new LinearLayoutManager(this));
        recyclerFeedback.setAdapter(feedbackAdapter);

        btnSendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int userId = 1; // Hàm lấy user_id
                if (userId == -1) {
                    Toast.makeText(ViewListFeedbackActivity.this, "You need login to send feedback!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(ViewListFeedbackActivity.this, SendFeedbackActivity.class);
                intent.putExtra("shoes_id", shoesId);
                intent.putExtra("user_id", userId);
                intent.putExtra("shoes_name", tvShoesName.getText().toString());
                intent.putExtra("shoes_price", tvShoesPrice.getText().toString());
                intent.putExtra("rating_product", ratingBar.getRating());
                startActivity(intent);
            }
        });

    }

    private void loadFeedbackData(int shoesId) {
        repository.getFeedbackByShoesId(shoesId, new FeedbackRepository.OnFeedbackFetchListener() {
            @Override
            public void onFeedbackFetched(List<Shoes_Feedback> feedbackList) {
                runOnUiThread(() -> {
                    if (feedbackList != null && !feedbackList.isEmpty()) {
                        Log.d("FEEDBACK_LIST", "Số lượng feedback: " + feedbackList.size());

                        // Cập nhật danh sách chính
                        ViewListFeedbackActivity.this.feedbackList.clear();
                        ViewListFeedbackActivity.this.feedbackList.addAll(feedbackList);

                        if (feedbackAdapter == null) {
                            feedbackAdapter = new ShoesFeedbackAdapter(feedbackList);
                            recyclerFeedback.setLayoutManager(new LinearLayoutManager(ViewListFeedbackActivity.this));
                            recyclerFeedback.setAdapter(feedbackAdapter);
                        } else {
                            feedbackAdapter.updateData(feedbackList);
                        }
                        // Gọi loadShoesData() sau khi danh sách feedback đã có dữ liệu
                        loadShoesData(shoesId);
                    } else {
                        tvNoFeedback.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
    }

    private void loadShoesData(int shoesId) {
        shoesRepository.getShoesById(shoesId, new ShoesRepository.OnShoesFetchListener() {
            @Override
            public void onShoesFetched(Shoes shoes) {
                runOnUiThread(() -> {
                    if (shoes != null) {
                        // Cập nhật tên và giá giày
                        tvShoesName.setText(shoes.getShoes_name());
                        tvShoesPrice.setText(String.format("$%.2f", shoes.getPrice()));

                        // Tính số sao trung bình
                        float totalRating = 0;
                        for (Shoes_Feedback feedback : feedbackList) {
                            totalRating += feedback.getStar();
                        }
                        float avgRating = (feedbackList.isEmpty()) ? 0 : totalRating / feedbackList.size();
                        // Cập nhật RatingBar
                        ratingBar.setRating(avgRating);

                        // Xử lý ảnh
                        String imagePath = shoes.getImg();
                        if (imagePath != null && !imagePath.isEmpty()) {
                            if (!imagePath.startsWith("http")) {
                                // Nếu là tên file trong drawable
                                int imageResource = getResources().getIdentifier(imagePath, "drawable", getPackageName());
                                if (imageResource != 0) {
                                    imgShoes.setImageResource(imageResource);
                                } else {
                                     // Ảnh mặc định nếu không tìm thấy
                                }
                            } else {
                                // Nếu là URL, dùng Glide để tải ảnh
//                                Glide.with(imgShoes.getContext())
//                                        .load(imagePath)
//                                        .placeholder(R.drawable.loading_image) // Hiển thị ảnh loading khi tải
//                                        .error(R.drawable.error_image) // Ảnh mặc định nếu lỗi
//                                        .into(imgShoes);
                            }
                        }
                    }
                });
            }
        });
    }
    private void checkIfUserCanSendFeedback() {

        int accountId; // Cần lấy ID của user đăng nhập thực tế
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String accountJson = prefs.getString("USER_ACCOUNT", null);
        boolean isLoggedIn = prefs.getBoolean("LOGGED_IN", false);
        Gson gson = new Gson();
        Account account = gson.fromJson(accountJson, Account.class);
        accountId = account.account_id;

        repository.checkUserPurchasedShoes(accountId, shoesId, hasPurchased -> {
            repository.getUserRole(accountId, role -> {
                runOnUiThread(() -> {
                    if (hasPurchased > 0 && role == 0) {
                        btnSendFeedback.setVisibility(View.VISIBLE);
                    } else {
                        btnSendFeedback.setVisibility(View.GONE);
                    }
                });
            });
        });
    }
}
