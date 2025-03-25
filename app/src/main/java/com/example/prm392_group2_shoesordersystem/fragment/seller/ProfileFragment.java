package com.example.prm392_group2_shoesordersystem.fragment.seller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.entity.Account;
import com.example.prm392_group2_shoesordersystem.repository.AccountRepository;
import com.example.prm392_group2_shoesordersystem.service.guest.LoginActivity;
import com.example.prm392_group2_shoesordersystem.service.manager.ManagePageActivity;
import com.google.gson.Gson;

public class ProfileFragment extends Fragment {
    ImageView btnUpdateProfile, btnChangePassword, btnForgotPassword, btnLogout;
    TextView tvUpdateProfile, tvChangePassword, tvForgotPassword, tvLogout, tvEmail;
    AccountRepository accountRepository = new AccountRepository(getContext());

    Context context = getContext();
    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        btnUpdateProfile = view.findViewById(R.id.btnUpdateProfile);
        tvUpdateProfile = view.findViewById(R.id.tvUpdateProfile);
        btnChangePassword = view.findViewById(R.id.btnChangePassword);
        tvChangePassword = view.findViewById(R.id.tvChangePassword);
        btnForgotPassword = view.findViewById(R.id.btnForgotPassword);
        tvForgotPassword = view.findViewById(R.id.tvForgotPassword);
        btnLogout = view.findViewById(R.id.btnLogout);
        tvLogout = view.findViewById(R.id.tvLogout);
        tvEmail = view.findViewById(R.id.tvEmail);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String accountJson = prefs.getString("USER_ACCOUNT", null);
        boolean isLoggedIn = prefs.getBoolean("LOGGED_IN", false);
        Gson gson = new Gson();
        Account account = gson.fromJson(accountJson, Account.class);
        if(account != null){
            tvEmail.setText(account.getEmail());
        }else {
            tvEmail.setText("Email not found");
        }
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
