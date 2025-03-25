package com.example.prm392_group2_shoesordersystem.service.guest;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_group2_shoesordersystem.R;
import com.example.prm392_group2_shoesordersystem.adapter.BestSellerAdapter;
import com.example.prm392_group2_shoesordersystem.dao.BestSellerDAO;
import com.example.prm392_group2_shoesordersystem.repository.BestSellerRepository;

import java.util.List;

public class BestSellerActivity extends AppCompatActivity {

    private BestSellerRepository bestSellerRepository;
    private RecyclerView recyclerView;
    private BestSellerAdapter bestSellerAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_seller);

        recyclerView = findViewById(R.id.recyclerViewSeller);
        if (recyclerView == null) {
            Log.e("BestSellerActivity", "RecyclerView is null. Check your XML layout.");
            return;
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bestSellerRepository = new BestSellerRepository(getApplication());
        new GetDataTask().execute();
    }

    private class GetDataTask extends AsyncTask<Void, Void, List<BestSellerDAO.BestSellerShoes>> {
        @Override
        protected List<BestSellerDAO.BestSellerShoes> doInBackground(Void... voids) {
            return bestSellerRepository.getBestSellerShoes();
        }

        @Override
        protected void onPostExecute(List<BestSellerDAO.BestSellerShoes> bestSellerShoes) {
            bestSellerAdapter = new BestSellerAdapter(bestSellerShoes);
            recyclerView.setAdapter(bestSellerAdapter);
        }
    }
}