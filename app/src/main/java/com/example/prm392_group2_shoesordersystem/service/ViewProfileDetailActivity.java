package com.example.prm392_group2_shoesordersystem.service;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.entity.Account;
import com.example.prm392_group2_shoesordersystem.repository.AccountRepository;

public class ViewProfileDetailActivity extends AppCompatActivity {
    private TextView tvFullName;
    private TextView tvEmail;
    private TextView tvPhone;
    private TextView tvRole;
    private TextView tvGender;
    private TextView tvDOB;
    private TextView tvAddress;
    private Button btnBack;
    private Button btnUpdate;
    private AccountRepository repository;
    private int accountId = 3; // Giả sử lấy tài khoản có ID là 1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile_detail);
        Log.d("DEBUG", "ViewProfileDetailActivity started");

        tvFullName = findViewById(R.id.tvFullName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        tvRole = findViewById(R.id.tvRole);
        tvGender = findViewById(R.id.tvGender);
        tvDOB = findViewById(R.id.tvDOB);
        tvAddress = findViewById(R.id.tvAddress);
        btnBack = findViewById(R.id.btnBack);
        btnUpdate = findViewById(R.id.btnUpdate);

        repository = new AccountRepository(this);

        // Gọi hàm lấy dữ liệu tài khoản theo ID
        loadAccountData(accountId);

        btnBack.setOnClickListener(v -> finish());

        // Bắt sự kiện khi nhấn nút Update
        btnUpdate.setOnClickListener(v -> {
            Intent intent = new Intent(ViewProfileDetailActivity.this, UpdateProfileActivity.class);
            intent.putExtra("ACCOUNT_ID", accountId); // Truyền ID tài khoản sang trang cập nhật
            startActivity(intent);
            finish();
        });
    }

    private void loadAccountData(int accountId) {
        repository.getAccountById(accountId, new AccountRepository.OnAccountFetchListener() {
            @Override
            public void onAccountFetched(Account account) {
                runOnUiThread(() -> {
                    if (account != null) {
                        tvFullName.setText(account.getFullname());
                        tvEmail.setText(account.getEmail());
                        tvPhone.setText(account.getPhone());

                        // Chuyển đổi role thành chuỗi tương ứng
                        String roleText;
                        switch (account.getRole()) {
                            case 0:
                                roleText = "Customer";
                                break;
                            case 1:
                                roleText = "Admin";
                                break;
                            case 2:
                                roleText = "Manager";
                                break;
                            case 3:
                                roleText = "Seller";
                                break;
                            default:
                                roleText = "Unknown Role";
                                break;
                        }
                        tvRole.setText(roleText);

                        // Chuyển đổi gender thành chuỗi tương ứng
                        String genderText = (account.getGender() == 0) ? "Female" : "Male";
                        tvGender.setText(genderText);

                        tvDOB.setText(account.getDob());
                        tvAddress.setText(account.getAddress());
                    }
                });
            }
        });
    }
}
