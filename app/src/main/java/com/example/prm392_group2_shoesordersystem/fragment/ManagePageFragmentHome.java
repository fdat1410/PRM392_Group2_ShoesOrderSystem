package com.example.prm392_group2_shoesordersystem.fragment;

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
import com.example.prm392_group2_shoesordersystem.adapter.ManagePageAdapter;

import java.util.Arrays;
import java.util.List;

public class ManagePageFragmentHome extends Fragment {
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_manage_page_fragment_home, container, false);


        recyclerView = view.findViewById(R.id.recyclerView);


        List<String> items = Arrays.asList(
                "Sale Performance by Month",
                "Sale Performance by Category",
                "Top Customer",
                "Top Product"
        );

        List<Integer> icons = Arrays.asList(
                R.drawable.baseline_feed_24,
                R.drawable.baseline_checklist_24,
                R.drawable.baseline_account_box_24,
                R.drawable.baseline_add_shopping_cart_24
        );


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new ManagePageAdapter(getContext(), items, icons));

        return view;
    }
}
