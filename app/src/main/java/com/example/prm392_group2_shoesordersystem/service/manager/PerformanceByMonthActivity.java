package com.example.prm392_group2_shoesordersystem.service.manager;


import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.adapter.RevenueAdapter;
import com.example.prm392_group2_shoesordersystem.entity.MonthSale;
import com.example.prm392_group2_shoesordersystem.repository.OrderRepository;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PerformanceByMonthActivity extends AppCompatActivity {

    private BarChart barChart;
    private RecyclerView recyclerView;
    private RevenueAdapter adapter;
    private List<MonthSale> revenueList;
    private OrderRepository orderRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_by_month_page);
        orderRepository = new OrderRepository(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        barChart = findViewById(R.id.barChart);
        recyclerView = findViewById(R.id.recyclerView);


        revenueList = orderRepository.ListMonthSale();


        setupBarChart();
        setupRecyclerView();
    }

    private void setupBarChart() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        final ArrayList<String> labels = new ArrayList<>();

        // Danh sách 12 màu sắc riêng biệt
        int[] colors = {
                Color.parseColor("#FF5733"), // Tháng 1 - Đỏ cam
                Color.parseColor("#33FF57"), // Tháng 2 - Xanh lá
                Color.parseColor("#3357FF"), // Tháng 3 - Xanh dương
                Color.parseColor("#FF33A1"), // Tháng 4 - Hồng
                Color.parseColor("#FFC300"), // Tháng 5 - Vàng
                Color.parseColor("#8D33FF"), // Tháng 6 - Tím
                Color.parseColor("#00FFFF"), // Tháng 7 - Xanh ngọc
                Color.parseColor("#FF7F50"), // Tháng 8 - Cam đậm
                Color.parseColor("#7FFF00"), // Tháng 9 - Xanh lục sáng
                Color.parseColor("#DC143C"), // Tháng 10 - Đỏ tươi
                Color.parseColor("#4682B4"), // Tháng 11 - Xanh thép
                Color.parseColor("#FFD700")  // Tháng 12 - Vàng kim
        };

        // Thêm dữ liệu vào BarChart
        for (int i = 0; i < revenueList.size(); i++) {
            entries.add(new BarEntry(i, (float) revenueList.get(i).getTotalPrice()));
            labels.add(String.valueOf(revenueList.get(i).getMonth()));
        }

        // Tạo BarDataSet với dữ liệu và màu sắc tùy chỉnh
        BarDataSet dataSet = new BarDataSet(entries, "Revenue");

        // Gán màu theo tháng
        List<Integer> barColors = new ArrayList<>();
        for (MonthSale sale : revenueList) {
            int monthIndex = sale.getMonth() - 1; // Chỉ số của tháng (0-11)
            barColors.add(colors[monthIndex]);
        }
        dataSet.setColors(barColors);

        // Định dạng văn bản
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(12f);

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);

        // Cấu hình trục X
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labels.size());

        // Ẩn mô tả
        barChart.getDescription().setEnabled(false);
        barChart.invalidate();
    }


    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<MonthSale> top3Months = new ArrayList<>(revenueList);
        Collections.sort(top3Months, Comparator.comparingDouble(MonthSale::getTotalPrice).reversed());

        if (top3Months.size() > 3) {
            top3Months = top3Months.subList(0, 3);
        }

        adapter = new RevenueAdapter(top3Months);
        recyclerView.setAdapter(adapter);
    }
}
