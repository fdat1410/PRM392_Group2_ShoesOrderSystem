package com.example.prm392_group2_shoesordersystem.service;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.repository.OrderRepository;

public class DeleteOrderActivity extends AppCompatActivity {

    private int orderId;
    private TextView tvOrderCode, tvOrderDate, tvOrderStatus;
    private EditText edtComment;
    private Button btnSend;
    private OrderRepository orderRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delete_order);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Ánh xạ view từ layout
        tvOrderCode = findViewById(R.id.tvOrderCode);
        tvOrderDate = findViewById(R.id.tvOrderDate);
        tvOrderStatus = findViewById(R.id.tvOrderStatus);
        edtComment = findViewById(R.id.edtComment);
        btnSend = findViewById(R.id.btnSend);

        // Lấy dữ liệu từ Intent
        orderId = getIntent().getIntExtra("order_id", -1);
        int orderStatus = getIntent().getIntExtra("order_status", -1);
        String orderDate = getIntent().getStringExtra("order_date");

        // Hiển thị thông tin đơn hàng
        tvOrderCode.setText(String.valueOf(orderId));
        tvOrderStatus.setText((orderStatus == 0 ? "Pending" : "Completed"));
        tvOrderDate.setText(orderDate);

        // Khởi tạo repository
        orderRepository = new OrderRepository(this);

        // Xử lý khi nhấn nút "Gửi yêu cầu xóa"
        btnSend.setOnClickListener(v -> sendDeleteRequest());
    }

    private void sendDeleteRequest() {
        if (orderId == -1) {
            Toast.makeText(this, "Error: Order not found", Toast.LENGTH_SHORT).show();
            return;
        }

        orderRepository.deleteOrderById(orderId);
        Toast.makeText(this, "Order deleted successfully", Toast.LENGTH_SHORT).show();

        // Chuyển về ViewOrderCustomerListActivity
        Intent intent = new Intent(DeleteOrderActivity.this, ViewListCustomerOrderActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}