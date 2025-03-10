package com.example.project.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.project.R;
import com.example.project.entity.Account;
import com.example.project.repository.AccountRepository;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText edtemail, edtCurrentPass, edtNewPass, edtConfirmPass;
    AccountRepository accountRepository;
    Button btnClose, btnSave;

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
        edtemail = findViewById(R.id.edtemail);
        edtCurrentPass = findViewById(R.id.edtCurrentPass);
        edtNewPass = findViewById(R.id.edtNewPass);
        edtConfirmPass = findViewById(R.id.edtConfirmPass);
        btnClose = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnEnter);

        accountRepository = new AccountRepository(this);

        btnClose.setOnClickListener(view -> finish());
        btnSave.setOnClickListener(view -> changePassword());
    }

    public void changePassword() {
        String email = edtemail.getText().toString().trim();
        String currentPassword = edtCurrentPass.getText().toString().trim();
        String newPassword = edtNewPass.getText().toString().trim();
        String confirmPassword = edtConfirmPass.getText().toString().trim();

        if (email.isEmpty() || currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
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

        Account account = accountRepository.checkExistAccount(email);
        if (account == null) {
            Toast.makeText(this, "Account not found", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!account.getPassword().equals(currentPassword)) {
            Toast.makeText(this, "Current password is incorrect", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean success = accountRepository.changePassword(email, newPassword);
        if (success) {
            Toast.makeText(this, "Password changed successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to change password", Toast.LENGTH_SHORT).show();
        }
    }
}
