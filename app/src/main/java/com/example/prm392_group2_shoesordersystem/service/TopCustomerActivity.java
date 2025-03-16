package com.example.prm392_group2_shoesordersystem.service;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.entity.CustomerSale;
import com.example.prm392_group2_shoesordersystem.repository.AccountRepository;



import java.util.ArrayList;
import java.util.List;

public class TopCustomerActivity extends AppCompatActivity {
    private TableLayout tableLayout;

    private AccountRepository accountRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_customer);
        accountRepository = new AccountRepository(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        tableLayout = findViewById(R.id.tableLayout);


        List<CustomerSale> customerList = new ArrayList<>();
        customerList = accountRepository.ListCustomerSale();

//        customerList.add(new CustomerSale(1, 4000, 5, "Nam P", "nam.p2678@mail.com"));
//        customerList.add(new CustomerSale(2, 4000, 5, "Nam P", "nam.p2678@mail.com"));
//        customerList.add(new CustomerSale(3, 4000, 5, "Nam P", "nam.p2678@mail.com"));
//        customerList.add(new CustomerSale(4, 4000, 5, "Nam P", "nam.p2678@mail.com"));
//        customerList.add(new CustomerSale(5, 4000, 5, "Nam P", "nam.p2678@mail.com"));

        // Thêm dữ liệu vào bảng
        for (int i = 0; i < customerList.size(); i++) {
            CustomerSale customer = customerList.get(i);
            TableRow row = new TableRow(this);
            row.setPadding(8, 8, 8, 8);
            row.setGravity(Gravity.CENTER_VERTICAL);

            TextView txtIndex = createTextView(String.valueOf(i + 1));
            TextView txtEmail = createTextView(customer.getEmail());
            TextView txtFullName = createTextView(customer.getFullname());
            TextView txtItems = createTextView(String.valueOf(customer.getItem_bought()));
            TextView txtTotal = createTextView(String.valueOf(customer.getTotal_amount()));

            row.addView(txtIndex);
            row.addView(txtEmail);
            row.addView(txtFullName);
            row.addView(txtItems);
            row.addView(txtTotal);

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
}
