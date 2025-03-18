package com.example.prm392_group2_shoesordersystem.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_group2_shoesordersystem.R;

public class SizeGuideActivity extends AppCompatActivity {
    ImageView imgSizeGuide;
    Button btnBack;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_size_guide);

        imgSizeGuide = findViewById(R.id.imgSizeGuide);
        imgSizeGuide.setImageResource(R.drawable.size_guide);

        TextView txtHowToMeasure = findViewById(R.id.txtHowToMeasure);
        txtHowToMeasure.setText(
                "1. Place your foot on a white sheet of paper.\n" +
                        "2. Draw an outline around your foot.\n" +
                        "3. Measure the length from heel to the longest toe.\n" +
                        "4. Compare with the size chart to choose the appropriate shoe size."
        );

        TextView txtTips = findViewById(R.id.txtTips);
        txtTips.setText(
                "👞 If your feet are wide: Choose a size 0.5cm larger.\n" +
                        "👟 If wearing sneakers: Choose a size 1cm larger."
        );

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
    }
}