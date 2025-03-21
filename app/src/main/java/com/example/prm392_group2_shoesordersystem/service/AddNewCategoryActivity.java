package com.example.prm392_group2_shoesordersystem.service;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.entity.Category;
import com.example.prm392_group2_shoesordersystem.entity.Shoes;
import com.example.prm392_group2_shoesordersystem.entity.Size;
import com.example.prm392_group2_shoesordersystem.repository.CategoryRepository;

public class AddNewCategoryActivity extends AppCompatActivity {
    EditText edtCategoryName;
    Button btnSave;
    CategoryRepository categoryRepository;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_new_category);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edtCategoryName = findViewById(R.id.edtCategoryName);
        btnSave = findViewById(R.id.btnSave);
        categoryRepository = new CategoryRepository(this);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInputs()){
                    Category category = new Category();
                    category.category_name = edtCategoryName.getText().toString();
                    addCategoryToDatabase(category);
                }
            }
        });
    }
    private void addCategoryToDatabase(Category category) {
        try {
            categoryRepository.insertCategory(category);
            Toast.makeText(this, "Add Category Successfully!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, SellerDashboardActivity.class);
            startActivity(intent);
        }catch (Exception e){
            Toast.makeText(this, "Add Category Failed!", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean validateInputs() {
        String name = edtCategoryName.getText().toString().trim();
        // Validate Name
        if (name.isEmpty()) {
            edtCategoryName.setError("Product name cannot be empty");
            edtCategoryName.requestFocus();
            return false;
        }
        return true;
    }
}