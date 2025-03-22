package com.example.prm392_group2_shoesordersystem.service;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.adapter.ListShoesOrderAdapter;
import com.example.prm392_group2_shoesordersystem.entity.Account;
import com.example.prm392_group2_shoesordersystem.entity.ShoesOrderDetail;
import com.example.prm392_group2_shoesordersystem.repository.AccountRepository;
import com.example.prm392_group2_shoesordersystem.repository.OrderRepository;

import java.util.List;
import java.util.Map;

public class TrackOrderActivity extends AppCompatActivity {

    private TextView tvName, tvPhone, tvAddress, tvNote, tvPaymentStatus, tvOrderDate;
    private RecyclerView recyclerView;
    private TextView tvTotal;
    private AccountRepository accountRepository;
    private OrderRepository orderRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_track_order);

        // Ánh xạ các TextView từ layout
        tvName = findViewById(R.id.tvName);
        tvPhone = findViewById(R.id.tvPhone);
        tvAddress = findViewById(R.id.tvAddress);
        tvNote = findViewById(R.id.tvNote);
        tvPaymentStatus = findViewById(R.id.tvPaymentStatus);
        tvOrderDate = findViewById(R.id.tvOrderDate);
        recyclerView = findViewById(R.id.recyclerView);
        tvTotal = findViewById(R.id.tvTotal);

        // Lấy dữ liệu từ Intent
        int orderId = getIntent().getIntExtra("order_id", -1);  // -1 là giá trị mặc định nếu không có dữ liệu
        String address = getIntent().getStringExtra("address");
        String note = getIntent().getStringExtra("note");
        int paymentStatus = getIntent().getIntExtra("payment_status", -1);
        String orderDate = getIntent().getStringExtra("order_date");
        double totalPrice = getIntent().getDoubleExtra("total_price", 0.0);
        int accountId = getIntent().getIntExtra("account_id", -1);

        // Hiển thị dữ liệu đơn hàng
        tvAddress.setText(address);
        tvNote.setText(note);
        tvPaymentStatus.setText(paymentStatus == 0 ? "Cash" : "Transfer");
        tvOrderDate.setText(orderDate);
        tvTotal.setText(String.format("%.2f VNĐ", totalPrice));

        // Khởi tạo AccountRepository
        accountRepository = new AccountRepository(getApplication());
        orderRepository = new OrderRepository(this);

        // Gọi getAccountById() để lấy thông tin khách hàng
        if (accountId != -1) {
            loadCustomerInfo(accountId);
        } else {
            tvName.setText("Unknown");
            tvPhone.setText("N/A");
        }

        // Load danh sách sản phẩm trong đơn hàng
        if (orderId != -1) {
            loadShoesOrderList(orderId);
        }
    }

    private void loadCustomerInfo(int accountId) {
        accountRepository.getAccountById(accountId, new AccountRepository.OnAccountFetchListener() {
            @Override
            public void onAccountFetched(Account account) {
                runOnUiThread(() -> {
                    if (account != null) {
                        tvName.setText(account.getFullname());
                        tvPhone.setText(account.getPhone());
                    } else {
                        tvName.setText("Unknown");
                        tvPhone.setText("N/A");
                    }
                });
            }
        });
    }

    private void loadShoesOrderList(int orderId) {
        List<ShoesOrderDetail> shoesOrderList = orderRepository.getShoesOrderDetails(orderId);

        ListShoesOrderAdapter adapter = new ListShoesOrderAdapter(shoesOrderList, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}