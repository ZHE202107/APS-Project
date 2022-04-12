package com.example.aps_project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.aps_project.databinding.ActivityTodayScheduleBinding;

public class TodayScheduleActivity extends AppCompatActivity {
    private ActivityTodayScheduleBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTodayScheduleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init(); //元件初始化
    }

    private void init() {
        binding.titleBarBack.tvCurrentTitle.setText("當日進度表");
        binding.titleBarBack.backBtn.setOnClickListener(View -> finish()); //關閉當前頁面
    }
}