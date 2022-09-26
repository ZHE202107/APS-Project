package com.example.aps_project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.aps_project.SessionManager;
import com.example.aps_project.databinding.ActivityTodayScheduleBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TodayScheduleActivity extends AppCompatActivity {
    private ActivityTodayScheduleBinding binding;
    @Inject
    public SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTodayScheduleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Log.v("ian-hilt", "TodayScheduleActivity："+sessionManager.hashCode());
        binding.setSession(sessionManager);
        init(); //元件初始化
    }

    private void init() {
        binding.titleBarBack.tvCurrentTitle.setText("當日進度表");
        binding.titleBarBack.backBtn.setOnClickListener(View -> finish()); //關閉當前頁面
    }
}