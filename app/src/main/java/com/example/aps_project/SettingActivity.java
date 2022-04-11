package com.example.aps_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.aps_project.databinding.ActivitySettingBinding;

public class SettingActivity extends AppCompatActivity {
    private ActivitySettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        binding.titleBarBack.tvCurrentTitle.setText("設定");
        binding.titleBarBack.backBtn.setOnClickListener(View -> this.finish()); //backBtn被按下，則關閉當前頁面
    }
}