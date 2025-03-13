package com.example.prm392_group2_shoesordersystem.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.entity.Category;
import com.example.prm392_group2_shoesordersystem.entity.Shoes;
import com.example.prm392_group2_shoesordersystem.entity.Size;
import com.example.prm392_group2_shoesordersystem.repository.CategoryRepository;
import com.example.prm392_group2_shoesordersystem.repository.ShoesRepository;
import com.example.prm392_group2_shoesordersystem.repository.SizeRepository;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavouriteFragment extends Fragment {
    private EditText edtName, edtPrice, edtDescription;
    private Spinner spinnerCategory;
    private Button btnAdd, btnUpload;
    private ImageView imgUpload;
    private ShoesRepository shoesRepository;
    private CategoryRepository categoryRepository;
    private SizeRepository sizeRepository;
    private static final int PICK_IMAGE_REQUEST = 1;
    private String selectedImageUri;
    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favourite_fragment, container, false);
        edtName = view.findViewById(R.id.edtName);
        edtPrice = view.findViewById(R.id.edtPrice);
        edtDescription = view.findViewById(R.id.edtDescription);
        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        btnAdd = view.findViewById(R.id.btnAdd);
        imgUpload = view.findViewById(R.id.imgUpload);
        btnUpload = view.findViewById(R.id.btnUpload);
        categoryRepository = new CategoryRepository(requireContext());
        sizeRepository = new SizeRepository(requireContext());
        List<Category> categories = categoryRepository.getAllCategories();
        // get category name of list category
        List<String> categoryNames = new ArrayList<>();
        for (Category category : categories) {
            categoryNames.add(category.category_name);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(),
                android.R.layout.simple_spinner_item,
                categoryNames);

        this.spinnerCategory.setAdapter(adapter);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        shoesRepository = new ShoesRepository(requireContext());
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shoes shoes = new Shoes();
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
                addShoesToDatabase(shoes);
            }
        });
        return view;
    }
    // open file chooser
    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true); // Optional: Limit to local storage
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                requireActivity().getContentResolver().takePersistableUriPermission(
                        imageUri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                );
                selectedImageUri = imageUri.toString();
                Picasso.get().load(imageUri).into(imgUpload); // Show selected image
            }
        } else {
            Toast.makeText(requireContext(), "No image selected", Toast.LENGTH_SHORT).show();
        }

    }

    // Add shoes with ShoesRepository
    private void addShoesToDatabase(Shoes shoes) {
        try {
            Shoes shoesId = shoesRepository.insertShoes(shoes);
            int shoes_id = Integer.parseInt(String.valueOf(shoesId.shoes_id));
            for (int i = 36; i < 44; i++) {
                sizeRepository.insertSize(new Size(i, 0, shoes_id));
            }
            Toast.makeText(requireContext(), "Add Shoes Successfully!", Toast.LENGTH_SHORT).show();
            // Chuyển sang HomeFragment sau khi thêm thành công
//            Fragment homeFragment = new HomeFragment();
//            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
//            fragmentManager.beginTransaction()
//                    .replace(R.id.home_fragment, homeFragment) // Thay bằng ID container của Fragment
//                    .addToBackStack(null)
//                    .commit();
        }catch (Exception e){
            Toast.makeText(requireContext(), "Add Shoes Failed!", Toast.LENGTH_SHORT).show();
        }

    }

}