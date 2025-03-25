// PerformanceByCategoryActivity.java
package com.example.prm392_group2_shoesordersystem.service.manager;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.adapter.CategoryQuantityAdapter;
import com.example.prm392_group2_shoesordersystem.entity.CategorySale;
import com.example.prm392_group2_shoesordersystem.repository.CategoryRepository;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PerformanceByCategoryActivity extends AppCompatActivity {
    private PieChart pieChart;
    private RecyclerView recyclerView;
    private CategoryQuantityAdapter adapter;
    private List<CategorySale> categorySales;
    private CategoryRepository categoryRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_by_category);
        categoryRepository = new CategoryRepository(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        pieChart = findViewById(R.id.pieChart);
        recyclerView = findViewById(R.id.recyclerView);

        categorySales = new ArrayList<>();
        categorySales = categoryRepository.ListCategorySale();
//        categorySales.add(new CategorySale(1, "Sneakers", 10000));
//        categorySales.add(new CategorySale(2, "Casual Shoes", 25000));
//        categorySales.add(new CategorySale(3, "Running Shoes", 1200));
//        categorySales.add(new CategorySale(4, "Sneak", 10000));
//        categorySales.add(new CategorySale(5, "Casual ", 25000));

        setupPieChart();
        setupRecyclerView();
    }

    private void setupPieChart() {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);

        ArrayList<PieEntry> yValues = new ArrayList<>();
        for (CategorySale sale : categorySales) {
            yValues.add(new PieEntry(sale.getTotalQuantity(), sale.getCategory_name()));
        }

        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);


        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(255, 102, 102));
        colors.add(Color.rgb(102, 204, 255));
        colors.add(Color.rgb(102, 255, 178));
        colors.add(Color.rgb(255, 204, 102));
        colors.add(Color.rgb(204, 102, 255));

        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.WHITE);

        pieChart.setData(data);
        pieChart.invalidate();
    }





    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        List<CategorySale> top3Months = new ArrayList<>(categorySales);
        Collections.sort(top3Months, Comparator.comparingInt(CategorySale::getTotalQuantity).reversed());

        if (top3Months.size() > 3) {
            top3Months = top3Months.subList(0, 3);
        }


        adapter = new CategoryQuantityAdapter(top3Months);
        recyclerView.setAdapter(adapter);
    }
}
