package com.example.prm392_group2_shoesordersystem.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
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
import com.example.prm392_group2_shoesordersystem.repository.ShoesRepository;
import com.squareup.picasso.Picasso;

public class AddNewShoesActivity extends AppCompatActivity {

    private EditText edtName, edtPrice, edtDescription;
    private Spinner spinnerCategory;
    private Button btnAdd, btnUpload;
    private ImageView imgUpload;
    private CheckBox chk38, chk39, chk40, chk41;
    private ShoesRepository shoesRepository;
    private static final int PICK_IMAGE_REQUEST = 1;
    private String selectedImageUri;

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
        spinnerCategory = findViewById(R.id.category);
        btnAdd = findViewById(R.id.btnAdd);
        imgUpload = findViewById(R.id.imgUpload);
        btnUpload = findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        chk38 = findViewById(R.id.cbSize38);
        chk39 = findViewById(R.id.cbSize39);
        chk40 = findViewById(R.id.cbSize40);
        chk41 = findViewById(R.id.cbSize41);
        shoesRepository = new ShoesRepository();
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
                //edtImage.setText(selectedImageUri);
                Picasso.get().load(imageUri).into(imgUpload); // Show selected image
            }
        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
        }

    }

    // Lưu đường dẫn ảnh vào Room Database qua UserRepository
//    private void saveImageToDatabase(String imageUri) {
//        try {
//            shoesRepository.(new User("dat", "dat@gmail.com", edtImage.getText().toString()));
//            Toast.makeText(this, "Lưu ảnh thành công!", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(this, UserListActivity.class);
//            startActivity(intent);
//        }catch (Exception e){
//            Toast.makeText(this, "Lưu ảnh that bai!", Toast.LENGTH_SHORT).show();
//        }
//    }
}