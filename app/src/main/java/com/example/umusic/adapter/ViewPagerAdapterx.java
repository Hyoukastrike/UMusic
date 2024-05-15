package com.example.umusic.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.umusic.fragment.HomeFragment;
import com.example.umusic.fragment.PersonalFragmentx;
import com.example.umusic.fragment.PlayFragment;

public class ViewPagerAdapterx extends FragmentStateAdapter {

    public ViewPagerAdapterx(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new PlayFragment();
            case 1:
                return new HomeFragment();
            case 2:
                return new PersonalFragmentx();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
