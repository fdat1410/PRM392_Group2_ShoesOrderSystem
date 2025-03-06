package com.example.prm392_group2_shoesordersystem;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group2_shoesordersystem.adapter.ManagePageAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Arrays;
import java.util.List;

public class ManagePageActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    private BottomNavigationView bottomNavigationView;
    private NavigationView navigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_page);

        // Ánh xạ view
        drawerLayout = findViewById(R.id.drawer_layout);
        recyclerView = findViewById(R.id.recyclerView);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        navigationView = findViewById(R.id.navigation_view);
        Toolbar toolbar = findViewById(R.id.toolbar);

        // Xử lý mở menu khi nhấn icon 3 gạch
        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        // Danh sách các mục
        List<String> items = Arrays.asList(
                "Sale Performance by Month",
                "Sale Performance by Category",
                "Top Customer",
                "Top Product"
        );

        List<Integer> icons = Arrays.asList(
                R.drawable.baseline_feed_24,
                R.drawable.baseline_checklist_24,
                R.drawable.baseline_account_box_24,
                R.drawable.baseline_add_shopping_cart_24
        );

        // Cấu hình RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ManagePageAdapter(this, items, icons));

        // Xử lý sự kiện điều hướng trong Navigation Drawer
        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_logout) {
                Toast.makeText(this, "Logged out!", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(GravityCompat.START);
            }
            return true;
        });

        // Xử lý sự kiện điều hướng dưới cùng
//        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
//            switch (item.getItemId()) {
//                case R.id.nav_home:
//                    Toast.makeText(this, "Home clicked", Toast.LENGTH_SHORT).show();
//                    return true;
//                case R.id.nav_seller_management:
//                    Toast.makeText(this, "Seller Management clicked", Toast.LENGTH_SHORT).show();
//                    return true;
//                case R.id.nav_profile:
//                    Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show();
//                    return true;
//                default:
//                    return false;
//            }
//        });
    }
}

