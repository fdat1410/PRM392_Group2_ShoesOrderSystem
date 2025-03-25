package com.example.prm392_group2_shoesordersystem.service.customer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.example.prm392_group2_shoesordersystem.repository.AccountRepository;
import com.google.gson.Gson;

import java.util.regex.Pattern;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText edtCurrentPass, edtNewPass, edtConfirmPass;
    AccountRepository accountRepository;
    Button btnClose, btnSave;
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_password);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ view
        edtCurrentPass = findViewById(R.id.edtCurrentPass);
        edtNewPass = findViewById(R.id.edtNewPass);
        edtConfirmPass = findViewById(R.id.edtConfirmPass);
        btnClose = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnEnter);

        accountRepository = new AccountRepository(this);
        userEmail = getSavedEmail();

        if (userEmail == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        btnClose.setOnClickListener(view -> finish());
        btnSave.setOnClickListener(view -> changePassword());
    }

    private void changePassword() {
        String currentPassword = edtCurrentPass.getText().toString().trim();
        String newPassword = edtNewPass.getText().toString().trim();
        String confirmPassword = edtConfirmPass.getText().toString().trim();

        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentPassword.equals(newPassword)) {
            Toast.makeText(this, "New password must be different from current password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(this, "New password and confirm password do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidPassword(newPassword)) {
            edtNewPass.setError("Password must be at least 8 characters, include uppercase, lowercase, number, and special character");
            return;
        }

        Account account = accountRepository.checkExistAccount(userEmail);
        if (account == null) {
            Toast.makeText(this, "Account not found", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!account.getPassword().equals(currentPassword)) {
            Toast.makeText(this, "Current password is incorrect", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean success = accountRepository.changePassword(userEmail, newPassword);
        if (success) {
            Toast.makeText(this, "Password changed successfully", Toast.LENGTH_SHORT).show();
            finish();

        } else {
            Toast.makeText(this, "Failed to change password", Toast.LENGTH_SHORT).show();
        }
    }

    private String getSavedEmail() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String accountJson = prefs.getString("USER_ACCOUNT", null);

        if (accountJson != null) {
            Gson gson = new Gson();
            Account account = gson.fromJson(accountJson, Account.class);
            return account.getEmail();
        }
        return null;
    }

    private boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
        return Pattern.matches(passwordPattern, password);
    }
}
