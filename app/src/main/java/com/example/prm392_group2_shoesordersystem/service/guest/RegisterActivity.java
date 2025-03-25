package com.example.prm392_group2_shoesordersystem.service.guest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.entity.Account;
import com.example.prm392_group2_shoesordersystem.repository.AccountRepository;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtEmail, edtName, edtPhone, edtAddress, edtPassword, edtConfirmPassword;
    private RadioGroup radioGroup;
    private CheckBox checkBox;
    private Button btnRegister;
    private AccountRepository accountRepository;

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

        // Ánh xạ các thành phần giao diện
        edtEmail = findViewById(R.id.edtEmail);
        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        edtAddress = findViewById(R.id.edtAddress);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        radioGroup = findViewById(R.id.radiogroup);
        checkBox = findViewById(R.id.check);
        btnRegister = findViewById(R.id.btnRegister);
        accountRepository = new AccountRepository(this);

        btnRegister.setOnClickListener(view -> registerAccount());
    }

    private void registerAccount() {
        String email = edtEmail.getText().toString().trim();
        String name = edtName.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();
        int selectedId = radioGroup.getCheckedRadioButtonId();
        boolean isChecked = checkBox.isChecked();

        // Kiểm tra đầu vào
        if (!validateInputs(email, name, phone, address, password, confirmPassword, selectedId, isChecked)) {
            return;
        }

        RadioButton radioButton = findViewById(selectedId);
        String gender = radioButton.getText().toString();
        int genderValue = gender.equals("Male") ? 1 : 0;
        int defaultAccStatus = 1;
        int defaultRole = 0;

        if (accountRepository.checkExistAccount(email) != null) {
            edtEmail.setError("Email already exists");
            return;
        }

        Account newAccount = new Account(
                0, "system", "system", email, password, name, phone,
                defaultAccStatus, address, "", genderValue, defaultRole
        );

        if (accountRepository.insertAccount(newAccount)) {
            Toast.makeText(this, "Register successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Registration failed. Please try again later.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateInputs(String email, String name, String phone, String address, String password, String confirmPassword, int selectedId, boolean isChecked) {
        boolean isValid = true;

        if (email.isEmpty()) {
            edtEmail.setError("Email is required");
            isValid = false;
        } else if (!isValidEmail(email)) {
            edtEmail.setError("Invalid email format");
            isValid = false;
        }

        if (name.isEmpty()) {
            edtName.setError("Name is required");
            isValid = false;
        }

        if (phone.isEmpty()) {
            edtPhone.setError("Phone is required");
            isValid = false;
        } else if (!isValidPhoneNumber(phone)) {
            edtPhone.setError("Invalid phone number! Must be 10 digits and start with 0.");
            isValid = false;
        }

        if (address.isEmpty()) {
            edtAddress.setError("Address is required");
            isValid = false;
        }

        if (password.isEmpty()) {
            edtPassword.setError("Password is required");
            isValid = false;
        } else if (!isValidPassword(password)) {
            edtPassword.setError("Password must be at least 8 characters, include uppercase, lowercase, number, and special character");
            isValid = false;
        }

        if (confirmPassword.isEmpty()) {
            edtConfirmPassword.setError("Please confirm your password");
            isValid = false;
        } else if (!password.equals(confirmPassword)) {
            edtConfirmPassword.setError("Passwords do not match");
            isValid = false;
        }

        if (selectedId == -1) {
            Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        if (!isChecked) {
            Toast.makeText(this, "You must agree to the terms and privacy", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        return isValid;
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(emailPattern, email);
    }

    private boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
        return Pattern.matches(passwordPattern, password);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String phonePattern = "^0\\d{9}$";
        return Pattern.matches(phonePattern, phoneNumber);
    }
}

