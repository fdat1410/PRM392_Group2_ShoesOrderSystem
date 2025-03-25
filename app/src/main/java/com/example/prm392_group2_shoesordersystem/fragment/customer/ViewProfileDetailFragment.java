package com.example.prm392_group2_shoesordersystem.fragment.customer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.adapter.ShoeCustomerPageAdapter;
import com.example.prm392_group2_shoesordersystem.entity.Account;
import com.example.prm392_group2_shoesordersystem.repository.AccountRepository;
import com.example.prm392_group2_shoesordersystem.repository.ShoesRepository;
import com.example.prm392_group2_shoesordersystem.service.customer.ChangePasswordActivity;
import com.example.prm392_group2_shoesordersystem.service.customer.UpdateProfileActivity;
import com.example.prm392_group2_shoesordersystem.service.customer.ViewProfileDetailActivity;
import com.example.prm392_group2_shoesordersystem.service.guest.LoginActivity;
import com.example.prm392_group2_shoesordersystem.service.manager.ManagePageActivity;
import com.google.gson.Gson;

public class ViewProfileDetailFragment extends Fragment {
    private TextView tvFullName;
    private TextView tvEmail;
    private TextView tvPhone;
    private TextView tvRole;
    private TextView tvGender;
    private TextView tvDOB;
    private TextView tvAddress, tvLogout;
    private Button btnUpdate, btnChangePassword;
    ImageView btnLogout;
    private AccountRepository repository;
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_detail, container, false);
        tvFullName = view.findViewById(R.id.tvFullName);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvPhone = view.findViewById(R.id.tvPhone);
        tvRole = view.findViewById(R.id.tvRole);
        tvGender = view.findViewById(R.id.tvGender);
        tvDOB = view.findViewById(R.id.tvDOB);
        tvAddress = view.findViewById(R.id.tvAddress);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        tvLogout = view.findViewById(R.id.tvLogout);
        btnLogout = view.findViewById(R.id.btnLogout);
        btnChangePassword = view.findViewById(R.id.btnChangePassword);

        repository = new AccountRepository(getContext());



        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String accountJson = prefs.getString("USER_ACCOUNT", null);
        boolean isLoggedIn = prefs.getBoolean("LOGGED_IN", false);


        Gson gson = new Gson();
        Account account = gson.fromJson(accountJson, Account.class);
        loadAccountData(account);


        btnUpdate.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), UpdateProfileActivity.class);
            intent.putExtra("ACCOUNT_ID", account.account_id);
            startActivity(intent);
            getActivity().finish();
        });
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        return view;
    }
    private void loadAccountData(Account account) {


        if (account != null) {
            tvFullName.setText(account.getFullname());
            tvEmail.setText(account.getEmail());
            tvPhone.setText(account.getPhone());

            // Chuyển đổi role thành chuỗi tương ứng
            String roleText;
            switch (account.getRole()) {
                case 0:
                    roleText = "Customer";
                    break;
                case 1:
                    roleText = "Admin";
                    break;
                case 2:
                    roleText = "Manager";
                    break;
                case 3:
                    roleText = "Seller";
                    break;
                default:
                    roleText = "Unknown Role";
                    break;
            }
            tvRole.setText(roleText);

            // Chuyển đổi gender thành chuỗi tương ứng
            String genderText = (account.getGender() == 0) ? "Female" : "Male";
            tvGender.setText(genderText);

            tvDOB.setText(account.getDob());
            tvAddress.setText(account.getAddress());

        }

    }

    private void logout() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("USER_ACCOUNT");
        editor.putBoolean("LOGGED_IN", false);
        editor.apply();

        // Hiển thị thông báo
        Toast.makeText(getContext(), "Logged out successfully!", Toast.LENGTH_SHORT).show();


        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }
}
