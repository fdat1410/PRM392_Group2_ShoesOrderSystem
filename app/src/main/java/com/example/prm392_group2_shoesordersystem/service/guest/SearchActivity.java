package com.example.prm392_group2_shoesordersystem.service.guest;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.adapter.SearchShoesAdapter;
import com.example.prm392_group2_shoesordersystem.entity.Category;
import com.example.prm392_group2_shoesordersystem.entity.Shoes;
import com.example.prm392_group2_shoesordersystem.repository.CategoryRepository;
import com.example.prm392_group2_shoesordersystem.repository.ShoesRepository;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private EditText etSearch, etMinPrice, etMaxPrice;
    private Spinner spinnerCategory;
    private Button btnSearch;
    private RecyclerView recyclerViewSearch;
    private ShoesRepository shoesRepository;
    private CategoryRepository categoryRepository;
    private SearchShoesAdapter searchShoesAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etSearch = findViewById(R.id.etSearch);
        etMinPrice = findViewById(R.id.etMinPrice);
        etMaxPrice = findViewById(R.id.etMaxPrice);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        btnSearch = findViewById(R.id.btnSearch);
        recyclerViewSearch = findViewById(R.id.recyclerViewSearch);

        shoesRepository = new ShoesRepository(this);
        categoryRepository = new CategoryRepository(this);
        searchShoesAdapter = new SearchShoesAdapter(this, null);
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewSearch.setAdapter(searchShoesAdapter);

        loadCategories();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchShoes();
            }
        });
    }

    private void loadCategories() {
        List<Category> categories = categoryRepository.getAllCategories();
        List<String> categoryNames = new ArrayList<>();
        categoryNames.add("All"); // Add "All" option
        for (Category category : categories) {
            categoryNames.add(category.getCategory_name());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
    }

    private void searchShoes() {
        String keyword = etSearch.getText().toString().trim();
        Double minPrice = null;
        Double maxPrice = null;
        String category = spinnerCategory.getSelectedItem().toString();

        try {
            if (!etMinPrice.getText().toString().isEmpty()) {
                minPrice = Double.parseDouble(etMinPrice.getText().toString());
            }
            if (!etMaxPrice.getText().toString().isEmpty()) {
                maxPrice = Double.parseDouble(etMaxPrice.getText().toString());
            }

            // Kiểm tra minPrice > maxPrice
            if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
                Toast.makeText(this, "Min price cannot be greater than max price!", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid price format", Toast.LENGTH_SHORT).show();
            return;
        }

        List<Shoes> shoes = shoesRepository.searchShoes(keyword, minPrice, maxPrice, category);
        if (shoes == null) {
            shoes = new ArrayList<>();
        }

        SearchShoesAdapter adapter = new SearchShoesAdapter(this, shoes);
        recyclerViewSearch.setAdapter(adapter);
    }

}