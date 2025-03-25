package com.example.prm392_group2_shoesordersystem.fragment.customer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.adapter.ShoeCustomerPageAdapter;
import com.example.prm392_group2_shoesordersystem.adapter.ShoesAdapter;
import com.example.prm392_group2_shoesordersystem.entity.Category;
import com.example.prm392_group2_shoesordersystem.entity.Shoes;
import com.example.prm392_group2_shoesordersystem.repository.CategoryRepository;
import com.example.prm392_group2_shoesordersystem.repository.ShoesRepository;

import java.util.ArrayList;
import java.util.List;

    public class ViewShoesListFragment extends Fragment {
        private RecyclerView recyclerView;
        private ShoeCustomerPageAdapter shoesAdapter;
        private ShoesRepository shoesRepository;
        private CategoryRepository categoryRepository;
        private List<Shoes> shoesList;
        private EditText etSearch, etMinPrice, etMaxPrice;
        private Spinner spinnerCategory;
        private Button btnSearch;

        public ViewShoesListFragment() {
        }

        @SuppressLint("MissingInflatedId")
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_view_shoes_list, container, false);

            recyclerView = view.findViewById(R.id.recyclerView);
            etSearch = view.findViewById(R.id.etSearch);
            etMinPrice = view.findViewById(R.id.etMinPrice);
            etMaxPrice = view.findViewById(R.id.etMaxPrice);
            spinnerCategory = view.findViewById(R.id.spinnerCategory);
            btnSearch = view.findViewById(R.id.btnSearch);

            shoesRepository = new ShoesRepository(requireContext());
            categoryRepository = new CategoryRepository(requireContext());
            recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));

            loadCategories();
            loadShoes(null, null, null, "All");

            btnSearch.setOnClickListener(v -> searchShoes());

            return view;
        }

        private void loadCategories() {
            List<Category> categories = categoryRepository.getAllCategories();
            List<String> categoryNames = new ArrayList<>();
            categoryNames.add("All");
            for (Category category : categories) {
                categoryNames.add(category.getCategory_name());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, categoryNames);
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

                if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
                    Toast.makeText(requireContext(), "Min price cannot be greater than max price!", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (NumberFormatException e) {
                Toast.makeText(requireContext(), "Invalid price format", Toast.LENGTH_SHORT).show();
                return;
            }

            loadShoes(keyword, minPrice, maxPrice, category);
        }

        private void loadShoes(String keyword, Double minPrice, Double maxPrice, String category) {
            shoesList = shoesRepository.searchShoes(keyword, minPrice, maxPrice, category);
            if (shoesList == null) {
                shoesList = new ArrayList<>();
            }
            shoesAdapter = new ShoeCustomerPageAdapter(shoesList, requireContext());
            recyclerView.setAdapter(shoesAdapter);
        }
    }
