package com.example.prm392_group2_shoesordersystem.service.customer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.fragment.customer.CartFragment;
import com.example.prm392_group2_shoesordersystem.fragment.customer.CustomerProfileFragment;
import com.example.prm392_group2_shoesordersystem.fragment.customer.HomeFragment;
import com.example.prm392_group2_shoesordersystem.fragment.customer.ViewOrderListCustomerFragment;
import com.example.prm392_group2_shoesordersystem.fragment.customer.ViewProfileDetailFragment;
import com.example.prm392_group2_shoesordersystem.fragment.customer.ViewShoesListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ManageCustomerDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_customer_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            if (item.getItemId() == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (item.getItemId() == R.id.nav_shoes) {
                selectedFragment = new ViewShoesListFragment();
            } else if (item.getItemId() == R.id.nav_profile) {
                selectedFragment = new ViewProfileDetailFragment();
            }else if (item.getItemId() == R.id.nav_order) {
                selectedFragment = new ViewOrderListCustomerFragment();
            }else if (item.getItemId() == R.id.nav_cart) {
                selectedFragment = new CartFragment();
            }
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }
            return true;
        });
    }
}
