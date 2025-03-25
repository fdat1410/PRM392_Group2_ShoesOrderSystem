package com.example.prm392_group2_shoesordersystem.service.guest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.entity.Account;
import com.example.prm392_group2_shoesordersystem.entity.EmailSender;
import com.example.prm392_group2_shoesordersystem.repository.AccountRepository;

import java.security.SecureRandom;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText edtEmailForgot;
    Button btnClear, btnEnter;
    AccountRepository accountRepository;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtEmailForgot = findViewById(R.id.edtEmailForgot);
        btnClear= findViewById(R.id.btnClear);
        btnEnter = findViewById(R.id.btnEnter);
        accountRepository = new AccountRepository(this);

        btnClear.setOnClickListener(view -> {
            edtEmailForgot.setText("");
        });

        btnEnter.setOnClickListener(view -> sendOTP());
    }

    private void sendOTP() {
        String email = edtEmailForgot.getText().toString().trim();

        if (email.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Account account = accountRepository.checkExistAccount(email);
        if (account == null) {
            Toast.makeText(this, "Account not found", Toast.LENGTH_SHORT).show();
            return;
        }

        String otp = generateOTP();
        sendEmailInBackground(email, otp);
    }

    private void sendEmailInBackground(String email, String otp) {
        executorService.execute(() -> {
            try {
                String subject = "Your OTP Code";
                String message = "Dear user" + ",\n\nYour OTP code is: " + otp +
                        "\nPlease enter this code to verify your identity.\n\nBest Regards,\nShoe Order System Team";

                EmailSender.sendEmail(email, subject, message);
                runOnUiThread(() -> {
                    Toast.makeText(this, "OTP sent successfully!", Toast.LENGTH_SHORT).show();
                    navigateToOTPVerification(email, otp);
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Failed to send OTP!", Toast.LENGTH_SHORT).show());
            }
        });
    }


    private void navigateToOTPVerification(String email, String otp) {
        Intent intent = new Intent(ForgotPasswordActivity.this, OTPVerificationActivity.class);
        intent.putExtra("email", email);
        intent.putExtra("otp", otp);
        startActivity(intent);
    }

    private String generateOTP() {
        SecureRandom random = new SecureRandom();
        int otp = 10000 + random.nextInt(90000); // Tạo OTP 5 số
        return String.valueOf(otp);
    }
}

