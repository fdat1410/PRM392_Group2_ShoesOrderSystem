package com.example.prm392_group2_shoesordersystem.service;



import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.github.mikephil.charting.utils.ColorTemplate;
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

        barChart = findViewById(R.id.barChart);
        recyclerView = findViewById(R.id.recyclerView);


        revenueList = orderRepository.ListMonthSale();


        setupBarChart();
        setupRecyclerView();
    }

    private void setupBarChart() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        final ArrayList<String> labels = new ArrayList<>();

        for (int i = 0; i < revenueList.size(); i++) {
            entries.add(new BarEntry((float) i, (float) revenueList.get(i).getTotalPrice()));
            labels.add( String.valueOf(revenueList.get(i).getMonth()));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Revenue");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(12f);

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labels.size());

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
