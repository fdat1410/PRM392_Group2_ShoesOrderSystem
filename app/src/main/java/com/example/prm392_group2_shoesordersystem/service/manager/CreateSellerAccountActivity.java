package com.example.prm392_group2_shoesordersystem.service.manager;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.entity.Account;
import com.example.prm392_group2_shoesordersystem.entity.EmailSender;
import com.example.prm392_group2_shoesordersystem.repository.AccountRepository;

import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CreateSellerAccountActivity extends AppCompatActivity {
    private EditText etEmail, etFullName, etPhone, etAddress, etDob;
    private RadioGroup rgGender;
    private Button btnCreate;
    AccountRepository accountRepository ;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_seller_account_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        etEmail = findViewById(R.id.et_email);
        etFullName = findViewById(R.id.et_fullname);
        etPhone = findViewById(R.id.et_phone);
        etAddress = findViewById(R.id.et_address);
        etDob = findViewById(R.id.et_dob);
        rgGender = findViewById(R.id.rg_gender);
        btnCreate = findViewById(R.id.btn_create);

        accountRepository = new AccountRepository(this);

        etDob.setOnClickListener(v -> showDatePickerDialog());

        btnCreate.setOnClickListener(v -> createSellerAccount());

    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    etDob.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
                }, year, month, day);

        datePickerDialog.show();
    }

    private void createSellerAccount() {
        String email = etEmail.getText().toString().trim();
        String fullName = etFullName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String dob = etDob.getText().toString().trim();
        int selectedGenderId = rgGender.getCheckedRadioButtonId();
        RadioButton selectedGenderButton = findViewById(selectedGenderId);
        String gender_raw = selectedGenderButton != null ? selectedGenderButton.getText().toString() : "";

        if (!isValidInput(email, fullName, phone, address, dob, gender_raw)) {
            return;
        }

        int gender = gender_raw.equals("Female") ? 0 : 1;
        Account account = accountRepository.checkExistAccount(email);

        if (account != null) {
            Toast.makeText(this, "Email is already used!", Toast.LENGTH_SHORT).show();
        } else {
            String password = generatePassword(6);
            boolean createSuccess = accountRepository.CreateSellerAccount(email, password, fullName, phone, address, dob, gender);

            if (createSuccess) {
                String subject = "Seller Account Created Successfully";
                String emailMessage = "Dear " + fullName + ",\n\nYour account has been created successfully.\nYour password: " + password +
                        "\nPlease change your password after logging in.\n\nBest Regards,\nShoe Order System Team";

                executorService.execute(() -> {
                    try {
                        EmailSender.sendEmail(email, subject, emailMessage);
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Account created! Email sent.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CreateSellerAccountActivity.this, ManagePageActivity.class);
                            startActivity(intent);
                            finish();
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        runOnUiThread(() -> Toast.makeText(this, "Failed to send email!", Toast.LENGTH_SHORT).show());
                    }
                });
            } else {
                Toast.makeText(this, "Failed to create account!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private boolean isValidInput(String email, String fullName, String phone, String address, String dob, String gender) {
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Invalid email address");
            return false;
        }
        if (TextUtils.isEmpty(fullName)) {
            etFullName.setError("Full name is required");
            return false;
        }
        if (TextUtils.isEmpty(phone) || phone.length() < 10 || phone.length() > 15) {
            etPhone.setError("Invalid phone number");
            return false;
        }
        if (TextUtils.isEmpty(address)) {
            etAddress.setError("Address is required");
            return false;
        }
        if (TextUtils.isEmpty(dob)) {
            etDob.setError("Date of birth is required");
            return false;
        }
        if (TextUtils.isEmpty(gender)) {
            Toast.makeText(this, "Please select a gender", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static String generatePassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        return password.toString();
    }

    private void sendEmailInBackground(String email, String subject, String message) {
        executorService.execute(() -> {
            try {
                EmailSender.sendEmail(email, subject, message);
                runOnUiThread(() -> Toast.makeText(this, "Email sent!", Toast.LENGTH_SHORT).show());
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Failed to send email!", Toast.LENGTH_SHORT).show());
            }
        });
    }





}
