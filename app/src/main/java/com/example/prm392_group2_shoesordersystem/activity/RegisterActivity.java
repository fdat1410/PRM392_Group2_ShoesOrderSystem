package com.example.prm392_group2_shoesordersystem.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project.R;
import com.example.project.entity.Account;
import com.example.project.repository.AccountRepository;

public class RegisterActivity extends AppCompatActivity {
    EditText edtEmail, edtName, edtPhone, edtAddress, edtPassword;
    RadioGroup radiogroup;
    CheckBox check;
    Button btnRegister;
    TextView txtError;
    AccountRepository accountRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtEmail = findViewById(R.id.edtEmail);
        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        edtAddress = findViewById(R.id.edtAddress);
        edtPassword = findViewById(R.id.edtPassword);
        radiogroup = findViewById(R.id.radiogroup);
        check = findViewById(R.id.check);
        btnRegister = findViewById(R.id.btnRegister);
        accountRepository = new AccountRepository(this);

        btnRegister.setOnClickListener(view -> {
            String email = edtEmail.getText().toString().trim();
            String name = edtName.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();
            String address = edtAddress.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            int selectedId = radiogroup.getCheckedRadioButtonId();
            RadioButton radioButton = findViewById(selectedId);
            String gender = radioButton.getText().toString();
            boolean isCheck = check.isChecked();

            if (email.isEmpty() || name.isEmpty() || phone.isEmpty() || address.isEmpty() || password.isEmpty() || gender.isEmpty()) {
                if (email.isEmpty()) edtEmail.setError("Email is required");
                if (name.isEmpty()) edtName.setError("Name is required");
                if (phone.isEmpty()) edtPhone.setError("Phone is required");
                if (address.isEmpty()) edtAddress.setError("Address is required");
                if (password.isEmpty()) edtPassword.setError("Password is required");
                if (gender.isEmpty()) Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isCheck) {
                Toast.makeText(this, "You must agree to the terms and privacy", Toast.LENGTH_SHORT).show();
                return;
            }

            Account account = accountRepository.checkExistAccount(email);
            if (account != null) {
                edtEmail.setError("Email already exists");
                return;
            }
            int genderValue = gender.equals("Male") ? 1 : 0;
            int defaultAccStatus = 1;
            int defaultRole = 1;

            Account newAccount = new Account(
                    0,
                    "system",
                    "system",
                    email,
                    password,
                    name,
                    phone,
                    defaultAccStatus,
                    address,
                    "",
                    genderValue,
                    defaultRole
            );
            boolean success = accountRepository.insertAccount(newAccount);

            if (success) {
                Toast.makeText(this, "Register successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Registration failed. Please try again later.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
