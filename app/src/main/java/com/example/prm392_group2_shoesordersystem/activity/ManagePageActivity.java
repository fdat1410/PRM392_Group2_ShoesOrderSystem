package com.example.prm392_group2_shoesordersystem.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.prm392_group2_shoesordersystem.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ManagePageActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_page);

        // Ánh xạ DrawerLayout
        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);


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
    }
}


