package com.example.prm392_group2_shoesordersystem.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new HomeFragment();
            case 1: return new CategoryFragment();
            case 2: return new CustomerAccountListFragment();
            case 3: return new OrderListFragment();
            default: return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4; // Số lượng Fragment
    }

}
