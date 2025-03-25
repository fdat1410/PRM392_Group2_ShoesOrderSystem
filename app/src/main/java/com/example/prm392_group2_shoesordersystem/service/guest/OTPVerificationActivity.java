package com.example.prm392_group2_shoesordersystem.service.guest;

import android.app.AlertDialog;
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
import com.example.prm392_group2_shoesordersystem.repository.AccountRepository;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OTPVerificationActivity extends AppCompatActivity {

    private EditText edtOTP;
    private Button btnSaveOTP;
    private String receivedOTP;
    private String userEmail;
    private AccountRepository accountRepository;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_+=<>?";
    private static final String ALL_CHARACTERS = UPPERCASE + LOWERCASE + DIGITS + SPECIAL_CHARACTERS;
    private static final int PASSWORD_LENGTH = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_otpverification);

        edtOTP = findViewById(R.id.edtOTP);
        btnSaveOTP = findViewById(R.id.btnSaveOTP);
        accountRepository = new AccountRepository(this);

        Intent intent = getIntent();
        receivedOTP = intent.getStringExtra("otp");
        userEmail = intent.getStringExtra("email");

        btnSaveOTP.setOnClickListener(v -> verifyOTP());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void verifyOTP() {
        String enteredOTP = edtOTP.getText().toString().trim();

        if (enteredOTP.isEmpty()) {
            Toast.makeText(this, "Please enter the OTP", Toast.LENGTH_SHORT).show();
            return;
        }

        if (enteredOTP.equals(receivedOTP)) {
            String newPassword = generateRandomPassword();
            updatePasswordInDatabase(userEmail, newPassword);
        } else {
            Toast.makeText(this, "Invalid OTP, please try again!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updatePasswordInDatabase(String email, String newPassword) {
        executorService.execute(() -> {
            boolean isUpdated = accountRepository.ForgotPassword(email, newPassword);

            runOnUiThread(() -> {
                if (isUpdated) {
                    showGeneratedPasswordDialog(newPassword);
                } else {
                    Toast.makeText(this, "Failed to update password!", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    public static String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        List<Character> password = new ArrayList<>();

        password.add(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        password.add(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        password.add(DIGITS.charAt(random.nextInt(DIGITS.length())));
        password.add(SPECIAL_CHARACTERS.charAt(random.nextInt(SPECIAL_CHARACTERS.length())));

        for (int i = 4; i < PASSWORD_LENGTH; i++) {
            password.add(ALL_CHARACTERS.charAt(random.nextInt(ALL_CHARACTERS.length())));
        }

        Collections.shuffle(password, random);

        StringBuilder passwordString = new StringBuilder();
        for (char c : password) {
            passwordString.append(c);
        }

        return passwordString.toString();
    }

    private void showGeneratedPasswordDialog(String password) {
        new AlertDialog.Builder(this)
                .setTitle("Your New Password")
                .setMessage("Password: " + password)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.dismiss();
                    navigateToLogin();
                })
                .show();
    }

    private void navigateToLogin() {
        Intent intent = new Intent(OTPVerificationActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}

