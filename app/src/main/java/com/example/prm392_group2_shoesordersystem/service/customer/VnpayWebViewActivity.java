package com.example.prm392_group2_shoesordersystem.service.customer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_group2_shoesordersystem.entity.Account;
import com.example.prm392_group2_shoesordersystem.entity.Cart;
import com.example.prm392_group2_shoesordersystem.entity.Order;
import com.example.prm392_group2_shoesordersystem.entity.Order_detail;
import com.example.prm392_group2_shoesordersystem.repository.OrderDetailRepository;
import com.example.prm392_group2_shoesordersystem.repository.OrderRepository;
import com.example.prm392_group2_shoesordersystem.repository.ShoesRepository;
import com.google.gson.Gson;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VnpayWebViewActivity extends AppCompatActivity {
    private WebView webView;
    private OrderRepository orderRepository;
    private OrderDetailRepository orderdetailRepository;
    private List<Cart> cartList;
    private ShoesRepository shoesRepository;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = new WebView(this);
        setContentView(webView);

        // Khởi tạo Repository
        shoesRepository = new ShoesRepository(this);
        orderRepository = new OrderRepository(this);
        orderdetailRepository = new OrderDetailRepository(this);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("http://localhost:8080/vnpay_jsp/vnpay_return.jsp")) {
                    Uri uri = Uri.parse(url);
                    String responseCode = uri.getQueryParameter("vnp_ResponseCode");
                    Log.d("VNPAY", "Response Code: " + responseCode);

                    if ("00".equals(responseCode)) { // ✅ Thanh toán thành công
                        handleSuccessfulPayment();
                        redirectToDashboard(); // Quay về ManageCustomerDashboardActivity
                    } else { // ❌ Hủy thanh toán hoặc thất bại
                        redirectToCheckoutActivity();
                    }
                    return true;
                }
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (url.contains("cancel") || url.contains("error")) { // Khi trang báo lỗi/hủy thanh toán
                    redirectToCheckoutActivity();
                }
            }
        });

        // Nhận URL thanh toán từ Intent và load WebView
        String paymentUrl = getIntent().getStringExtra("paymentUrl");
        webView.loadUrl(paymentUrl);
    }

    /**
     * Xử lý đơn hàng khi thanh toán thành công
     */
    private void handleSuccessfulPayment() {
        Intent intent = getIntent();
        int totalAmount = intent.getIntExtra("totalAmount", -1);

        // Lấy thông tin tài khoản từ SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String accountJson = prefs.getString("USER_ACCOUNT", null);
        Gson gson = new Gson();
        Account account = gson.fromJson(accountJson, Account.class);

        // Tạo đơn hàng
        Order order = new Order();
        order.setOrder_date(String.valueOf(LocalDate.now()));
        order.setAddress(account.getAddress());
        order.setAccount_id(account.getAccount_id());
        order.setOrd_status(0);
        order.setPayment_status(1); // Đánh dấu đã thanh toán
        order.setTotalPrice(totalAmount);
        order.setNote("Thanh toán thành công qua VNPAY");

        Order order_raw = orderRepository.createOrder(order);

        // Lấy danh sách giỏ hàng từ Intent
        cartList = getIntent().getParcelableArrayListExtra("cart_list");
        if (cartList == null) cartList = new ArrayList<>();

        // Tạo chi tiết đơn hàng
        for (Cart cart : cartList) {
            Order_detail od = new Order_detail();
            od.setOrder_id(order_raw.getOrder_id());
            od.setQuantity(cart.getQuantity());
            od.setShoes_id(cart.getShoes_id());
            od.setSize(cart.getSize());

            orderdetailRepository.createOrderDetai(od);
        }

        Toast.makeText(this, "Thanh toán thành công!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Chuyển về ManageCustomerDashboardActivity sau khi thanh toán thành công
     */
    private void redirectToDashboard() {
        Intent intent = new Intent(VnpayWebViewActivity.this, ManageCustomerDashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // Đóng WebViewActivity
    }

    /**
     * Quay lại CheckoutActivity khi thanh toán thất bại hoặc bị hủy
     */
    private void redirectToCheckoutActivity() {
        Intent intent = new Intent(VnpayWebViewActivity.this, CheckOutActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish(); // Đóng WebViewActivity
    }
}
