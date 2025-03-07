package com.example.prm392_group2_shoesordersystem.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.example.prm392_group2_shoesordersystem.repository.CategoryRepository;
import com.example.prm392_group2_shoesordersystem.repository.ShoesRepository;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AddNewShoesActivity extends AppCompatActivity {

    private EditText edtName, edtPrice, edtDescription;
    private Spinner spinnerCategory;
    private Button btnAdd, btnUpload;
    private ImageView imgUpload;
    private ShoesRepository shoesRepository;
    private CategoryRepository categoryRepository;
    private static final int PICK_IMAGE_REQUEST = 1;
    private String selectedImageUri;
    private String imgPath;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_new_shoes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edtName = findViewById(R.id.edtName);
        edtPrice = findViewById(R.id.edtPrice);
        edtDescription = findViewById(R.id.edtDescription);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        btnAdd = findViewById(R.id.btnAdd);
        imgUpload = findViewById(R.id.imgUpload);
        btnUpload = findViewById(R.id.btnUpload);
        categoryRepository = new CategoryRepository(this);
        List<Category> categories = categoryRepository.getAllCategories();
        // Chuyển danh sách Category thành danh sách tên danh mục
        List<String> categoryNames = new ArrayList<>();
        for (Category category : categories) {
            categoryNames.add(category.category_name);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                categoryNames);

        this.spinnerCategory.setAdapter(adapter);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        shoesRepository = new ShoesRepository(this);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shoes shoes = new Shoes();
                shoes.shoes_name = edtName.getText().toString();
                String price_string = edtPrice.getText().toString();
                shoes.price = Double.parseDouble(price_string);
                shoes.description = edtDescription.getText().toString();
                shoes.img = selectedImageUri;
                // Lấy category_id từ Spinner dựa trên vị trí được chọn
                int selectedPosition = spinnerCategory.getSelectedItemPosition();
                if (selectedPosition >= 0 && selectedPosition < categories.size()) {
                    shoes.category_id = categories.get(selectedPosition).category_id;
                }
                shoes.shoes_status = 1;
                shoes.create_by = null;
                shoes.update_by = null;
                addShoesToDatabase(shoes);

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

    // Add shoes with ShoesRepository
    private void addShoesToDatabase(Shoes shoes) {
        try {
            shoesRepository.insertShoe(shoes);
            Toast.makeText(this, "Add Shoes Successfully!", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(this, "Add Shoes Failed!", Toast.LENGTH_SHORT).show();
        }
    }
}