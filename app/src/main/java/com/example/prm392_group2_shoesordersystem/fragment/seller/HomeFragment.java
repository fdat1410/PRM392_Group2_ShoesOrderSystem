package com.example.prm392_group2_shoesordersystem.fragment.seller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.adapter.ShoesAdapter;
import com.example.prm392_group2_shoesordersystem.entity.Shoes;
import com.example.prm392_group2_shoesordersystem.repository.ShoesRepository;
import com.example.prm392_group2_shoesordersystem.service.seller.AddNewShoesActivity;

import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private Button btnAdd;
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
        btnAdd = view.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddNewShoesActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
