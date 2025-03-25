package com.example.prm392_group2_shoesordersystem.fragment.customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.adapter.ShoeCustomerPageAdapter;
import com.example.prm392_group2_shoesordersystem.adapter.ShoesAdapter;
import com.example.prm392_group2_shoesordersystem.entity.Shoes;
import com.example.prm392_group2_shoesordersystem.repository.ShoesRepository;

import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private ShoeCustomerPageAdapter shoesAdapter;
    private ShoesRepository shoesRepository;
    private List<Shoes> shoesList;
    public HomeFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        shoesRepository = new ShoesRepository(requireContext());
        shoesList = shoesRepository.getLatestShoes();

        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        shoesAdapter = new ShoeCustomerPageAdapter(shoesList, requireContext());
        recyclerView.setAdapter(shoesAdapter);
        return view;
    }
}