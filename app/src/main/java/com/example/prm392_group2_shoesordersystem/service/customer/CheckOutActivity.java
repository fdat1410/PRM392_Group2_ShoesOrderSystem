package com.example.prm392_group2_shoesordersystem.service.customer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.adapter.CheckOutAdapter;
import com.example.prm392_group2_shoesordersystem.entity.Account;
import com.example.prm392_group2_shoesordersystem.entity.Cart;
import com.example.prm392_group2_shoesordersystem.entity.Order;
import com.example.prm392_group2_shoesordersystem.entity.Order_detail;
import com.example.prm392_group2_shoesordersystem.entity.Shoes;
import com.example.prm392_group2_shoesordersystem.repository.OrderDetailRepository;
import com.example.prm392_group2_shoesordersystem.repository.OrderRepository;
import com.example.prm392_group2_shoesordersystem.repository.ShoesRepository;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class CheckOutActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView txtTotalAmount;
    private RadioGroup radioGroupPayment;
    private Button btnPlaceOrder;
    private List<Cart> cartList;
    private ShoesRepository shoesRepository;

    private OrderRepository orderRepository;

    private OrderDetailRepository orderdetailRepository;
    private double totalAmount = 0;

    private TextInputEditText edtName, edtPhone, edtAddress;

    private static final String VNP_PAY_URL = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    private static final String VNP_RETURN_URL = "http://localhost:8080/vnpay_jsp/vnpay_return.jsp";
    private static final String VNP_TMN_CODE = "75ISX9TK";
    private static final String SECRET_KEY = "X5XU4NC97SK1Q23OL75TL1O0SAALPAZ2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        recyclerView = findViewById(R.id.recyclerViewCheckout);
        txtTotalAmount = findViewById(R.id.txtTotalAmount);
        radioGroupPayment = findViewById(R.id.radioGroupPayment);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);

        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        edtAddress = findViewById(R.id.edtAddress);

        shoesRepository = new ShoesRepository(this);
        orderRepository = new OrderRepository(this);
        orderdetailRepository = new OrderDetailRepository(this);
        cartList = getIntent().getParcelableArrayListExtra("cart_list");
        if (cartList == null) cartList = new ArrayList<>();

        for (Cart cart : cartList) {
            Shoes shoe = shoesRepository.getShoeById(cart.getShoes_id());
            totalAmount += cart.getQuantity() * shoe.getPrice();
        }

        txtTotalAmount.setText(String.format("Total: %,d $", (int) totalAmount));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CheckOutAdapter(this, cartList));

        btnPlaceOrder.setOnClickListener(v -> {
            if (validateInputs()) {
                processPayment(totalAmount, cartList);
            }
        });
    }

    private boolean validateInputs() {
        String name = edtName.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            edtName.setError("Name cannot be empty");
            return false;
        }
        if (!name.matches("^[a-zA-Z\\s]{2,}$")) {
            edtName.setError("Name must contain only letters and at least 2 characters");
            return false;
        }

        if (TextUtils.isEmpty(phone)) {
            edtPhone.setError("Phone number cannot be empty");
            return false;
        }
        if (!phone.matches("^0\\d{9,10}$")) {
            edtPhone.setError("Invalid phone number (must start with 0 and be 10-11 digits)");
            return false;
        }

        if (TextUtils.isEmpty(address)) {
            edtAddress.setError("Address cannot be empty");
            return false;
        }
        if (address.length() < 5) {
            edtAddress.setError("Address must have at least 5 characters");
            return false;
        }
        return true;
    }

    private void processPayment(double totalAmount, List<Cart> cartList1) {
        int selectedId = radioGroupPayment.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedButton = findViewById(selectedId);
        if ("Credit Card".equals(selectedButton.getText().toString())) {
            processVNPAYPayment((int) totalAmount, cartList1);
        } else {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            String accountJson = prefs.getString("USER_ACCOUNT", null);
            Gson gson = new Gson();
            Account account = gson.fromJson(accountJson, Account.class);

            if (account == null) {
                Toast.makeText(this, "User account not found", Toast.LENGTH_SHORT).show();
                return;
            }

//            String name = edtName.getText().toString().trim();
//            String phone = edtPhone.getText().toString().trim();
            String address = edtAddress.getText().toString().trim();

            Order order = new Order();
            order.setOrder_date(String.valueOf(LocalDate.now()));
            order.setAddress(address);
            order.setAccount_id(account.getAccount_id());
            order.setOrd_status(0);
            order.setPayment_status(0);
            order.setTotalPrice(totalAmount);
            order.setNote("Ok");


            if (cartList1 == null || cartList1.isEmpty()) {
                Toast.makeText(this, "Cart is empty", Toast.LENGTH_SHORT).show();
                return;
            }


            new Thread(() -> {
                try {
                    Order orderRaw = orderRepository.createOrder(order);
                    if (orderRaw == null) {
                        runOnUiThread(() -> Toast.makeText(this, "Failed to create order", Toast.LENGTH_SHORT).show());
                        return;
                    }

                    for (Cart cart : cartList1) {
                        Order_detail od = new Order_detail();
                        od.setOrder_id(orderRaw.getOrder_id());
                        od.setQuantity(cart.getQuantity());
                        od.setShoes_id(cart.getShoes_id());
                        od.setSize(cart.getSize());

                        orderdetailRepository.createOrderDetai(od);
                    }

                    runOnUiThread(() -> {
                        Toast.makeText(this, "Check out Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CheckOutActivity.this, ManageCustomerDashboardActivity.class);
                        startActivity(intent);
                    });

                } catch (Exception e) {
                    runOnUiThread(() -> Toast.makeText(this, "Transaction Failed: " + e.getMessage(), Toast.LENGTH_LONG).show());
                }
            }).start();
        }
    }


    private void processVNPAYPayment(int amount, List<Cart> cartList1) {
        try {
            Map<String, String> params = new TreeMap<>();
            params.put("vnp_Version", "2.1.0");
            params.put("vnp_Command", "pay");
            params.put("vnp_TmnCode", VNP_TMN_CODE);
            params.put("vnp_Amount", String.valueOf(amount * 10000));
            params.put("vnp_CurrCode", "VND");
            params.put("vnp_TxnRef", getRandomNumber(8));
            params.put("vnp_OrderInfo", "Thanh toán đơn hàng");
            params.put("vnp_OrderType", "other");
            params.put("vnp_Locale", "vn");
            params.put("vnp_ReturnUrl", VNP_RETURN_URL);
            params.put("vnp_IpAddr", getLocalIpAddress(this));

            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
            formatter.setTimeZone(TimeZone.getTimeZone("GMT+7"));
            params.put("vnp_CreateDate", formatter.format(Calendar.getInstance().getTime()));

            // Tạo mã bảo mật (Secure Hash)
            String secureHash = hmacSHA512(SECRET_KEY, buildQuery(params));
            params.put("vnp_SecureHash", secureHash);

            // Xây dựng URL thanh toán
            String paymentUrl = VNP_PAY_URL + "?" + buildQuery(params);

            // Chuyển sang màn hình thanh toán với WebView
            Intent intent = new Intent(this, VnpayWebViewActivity.class);
            intent.putParcelableArrayListExtra("cart_list", new ArrayList<>(cartList1));
            intent.putExtra("totalAmount", amount);
            intent.putExtra("paymentUrl", paymentUrl);
            startActivity(intent);

        } catch (Exception e) {
            Toast.makeText(this, "Payment error: " + e.getMessage(), Toast.LENGTH_LONG).show();

        }
    }


    private String buildQuery(Map<String, String> params) {
        StringBuilder query = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            try {
                query.append(entry.getKey())
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), "UTF-8"))
                        .append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return query.substring(0, query.length() - 1);
    }

    private static String hmacSHA512(String key, String data) {
        try {
            Mac hmac512 = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA512");
            hmac512.init(secretKey);
            byte[] result = hmac512.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) sb.append(String.format("%02x", b & 0xff));
            return sb.toString();
        } catch (Exception ex) {
            return "";
        }
    }

    private static String getLocalIpAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        return (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "." + ((ip >> 24) & 0xFF);
    }

    private static String getRandomNumber(int len) {
        return String.valueOf(new Random().nextInt((int) Math.pow(10, len)));
    }
}
