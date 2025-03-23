package com.example.prm392_group2_shoesordersystem.service;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.entity.Shoes_Feedback;
import com.example.prm392_group2_shoesordersystem.repository.FeedbackRepository;

public class SendFeedbackActivity extends AppCompatActivity {
    private ImageView btnBack;
    private RatingBar ratingBar;
    private EditText edtComment;
    private Button btnSendFeedback;
    private FeedbackRepository feedbackRepository;
    private TextView tvShoesName;
    private RatingBar ratingProduct;
    private TextView tvShoesPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_feedback);

        Intent intent = getIntent();
        int userId = intent.getIntExtra("user_id", -1);
        int shoesId = intent.getIntExtra("shoes_id", -1);
        String shoesName = intent.getStringExtra("shoes_name");
        String shoesPrice = intent.getStringExtra("shoes_price");
        float ratingShoes = intent.getFloatExtra("rating_product", 0);

        if (userId == -1 || shoesId == -1) {
            Toast.makeText(this, "Lỗi: Thiếu thông tin người dùng hoặc sản phẩm", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        //Ánh xạ view từ layout
        btnBack = findViewById(R.id.btnBack);
        ratingBar = findViewById(R.id.ratingBar);
        edtComment = findViewById(R.id.edtComment);
        btnSendFeedback = findViewById(R.id.btnSendFeedback);
        tvShoesName = findViewById(R.id.tvProductName);
        tvShoesPrice = findViewById(R.id.tvProductPrice);
        ratingProduct = findViewById(R.id.ratingProduct);

        // repository
        feedbackRepository = new FeedbackRepository(this);

        // Hiển thị thông tin sản phẩm
        tvShoesName.setText(shoesName);
        tvShoesPrice.setText(shoesPrice);
        ratingProduct.setRating(ratingShoes);


        // Xử lí ratingbar
        ratingBar.setRating(0);
        ratingBar.setProgressTintList(getColorStateList(android.R.color.darker_gray));

        // Xử lý khi người dùng chạm vào RatingBar
        ratingBar.setOnRatingBarChangeListener((ratingBar, newRating, fromUser) -> {
            if (fromUser) {
                ratingBar.setProgressTintList(getColorStateList(R.color.yellow)); // Hiển thị màu vàng khi chọn sao
            }
        });

        // Xử lí nút back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SendFeedbackActivity.this, ViewListFeedbackActivity.class);
                startActivity(intent);
            }
        });

        // Xử lí nút Send
        btnSendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFeedback();
            }
        });
    }

    private void sendFeedback() {
        float rating = ratingBar.getRating();
        String comment = edtComment.getText().toString().trim();

        if (rating == 0) {
            Toast.makeText(this, "Please rate before sending feedback", Toast.LENGTH_SHORT).show();
            return;
        }

        if (comment.isEmpty()) {
            Toast.makeText(this, "Please enter your feedback", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy thông tin từ Intent
        Intent intent = getIntent();
        int userId = intent.getIntExtra("user_id", -1);
        int shoesId = intent.getIntExtra("shoes_id", -1);

        if (userId == -1 || shoesId == -1) {
            Toast.makeText(this, "Error: Missing user or product info", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra xem người dùng đã đánh giá chưa
        feedbackRepository.checkIfUserReviewed(userId, shoesId, hasReviewed -> {
            if (hasReviewed > 0) {
                // Nếu đã đánh giá, thông báo lỗi và dừng lại
                runOnUiThread(() -> Toast.makeText(this, "You have already rated this product. Please come back!", Toast.LENGTH_SHORT).show());
            } else {
                // Nếu chưa đánh giá, tiến hành gửi đánh giá
                Shoes_Feedback feedback = new Shoes_Feedback();
                feedback.shoes_id = shoesId;
                feedback.account_id = userId;
                feedback.star = (int) rating;
                feedback.comment = comment;

                feedbackRepository.insertFeedback(feedback);

                // Thông báo thành công
                runOnUiThread(() -> {
                    Toast.makeText(this, "Feedback sent!", Toast.LENGTH_LONG).show();
                    // Chuyển sang trang danh sách phản hồi
                    Intent feedbackListIntent = new Intent(SendFeedbackActivity.this, ViewListFeedbackActivity.class);
                    startActivity(feedbackListIntent);
                    finish();
                });
            }
        });
    }

}