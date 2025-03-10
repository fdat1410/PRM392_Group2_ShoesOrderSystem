package com.example.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project.R;
import com.example.project.entity.Account;
import com.example.project.repository.AccountRepository;

import java.security.SecureRandom;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText edtEmailForgot, edtNameForgot;
    Button btnBack, btnEnter;
    AccountRepository accountRepository;

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
        edtNameForgot = findViewById(R.id.edtNameForgot);
        btnBack = findViewById(R.id.btnBack);
        btnEnter = findViewById(R.id.btnEnter);
        accountRepository = new AccountRepository(this);
        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
            startActivity(intent);

        });
        btnEnter.setOnClickListener(view -> {
            forgotPassword();
        });

    }
    public void forgotPassword(){
        String email = edtEmailForgot.getText().toString().trim();
        String name = edtNameForgot.getText().toString().trim();
        if (email.isEmpty() || name.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        Account account = accountRepository.checkExistAccount(email);
        if (account == null) {
            Toast.makeText(this, "Account not found", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!account.getFullname().equals(name)) {
            Toast.makeText(this, "Name is incorrect", Toast.LENGTH_SHORT).show();
            return;
        }
        String newPassword = generateRandomPassword(8);
        boolean success = accountRepository.ForgotPassword(email, newPassword);
        if(success){
            Toast.makeText(this, "Password changed successfully", Toast.LENGTH_SHORT).show();
            showPasswordDialog(newPassword);

        }else{
            Toast.makeText(this, "Failed to change password", Toast.LENGTH_SHORT).show();
        }



    }
    private String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%^&*!";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            password.append(chars.charAt(index));
        }
        return password.toString();
    }
    private void showPasswordDialog(String newPassword) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New Password");
        builder.setMessage("Your new password is: " + newPassword);
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

}