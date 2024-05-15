package com.example.umusic;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.umusic.adapter.ViewPagerAdapter;
import com.example.umusic.adapter.ViewPagerAdapterx;
import com.example.umusic.effect.DepthPageTransformer;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivityx extends AppCompatActivity {

    public static final int MY_REQUEST_CODE = 10;

    private BottomNavigationView bottomNav;
    private ViewPager2 mViewPager;
    final private UserActivity mUserActivity = new UserActivity();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activityx);

        setTitleToolBar();

        mViewPager = findViewById(R.id.view_pager);
        bottomNav = findViewById(R.id.bottom_nav);
        ViewPagerAdapterx adapter = new ViewPagerAdapterx(this);
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
                        bottomNav.getMenu().findItem(R.id.action_personalx).setChecked(true);
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
                else if (item.getItemId() == R.id.action_personalx){
                    mViewPager.setCurrentItem(2);
                }

                return true;
            }
        });

    }

    private void setTitleToolBar(){
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("Main Activity");
        }
    }

}