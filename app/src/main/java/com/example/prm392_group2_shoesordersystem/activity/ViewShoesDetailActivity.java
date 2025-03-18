package com.example.prm392_group2_shoesordersystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prm392_group2_shoesordersystem.R;
import com.squareup.picasso.Picasso;

public class ViewShoesDetailActivity extends AppCompatActivity {
    private ImageView imgShoe;
    private TextView txtShoeName, txtPrice, txtDescription;
    private Button btnAddToCart, btnSizeGuide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_shoes_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imgShoe = findViewById(R.id.imgShoe);
        txtShoeName = findViewById(R.id.txtShoeName);
        txtPrice = findViewById(R.id.txtPrice);
        txtDescription = findViewById(R.id.txtDescription);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnSizeGuide = findViewById(R.id.btnSizeGuide);

        String shoeName = getIntent().getStringExtra("shoes_name");
        double shoePrice = getIntent().getDoubleExtra("shoes_price", 0);
        String shoeDescription = getIntent().getStringExtra("shoes_description");
        String shoeImg = getIntent().getStringExtra("shoes_img");

        txtShoeName.setText(shoeName);
        txtPrice.setText(String.format("%,.0f", shoePrice));
        txtDescription.setText(shoeDescription);
        Picasso.get().load(shoeImg).placeholder(R.drawable.ic_launcher_background).into(imgShoe);

        btnSizeGuide.setOnClickListener(v -> {
            Intent intent = new Intent(ViewShoesDetailActivity.this, SizeGuideActivity.class);
            startActivity(intent);
        });

    }
}


