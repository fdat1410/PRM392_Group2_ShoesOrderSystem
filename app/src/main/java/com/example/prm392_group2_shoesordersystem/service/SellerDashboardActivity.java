package com.example.prm392_group2_shoesordersystem.service;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;
import com.example.prm392_group2_shoesordersystem.adapter.ViewPagerAdapter;
import com.example.prm392_group2_shoesordersystem.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class    SellerDashboardActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ViewPager2 viewPager;
    private ImageButton btnOpenDrawer;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_seller_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        viewPager = findViewById(R.id.view_pager);
        ViewPagerAdapter viewPagerAdapter= new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.action_home) {
                viewPager.setCurrentItem(0);
                //selectedFragment = new HomeFragment();
            } else if (item.getItemId() == R.id.action_fav) {
                viewPager.setCurrentItem(1);
                //selectedFragment = new FavouriteFragment();
            } else if (item.getItemId() == R.id.action_profile) {
                viewPager.setCurrentItem(2);
                //selectedFragment = new ProfileFragment();
            }
            return true;
        });
    }
}