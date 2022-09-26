package com.example.aps_project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.aps_project.SessionManager;
import com.example.aps_project.databinding.ActivitySettingBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SettingActivity extends AppCompatActivity {
    private ActivitySettingBinding binding;
    @Inject
    public SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Log.v("ian-hilt", "SettingActivity："+sessionManager.hashCode());
        binding.setSession(sessionManager);
        init();
    }

    private void init() {
        binding.titleBarBack.tvCurrentTitle.setText("設定");
        binding.titleBarBack.backBtn.setOnClickListener(View -> this.finish()); //backBtn被按下，則關閉當前頁面
    }
}