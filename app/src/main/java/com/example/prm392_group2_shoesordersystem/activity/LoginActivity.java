package com.example.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project.R;
import com.example.project.entity.Account;
import com.example.project.repository.AccountRepository;

public class LoginActivity extends AppCompatActivity {
    EditText edtEmail2, edtPassword2;
    Spinner roles;
    Button btnLogin, btnForgotPassword, btnregister, btnterm;
    AccountRepository accountRepository;

    // Bản đồ ánh xạ role từ String sang int
    private final String[] roleArray = {"Customer", "Admin", "Manager", "Seller"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtEmail2 = findViewById(R.id.edtEmail2);
        edtPassword2 = findViewById(R.id.edtPassword2);
        btnLogin = findViewById(R.id.btnLogin);
        btnForgotPassword = findViewById(R.id.btnForgotPassword);
        btnregister = findViewById(R.id.btnregister);
        btnterm = findViewById(R.id.btnterm);
        roles = findViewById(R.id.roles);
        accountRepository = new AccountRepository(this);

        // Cấu hình Spinner với danh sách role
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roleArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roles.setAdapter(adapter);

        btnLogin.setOnClickListener(view -> login());

        btnregister.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        btnForgotPassword.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
    }

    public void login() {
        String email = edtEmail2.getText().toString().trim();
        String password = edtPassword2.getText().toString().trim();
        int selectedRoleIndex = roles.getSelectedItemPosition(); // Lấy role dạng số

        if (email.isEmpty() || password.isEmpty()) {
            edtEmail2.setError("Email is required");
            edtPassword2.setError("Password is required");
            return;
        }

        new Thread(() -> {
            Account account = accountRepository.login(email, password);

            runOnUiThread(() -> {
                if (account != null) {
                    int accountRole = account.getRole(); // Role từ DB (int)

                    if (selectedRoleIndex != accountRole) {
                        Toast.makeText(this, "Role mismatch! Please select the correct role.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Intent intent;
                    switch (selectedRoleIndex) {
                        case 0:
                            intent = new Intent(LoginActivity.this, ChangePasswordActivity.class);
                            break;
                        case 1:
                            intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                            break;
                        case 2:
                            intent = new Intent(LoginActivity.this, RegisterActivity.class);
                            break;
                        case 3:
                            intent = new Intent(LoginActivity.this, ViewShoesListActivity.class);
                            break;
                        default:
                            Toast.makeText(this, "Invalid role selected!", Toast.LENGTH_SHORT).show();
                            return;
                    }

                    intent.putExtra("ROLE", roleArray[selectedRoleIndex]); // Gửi role dưới dạng String
                    startActivity(intent);
                    finish();
                } else {
                    edtEmail2.setError("Email or password is incorrect");
                    edtPassword2.setError("Email or password is incorrect");
                }
            });
        }).start();
    }
}
