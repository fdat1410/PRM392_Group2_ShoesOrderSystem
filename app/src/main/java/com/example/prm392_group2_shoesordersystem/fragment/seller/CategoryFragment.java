package com.example.prm392_group2_shoesordersystem.fragment.seller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.adapter.CategoryAdapter;
import com.example.prm392_group2_shoesordersystem.entity.Category;
import com.example.prm392_group2_shoesordersystem.repository.CategoryRepository;
import com.example.prm392_group2_shoesordersystem.service.seller.AddNewCategoryActivity;

import java.util.List;

public class CategoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private Button btnAdd;
    private CategoryRepository categoryRepository;
    Context context = getContext();
    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_fragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        categoryRepository = new CategoryRepository(getContext());
        List<Category> categories =categoryRepository.getAllCategories();
        CategoryAdapter categoryAdapter = new CategoryAdapter(requireContext(),categories, categoryRepository);
        recyclerView.setAdapter(categoryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        btnAdd = view.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddNewCategoryActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}