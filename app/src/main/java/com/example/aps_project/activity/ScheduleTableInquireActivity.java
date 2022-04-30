package com.example.aps_project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.aps_project.SessionManager;
import com.example.aps_project.databinding.ActivityScheduleTableInquireBinding;
import com.example.aps_project.databinding.FragmentAddScheduleBinding;

public class ScheduleTableInquireActivity extends AppCompatActivity {
    private ActivityScheduleTableInquireBinding mainBinding;
    private FragmentAddScheduleBinding fragmentBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityScheduleTableInquireBinding.inflate(getLayoutInflater());
        fragmentBinding = FragmentAddScheduleBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        mainBinding.setSession(new SessionManager(this));
        init(); //初始化
    }

    private void init() {
        mainBinding.titleBarBack.tvCurrentTitle.setText("進度表查詢");
        mainBinding.titleBarBack.backBtn.setOnClickListener((view) -> {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            } else {
                this.finish();  // 關閉當前(Activity)頁面
            }
        });
    }
}