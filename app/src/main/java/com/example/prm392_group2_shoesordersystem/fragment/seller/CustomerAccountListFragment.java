package com.example.prm392_group2_shoesordersystem.fragment.seller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.adapter.AccountAdapter;
import com.example.prm392_group2_shoesordersystem.entity.Account;
import com.example.prm392_group2_shoesordersystem.repository.AccountRepository;

import java.util.List;

public class CustomerAccountListFragment extends Fragment {
    private RecyclerView recyclerView;
    private AccountRepository accountRepository;
    Context context = getContext();
    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_account_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        accountRepository = new AccountRepository(getContext());
        List<Account> accounts =accountRepository.getAllCustomerAccounts();
        AccountAdapter accountAdapter = new AccountAdapter(requireContext(),accounts, accountRepository);
        recyclerView.setAdapter(accountAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        return view;
    }
}
