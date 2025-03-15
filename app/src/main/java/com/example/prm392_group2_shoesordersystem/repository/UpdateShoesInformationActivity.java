package com.example.prm392_group2_shoesordersystem.repository;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.entity.Category;
import com.example.prm392_group2_shoesordersystem.entity.Shoes;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class UpdateShoesInformationActivity extends AppCompatActivity {

    private EditText edtName, edtPrice, edtDescription;
    private Spinner spinnerCategory;
    private Button btnUpload, btnUpdate;
    private ImageView imgUpload;
    private ShoesRepository shoesRepository;
    private CategoryRepository categoryRepository;
    private static final int PICK_IMAGE_REQUEST = 1;
    private String selectedImageUri;
    private int shoes_category_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_shoes_information);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edtName = findViewById(R.id.edtName);
        edtPrice = findViewById(R.id.edtPrice);
        edtDescription = findViewById(R.id.edtDescription);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        imgUpload = findViewById(R.id.imgUpload);
        btnUpload = findViewById(R.id.btnUpload);
        btnUpdate = findViewById(R.id.btnUpdate);
        categoryRepository = new CategoryRepository(this);
        //set text
        Intent intent = getIntent();
        if (intent != null) {
            edtName.setText(intent.getStringExtra("SHOES_NAME"));
            edtPrice.setText(String.valueOf(intent.getDoubleExtra("SHOES_PRICE", 0)));
            edtDescription.setText(intent.getStringExtra("SHOES_DESCRIPTION"));
            selectedImageUri = intent.getStringExtra("SHOES_IMG");
            Picasso.get().load(selectedImageUri).into(imgUpload);
            shoes_category_id = intent.getIntExtra("SHOES_CATEGORY_ID", 0);
        }

        List<Category> categories = categoryRepository.getAllCategories();
        // Chuyển danh sách Category thành danh sách tên category
        List<String> categoryNames = new ArrayList<>();
        for (Category category : categories) {
            categoryNames.add(category.category_name);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                categoryNames);
        //set giá trị cho spinner
        int selectedIndex = 0; // Vị trí mặc định

        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).category_id == intent.getIntExtra("SHOES_CATEGORY_ID", 0)) {
                selectedIndex = i; // Tìm vị trí tương ứng với category_id
            }
        }
        this.spinnerCategory.setAdapter(adapter);
        spinnerCategory.setSelection(selectedIndex); // Đặt Spinner đúng vị trí
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        shoesRepository = new ShoesRepository(this);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shoes shoes = new Shoes();
                shoes.shoes_id = getIntent().getIntExtra("SHOES_ID", 0);
                shoes.shoes_name = edtName.getText().toString();
                String price_string = edtPrice.getText().toString();
                shoes.price = Double.parseDouble(price_string);
                shoes.description = edtDescription.getText().toString();
                shoes.img = selectedImageUri;
                // get id from position
                int selectedPosition = spinnerCategory.getSelectedItemPosition();
                if (selectedPosition >= 0 && selectedPosition < categories.size()) {
                    shoes.category_id = categories.get(selectedPosition).category_id;
                }
                shoes.shoes_status = 1;
                shoes.create_by = null;
                shoes.update_by = null;
                updateShoes(shoes);
            }
        });

    }
    // Mở thư viện ảnh
    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true); // Optional: Limit to local storage
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                getContentResolver().takePersistableUriPermission(
                        imageUri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                );
                selectedImageUri = imageUri.toString();
                Picasso.get().load(imageUri).into(imgUpload); // Show selected image
            }
        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
        }

    }
    // Update giày
    private void updateShoes(Shoes shoes) {
        try {
            // Cập nhật giày trong database
            shoesRepository.updateShoesById(shoes.shoes_id, shoes.shoes_name, shoes.price, shoes.img, shoes.description, shoes.category_id);
            Toast.makeText(this, "Update Shoes Successfully!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Update Failed!", Toast.LENGTH_SHORT).show();
        }
    }
}