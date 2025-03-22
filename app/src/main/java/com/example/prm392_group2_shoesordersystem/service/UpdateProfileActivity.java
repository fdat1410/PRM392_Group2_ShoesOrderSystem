package com.example.prm392_group2_shoesordersystem.service;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.entity.Account;
import com.example.prm392_group2_shoesordersystem.repository.AccountRepository;

import java.util.Calendar;

public class UpdateProfileActivity extends AppCompatActivity {
    private EditText etFullName;
    private TextView tvEmail;
    private EditText etPhone;
    private TextView tvRole;
    private Spinner spGender;
    private EditText etDOB;
    private EditText etAddress;
    private Button btnCancel;
    private Button btnUpdate;
    private AccountRepository repository;
    private int accountId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile); // Đảm bảo đặt đúng layout XML

        // Ánh xạ view từ XML
        etFullName = findViewById(R.id.etFullName);
        tvEmail = findViewById(R.id.tvEmail);
        etPhone = findViewById(R.id.etPhone);
        tvRole = findViewById(R.id.tvRole);
        spGender = findViewById(R.id.spGender);
        etDOB = findViewById(R.id.etDOB);
        etAddress = findViewById(R.id.etAddress);
        btnCancel = findViewById(R.id.btnCancel);
        btnUpdate = findViewById(R.id.btnUpdate);

        // Danh sách giới tính
        String[] genders = {"Male", "Female"};

        // Tạo ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genders);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Gán Adapter vào Spinner
        spGender.setAdapter(adapter);

        // Lắng nghe Spinner giúp xác định item người dùng chọn
        spGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedGender = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "You choose: " + selectedGender, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Không chọn gì
            }
        });

        // Lấy accountId từ Intent
        accountId = getIntent().getIntExtra("ACCOUNT_ID", -1);

        // Kiểm tra nếu ID hợp lệ thì tải dữ liệu
        if (accountId != -1) {
            repository = new AccountRepository(this);
            loadAccountData(accountId);
        } else {
            Toast.makeText(this, "Don't found account!", Toast.LENGTH_SHORT).show();
        }
        // Xử lý sự kiện khi nhấn vào đê sửa DOB
        etDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int y = c.get(Calendar.YEAR);
                int m = c.get(Calendar.MONTH);
                int d = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        etDOB.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, y, m, d);
                dialog.show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateProfileActivity.this, ViewProfileDetailActivity.class);
                startActivity(intent);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                updateProfile();
            }
        });
    }

    private void loadAccountData(int accountId) {
        repository.getAccountById(accountId, new AccountRepository.OnAccountFetchListener() {
            @Override
            public void onAccountFetched(Account account) {
                runOnUiThread(() -> {
                    if (account != null) {
                        etFullName.setText(account.getFullname());
                        tvEmail.setText(account.getEmail());
                        etPhone.setText(account.getPhone());
                        etDOB.setText(account.getDob());
                        etAddress.setText(account.getAddress());

                        // Chuyển đổi role thành chuỗi tương ứng
                        String roleText;
                        switch (account.getRole()) {
                            case 0:
                                roleText = "Admin";
                                break;
                            case 1:
                                roleText = "Customer";
                                break;
                            case 2:
                                roleText = "Seller";
                                break;
                            case 3:
                                roleText = "Manager";
                                break;
                            default:
                                roleText = "Unknown Role";
                                break;
                        }
                        tvRole.setText(roleText);

                        // Set giới tính cho Spinner
                        if (account.getGender() == 0) {
                            spGender.setSelection(0); // 0 tương ứng với "Female"
                        } else {
                            spGender.setSelection(1); // 1 tương ứng với "Male"
                        }
                    }
                });
            }
        });
    }

    private void updateProfile() {
        if(accountId == -1){
            Toast.makeText(this, "Invalid account ID", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy dữ liệu từ form
        String fullName = etFullName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String dob = etDOB.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        int gender = spGender.getSelectedItemPosition(); // 0: Male, 1: Female

        // Kiểm tra dữ liệu hợp lệ
        if(fullName.isEmpty() || phone.isEmpty() || dob.isEmpty() || address.isEmpty()){
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        // Kiểm tra tên chỉ chứa chữ cái và khoảng trắng
        if (!fullName.matches("^[a-zA-Z\\s]+$")) {
            Toast.makeText(this, "Full name should contain only letters", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra số điện thoại chỉ chứa số và có độ dài hợp lệ
        if (!phone.matches("^[0-9]{10,15}$")) {
            Toast.makeText(this, "Phone number should contain only digits (10-15 characters)", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy dữ liệu hiện tại từ database trước khi cập nhật
        repository.getAccountById(accountId, existingAccount -> {
            if (existingAccount == null) {
                runOnUiThread(() ->
                        Toast.makeText(this, "Account not found", Toast.LENGTH_SHORT).show()
                );
                return;
            }

            // Giữ nguyên các giá trị cũ, chỉ cập nhật những gì người dùng đã thay đổi
            existingAccount.setFullname(fullName);
            existingAccount.setPhone(phone);
            existingAccount.setDob(dob);
            existingAccount.setAddress(address);
            existingAccount.setGender(gender);

            // Thực hiện cập nhật
            repository.updateAccount(existingAccount, success -> {
                runOnUiThread(() -> {
                    if (success) {
                        Toast.makeText(this, "Update Success!", Toast.LENGTH_SHORT).show();
                        // Chuyển về ViewProfileDetailActivity
                        Intent intent = new Intent(UpdateProfileActivity.this, ViewProfileDetailActivity.class);
                        intent.putExtra("ACCOUNT_ID", accountId);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, "Update Fail!", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        });
    }
}
