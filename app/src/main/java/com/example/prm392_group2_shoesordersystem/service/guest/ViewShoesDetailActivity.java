package com.example.prm392_group2_shoesordersystem.service.guest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.entity.Account;
import com.example.prm392_group2_shoesordersystem.entity.Cart;
import com.example.prm392_group2_shoesordersystem.repository.CartRepository;
import com.example.prm392_group2_shoesordersystem.service.customer.CheckOutActivity;
import com.example.prm392_group2_shoesordersystem.service.customer.ViewListFeedbackActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ViewShoesDetailActivity extends AppCompatActivity {
    private ImageView imgShoe;
    private TextView txtShoeName, txtPrice, txtDescription;
    private Button btnAddToCart, btnSizeGuide, btnViewFb, btnCheckout;
    private RadioGroup rgSizes1, rgSizes2;
    private int selectedSize = 0;
    private CartRepository cartRepository;
    private String shoes_id;
    private String imgUrl;

    @SuppressLint("MissingInflatedId")
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
        btnViewFb = findViewById(R.id.btnViewFb);
        btnViewFb.setOnClickListener(v -> {
            Intent intent = new Intent(ViewShoesDetailActivity.this, ViewListFeedbackActivity.class);
            intent.putExtra("shoes_id", Integer.parseInt(shoes_id));
            intent.putExtra("shoes_img", imgUrl);
            startActivity(intent);
        });
        rgSizes1 = findViewById(R.id.rgSizes1);
        rgSizes2 = findViewById(R.id.rgSizes2);
        btnCheckout = findViewById(R.id.btnCheckout);
        cartRepository = new CartRepository(this);

        String shoeName = getIntent().getStringExtra("shoes_name");
        double shoePrice = getIntent().getDoubleExtra("shoes_price", 0);
        String shoeDescription = getIntent().getStringExtra("shoes_description");
        String shoeImg = getIntent().getStringExtra("shoes_img");
        int shoesId = getIntent().getIntExtra("SHOES_ID", 0);


        Intent intent = getIntent();
        if (intent != null) {
            txtShoeName.setText(intent.getStringExtra("SHOES_NAME"));
            txtPrice.setText("$"+String.valueOf(intent.getDoubleExtra("SHOES_PRICE", 0)));
            txtDescription.setText(intent.getStringExtra("SHOES_DESCRIPTION"));
            imgUrl = intent.getStringExtra("SHOES_IMG");
            Picasso.get().load(imgUrl).into(imgShoe);
            shoes_id = String.valueOf(intent.getIntExtra("SHOES_ID", 0));
        }

//        txtShoeName.setText(shoeName);
//        txtPrice.setText(String.format("%,.0f", shoePrice));
//        txtDescription.setText(shoeDescription);
//        Picasso.get().load(shoeImg).placeholder(R.drawable.ic_launcher_background).into(imgShoe);

        rgSizes1.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedRadioButton = findViewById(checkedId);
            if (selectedRadioButton != null) {
                selectedSize = Integer.parseInt(selectedRadioButton.getText().toString());
            }
        });

        rgSizes2.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedRadioButton = findViewById(checkedId);
            if (selectedRadioButton != null) {
                selectedSize = Integer.parseInt(selectedRadioButton.getText().toString());
            }
        });

        btnAddToCart.setOnClickListener(v -> {
            if (selectedSize == 0) {
                Toast.makeText(this, "Vui lòng chọn size", Toast.LENGTH_SHORT).show();
                return;
            }


            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            String accountJson = prefs.getString("USER_ACCOUNT", null);

            if (accountJson == null) {
                Toast.makeText(this, "Vui lòng đăng nhập trước", Toast.LENGTH_SHORT).show();
                return;
            }

            Gson gson = new Gson();
            Type accountType = new TypeToken<Account>() {}.getType();
            Account account = gson.fromJson(accountJson, accountType);
            Cart cart = new Cart();
            cart.setAccount_id(account.getAccount_id());
            cart.setShoes_id(shoesId);
            cart.setQuantity(1);
            cart.setSize(selectedSize);
            Cart cart_raw = cartRepository.checkCartExist(account.getAccount_id(), shoesId, selectedSize);
            if(cart_raw != null) {
                cartRepository.UpdateQuanntityCartExist(cart_raw.getCart_id());
            } else {
                cartRepository.addCartItem(cart);
            }
            Toast.makeText(this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
        });

        btnSizeGuide.setOnClickListener(v -> {
            Intent intent2 = new Intent(ViewShoesDetailActivity.this, SizeGuideActivity.class);
            startActivity(intent2);
        });

        btnCheckout.setOnClickListener(v -> {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            String accountJson = prefs.getString("USER_ACCOUNT", null);
            Gson gson = new Gson();
            Type accountType = new TypeToken<Account>() {}.getType();
            Account account = gson.fromJson(accountJson, accountType);
            Cart cart = new Cart();
            cart.setAccount_id(account.getAccount_id());
            cart.setShoes_id(shoesId);
            cart.setQuantity(1);
            cart.setSize(selectedSize);
            ArrayList<Cart> carts = new ArrayList<>();
            carts.add(cart);
            Intent intent3 = new Intent(ViewShoesDetailActivity.this, CheckOutActivity.class);
            intent3.putParcelableArrayListExtra("cart_list", carts);
            startActivity(intent3);
        });
    }
}


