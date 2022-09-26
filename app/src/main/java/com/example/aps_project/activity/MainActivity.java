package com.example.aps_project.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.se.omapi.Session;
import android.util.Log;
import android.view.View;

import com.example.aps_project.R;
import com.example.aps_project.SessionManager;
import com.example.aps_project.adapter.ViewPagerAdapter;
import com.example.aps_project.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Inject
    public SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Log.v("ian-hilt", "MainActivity："+sessionManager.hashCode());
        binding.setSession(sessionManager);
        Log.e("www", "protected void onCreate(Bundle savedInstanceState) {：" + new SessionManager(this).fetchUserName());
        init(); //初始化
    }

    public void init() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        binding.viewPager2.setAdapter(viewPagerAdapter);

        List<String> title = Arrays.asList("生產排程","當日進度表","訊息通知");
        new TabLayoutMediator(binding.tabLayout, binding.viewPager2, true, false, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(title.get(position));
            }
        }).attach();
        binding.tabLayout.selectTab(binding.tabLayout.getTabAt(1));
    }

    // Click, 顯示TodayScheduleFragment 當日進度表區塊
    public void showTodaySchedule(View v) {
        //Todo
    }
    // Click, 顯示ProductionScheduleFragment 生產排程區塊
    public void showProductionSchedule(View v) {
        //Todo
    }
    // Click, 顯示MsgNotifyFragment 訊息通知區塊
    public void showMsgNotify(View v) {
        //Todo
    }
}