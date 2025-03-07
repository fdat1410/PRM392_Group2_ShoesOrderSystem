package com.example.prm392_group2_shoesordersystem.activity;

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
    String selectedRole = "Customer";
    AccountRepository accountRepository;
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
        String[] roleArray = {"Customer", "Admin", "Manager", "Seller"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roleArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roles.setAdapter(adapter);

        roles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRole = roleArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedRole = "Customer";
            }
        });
        btnLogin.setOnClickListener(view -> {
            login();
        });
        btnregister.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
        btnForgotPassword.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });


    }
        public void login(){
            String email =edtEmail2.getText().toString().trim();
            String password = edtPassword2.getText().toString().trim();
            if(email.isEmpty() || password.isEmpty()){
                edtEmail2.setError("Email is required");
                edtPassword2.setError("Password is required");
                return;
            }

            Account account = accountRepository.login(email,password);
            if(account != null){

                if (!selectedRole.equals(account.getRole())) {
                    Toast.makeText(this, "Role mismatch! Please select the correct role.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent;
                switch (selectedRole) {
                    case "Customer":
                        intent = new Intent(LoginActivity.this, ChangePasswordActivity.class);
                        break;
                    case "Admin":
                        intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                        break;
                    case "Manager":
                        intent = new Intent(LoginActivity.this, RegisterActivity.class);
                        break;
                    case "Seller":
                        intent = new Intent(LoginActivity.this, ViewShoesListActivity.class);
                        break;
                    default:
                        Toast.makeText(this, "Invalid role selected!", Toast.LENGTH_SHORT).show();
                        return;
                }

                intent.putExtra("ROLE", selectedRole);
                startActivity(intent);
                finish();
            }else{
                edtEmail2.setError("Email or password is incorrect");
                edtPassword2.setError("Email or password is incorrect");
            }

        }
}