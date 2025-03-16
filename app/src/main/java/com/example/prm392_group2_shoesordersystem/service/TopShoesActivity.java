package com.example.prm392_group2_shoesordersystem.service;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.entity.ShoesSale;
import com.example.prm392_group2_shoesordersystem.repository.ShoesRepository;

import java.util.ArrayList;
import java.util.List;

public class TopShoesActivity extends AppCompatActivity {
    private TableLayout tableLayout;
    private ShoesRepository shoesRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_shoes);

        shoesRepository = new ShoesRepository(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        tableLayout = findViewById(R.id.tableLayout);

        // Dữ liệu cứng
        List<ShoesSale> shoesList = new ArrayList<>();
        shoesList = shoesRepository.ListShoesSale();
//        shoesList.add(new ShoesSale(1, 5000, 10, "shoes_1", "Nike Air Max"));
//        shoesList.add(new ShoesSale(2, 4500, 8, "shoes_2", "Adidas UltraBoost"));
//        shoesList.add(new ShoesSale(3, 4200, 7, "shoes_3", "Puma RS-X"));
//        shoesList.add(new ShoesSale(4, 3900, 6, "shoes_4", "New Balance 990"));
//        shoesList.add(new ShoesSale(5, 3500, 5, "shoes_5", "Reebok Classic"));


        for (ShoesSale shoes : shoesList) {
            TableRow row = new TableRow(this);
            row.setPadding(8, 8, 8, 8);
            row.setGravity(Gravity.CENTER_VERTICAL);

            TextView txtId = createTextView(String.valueOf(shoes.getShoes_id()));
            TextView txtName = createTextView(shoes.getShoes_name());
            TextView txtQuantity = createTextView(String.valueOf(shoes.getItem_bought()));
            TextView txtAmount = createTextView(String.valueOf(shoes.getTotal_amount()));
            ImageView imgShoes = createImageView(shoes.getImg());

            row.addView(txtId);
            row.addView(txtName);
            row.addView(txtQuantity);
            row.addView(txtAmount);
            row.addView(imgShoes);

            tableLayout.addView(row);
        }
    }

    private TextView createTextView(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setPadding(8, 8, 8, 8);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(14);
        textView.setBackgroundResource(R.drawable.cell_border);
        return textView;
    }

    private ImageView createImageView(String imgName) {
        ImageView imageView = new ImageView(this);
        int resId = getResources().getIdentifier(imgName, "drawable", getPackageName());

        if (resId == 0) { // Nếu không tìm thấy ảnh, đặt ảnh mặc định
            resId = R.drawable.avatar_trang_4; // Đảm bảo có ảnh default_image trong thư mục drawable
        }

        imageView.setImageResource(resId);
        imageView.setPadding(8, 8, 8, 8);
        imageView.setAdjustViewBounds(true);
        imageView.setMaxHeight(100);
        imageView.setMaxWidth(100);
        return imageView;
    }


}
