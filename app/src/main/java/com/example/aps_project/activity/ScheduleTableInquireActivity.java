package com.example.aps_project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.aps_project.databinding.ActivityScheduleTableInquireBinding;

public class ScheduleTableInquireActivity extends AppCompatActivity {
    private ActivityScheduleTableInquireBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScheduleTableInquireBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init(); //初始化
    }

    private void init() {
        binding.titleBarBack.tvCurrentTitle.setText("進度表查詢");
        binding.titleBarBack.backBtn.setOnClickListener((view) -> {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            } else {
                this.finish();  // 關閉當前(Activity)頁面
            }
        });
    }
}