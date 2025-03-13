package com.example.prm392_group2_shoesordersystem.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.activity.CreateSellerAccountActivity;
import com.example.prm392_group2_shoesordersystem.adapter.AccountSellerAdapter;
import com.example.prm392_group2_shoesordersystem.entity.Account;
import com.example.prm392_group2_shoesordersystem.repository.AccountRepository;
import java.util.ArrayList;
import java.util.List;

public class ManagePageFragmentSellerManagement extends Fragment {
    private RecyclerView recyclerView;
    private AccountSellerAdapter adapter;
    private AccountRepository accountRepository;
    private Button btnCreateSeller, btnNext, btnPrevious;
    private List<Account> sellerList = new ArrayList<>();
    private int currentPage = 0;
    private static final int PAGE_SIZE = 2;
    private TextView txtPageNumber;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_manage_page_fragment_seller_management, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewSeller);
        btnCreateSeller = view.findViewById(R.id.btnCreateSeller);
        btnNext = view.findViewById(R.id.btnNext);
        btnPrevious = view.findViewById(R.id.btnPrevious);
        txtPageNumber = view.findViewById(R.id.txtPageNumber);

        accountRepository = new AccountRepository(requireContext());
        sellerList = accountRepository.ViewListAccountSeller();


        if (sellerList == null) {
            sellerList = new ArrayList<>();
        }

        adapter = new AccountSellerAdapter(getCurrentPageData(), requireContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        btnCreateSeller.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), CreateSellerAccountActivity.class);
            startActivity(intent);
        });

        btnNext.setOnClickListener(v -> {
            if ((currentPage + 1) * PAGE_SIZE < sellerList.size()) {
                currentPage++;
                updateRecyclerView();
            }
        });

        btnPrevious.setOnClickListener(v -> {
            if (currentPage > 0) {
                currentPage--;
                updateRecyclerView();
            }
        });

        updateButtonVisibility();
        return view;
    }

    private List<Account> getCurrentPageData() {
        int start = currentPage * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, sellerList.size());
        return sellerList.subList(start, end);
    }

    private void updateButtonVisibility() {
        btnPrevious.setEnabled(currentPage > 0);
        btnNext.setEnabled((currentPage + 1) * PAGE_SIZE < sellerList.size());
    }

    private void updateRecyclerView() {
        adapter.updateData(getCurrentPageData());
        updateButtonVisibility();
        txtPageNumber.setText(String.valueOf(currentPage + 1));
    }
}
