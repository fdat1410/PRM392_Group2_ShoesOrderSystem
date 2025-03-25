package com.example.prm392_group2_shoesordersystem.service.guest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.entity.Account;
import com.example.prm392_group2_shoesordersystem.repository.AccountRepository;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.prm392_group2_shoesordersystem.service.customer.ManageCustomerDashboardActivity;
import com.example.prm392_group2_shoesordersystem.service.manager.ManagePageActivity;
import com.example.prm392_group2_shoesordersystem.service.seller.SellerDashboardActivity;
import com.google.gson.Gson;
import com.example.prm392_group2_shoesordersystem.service.customer.ViewProfileDetailActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private EditText edtEmail, edtPassword;
    private Button btnLogin;
    private CheckBox checkBoxRemember;
    private TextView btnForgotPassword, btnRegister;
    private AccountRepository accountRepository;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmail2);
        edtPassword = findViewById(R.id.edtPassword2);
        btnLogin = findViewById(R.id.btnLogin);
        checkBoxRemember = findViewById(R.id.checkBox);
        btnForgotPassword = findViewById(R.id.btnForgotPassword);
        btnRegister = findViewById(R.id.btnregister);

        accountRepository = new AccountRepository(this);

        btnLogin.setOnClickListener(v -> loginUser());

        btnForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });

        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        executorService.execute(() -> {
            Account account = accountRepository.getAccountByEmail(email);
            runOnUiThread(() -> {
                if (account != null && account.getPassword().equals(password)) {
                    Gson gson = new Gson();
                    String accountJson = gson.toJson(account);
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("USER_ACCOUNT", accountJson);
                    editor.putBoolean("LOGGED_IN", true);
                    editor.apply();
                    navigateBasedOnRole(account.getRole());

                } else {
                    Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void navigateBasedOnRole(int role) {
        Intent intent;
        switch (role) {
            case 0:
                intent = new Intent(LoginActivity.this, ManageCustomerDashboardActivity.class);
                break;
            case 2:
                intent = new Intent(LoginActivity.this, ManagePageActivity.class);
                break;
            case 3:
                intent = new Intent(LoginActivity.this, SellerDashboardActivity.class);
                break;
            default:
                Toast.makeText(this, "Invalid role", Toast.LENGTH_SHORT).show();
                return;
        }
        startActivity(intent);
        finish();
    }
}


