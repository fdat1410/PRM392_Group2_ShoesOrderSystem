package com.example.prm392_group2_shoesordersystem.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.prm392_group2_shoesordersystem.fragment.seller.CategoryFragment;
import com.example.prm392_group2_shoesordersystem.fragment.seller.CustomerAccountListFragment;
import com.example.prm392_group2_shoesordersystem.fragment.seller.HomeFragment;
import com.example.prm392_group2_shoesordersystem.fragment.seller.OrderListFragment;
import com.example.prm392_group2_shoesordersystem.fragment.seller.ProfileFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new CategoryFragment();
            case 2:
                return new CustomerAccountListFragment();
            case 3:
                return new OrderListFragment();
            case 4:
                return new ProfileFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;

    }

}
