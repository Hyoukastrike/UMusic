package com.example.umusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.umusic.adapter.ViewPagerAdapter;
import com.example.umusic.effect.DepthPageTransformer;
import com.example.umusic.fragment.PlayFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

import music.AudioModel;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;
    private ViewPager2 mViewPager;
    ArrayList<AudioModel> songLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




//            Bundle bundle = new Bundle();
//            bundle.putParcelableArrayList("array",songLists);
//            PlayFragment playFragment = new PlayFragment();
//            playFragment.setArguments(bundle);





//        AHBottomNavigation bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
//
//// Create items
//        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.action_play, R.drawable.ic_play_black, R.color.red);
//        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.action_home, R.drawable.ic_headphone_black, R.color.blue);
//        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.action_personal, R.drawable.ic_person_black, R.color.yellow);
//
//// Add items
//        bottomNavigation.addItem(item1);
//        bottomNavigation.addItem(item2);
//        bottomNavigation.addItem(item3);


        mViewPager = findViewById(R.id.view_pager);
        bottomNav = findViewById(R.id.bottom_nav);
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        mViewPager.setAdapter(adapter);
        mViewPager.setPageTransformer(new DepthPageTransformer());




        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        bottomNav.getMenu().findItem(R.id.action_play).setChecked(true);
                        break;
                    case 1:
                        bottomNav.getMenu().findItem(R.id.action_home).setChecked(true);
                        break;
                    case 2:
                        bottomNav.getMenu().findItem(R.id.action_personal).setChecked(true);
                        break;
                }
            }
        });

        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId() == R.id.action_play){
                    mViewPager.setCurrentItem(0);
                }
                else if(item.getItemId() == R.id.action_home){
                    mViewPager.setCurrentItem(1);
                }
                else if (item.getItemId() == R.id.action_personal){
                    mViewPager.setCurrentItem(2);
                }

                return true;
            }
        });

        sendDatatoPlayFragment();
    }

    private void sendDatatoPlayFragment(){


    }
}