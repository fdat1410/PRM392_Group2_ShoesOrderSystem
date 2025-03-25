package com.example.prm392_group2_shoesordersystem.fragment.manager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.entity.Account;
import com.google.gson.Gson;

public class ManagePageFragmentProfile extends Fragment {
    ImageView btnUpdateProfile, btnChangePassword, btnForgotPassword;
    TextView tvUpdateProfile, tvChangePassword, tvForgotPassword, tvEmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_manage_page_fragment_profile, container, false);
        btnUpdateProfile = view.findViewById(R.id.btnUpdateProfile);
        tvUpdateProfile = view.findViewById(R.id.tvUpdateProfile);
        btnChangePassword = view.findViewById(R.id.btnChangePassword);
        tvChangePassword = view.findViewById(R.id.tvChangePassword);
        btnForgotPassword = view.findViewById(R.id.btnForgotPassword);
        tvForgotPassword = view.findViewById(R.id.tvForgotPassword);
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
        return view;
    }
}
