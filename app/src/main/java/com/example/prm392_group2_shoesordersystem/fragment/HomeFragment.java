package com.example.prm392_group2_shoesordersystem.fragment;

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
import com.example.prm392_group2_shoesordersystem.adapter.ShoesAdapter;
import com.example.prm392_group2_shoesordersystem.entity.Shoes;
import com.example.prm392_group2_shoesordersystem.repository.ShoesRepository;

import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ShoesRepository shoesRepository;;
    Context context = getContext();
    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        shoesRepository = new ShoesRepository(getContext());
        List<Shoes> shoes =shoesRepository.getAllShoes();
        ShoesAdapter shoesAdapter = new ShoesAdapter(requireContext(),shoes);
        recyclerView.setAdapter(shoesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        return view;
    }
}
