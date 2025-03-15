package com.example.prm392_group2_shoesordersystem.service;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.fragment.ManagePageFragmentHome;
import com.example.prm392_group2_shoesordersystem.fragment.ManagePageFragmentProfile;
import com.example.prm392_group2_shoesordersystem.fragment.ManagePageFragmentSellerManagement;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.prm392_group2_shoesordersystem.entity.Account;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ManagePageActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_page);

        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);


        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);


        TextView navHeaderTitle = headerView.findViewById(R.id.name);
        TextView navHeaderSubtitle = headerView.findViewById(R.id.email);


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String accountJson = prefs.getString("USER_ACCOUNT", null);

        if (accountJson != null) {
            Gson gson = new Gson();
            Account account = gson.fromJson(accountJson, Account.class);

            if (account != null) {
                if (navHeaderTitle != null) {
                    navHeaderTitle.setText(account.getFullname());
                }
                if (navHeaderSubtitle != null) {
                    navHeaderSubtitle.setText(account.getEmail());
                }
            }
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ManagePageFragmentHome())
                    .commit();
        }

        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            if (item.getItemId() == R.id.nav_home) {
                selectedFragment = new ManagePageFragmentHome();
            } else if (item.getItemId() == R.id.nav_seller_management) {
                selectedFragment = new ManagePageFragmentSellerManagement();
            } else if (item.getItemId() == R.id.nav_profile) {
                selectedFragment = new ManagePageFragmentProfile();
            }
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }
            return true;
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_logout) {
                    logout();
                    return true;
                }
                return false;
            }
        });
    }

    private void logout() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("USER_ACCOUNT");
        editor.putBoolean("LOGGED_IN", false);
        editor.apply();

        // Hiển thị thông báo
        Toast.makeText(this, "Logged out successfully!", Toast.LENGTH_SHORT).show();


        Intent intent = new Intent(ManagePageActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}


