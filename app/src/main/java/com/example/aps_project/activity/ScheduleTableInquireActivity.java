package com.example.aps_project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.se.omapi.Session;
import android.util.Log;

import com.example.aps_project.SessionManager;
import com.example.aps_project.databinding.ActivityScheduleTableInquireBinding;
import com.example.aps_project.databinding.FragmentAddScheduleBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ScheduleTableInquireActivity extends AppCompatActivity {
    private ActivityScheduleTableInquireBinding mainBinding;
    private FragmentAddScheduleBinding fragmentBinding;
    @Inject
    public SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityScheduleTableInquireBinding.inflate(getLayoutInflater());
        fragmentBinding = FragmentAddScheduleBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        Log.v("ian-hilt", "ScheduleTableInquireActivity："+sessionManager.hashCode());
        mainBinding.setSession(sessionManager);
        init(); //初始化
    }

    private void init() {
        mainBinding.titleBarBack.tvCurrentTitle.setText("進度表查詢");
        // 監聽按下返回鍵的事件, 當按下返回鍵彈出當前的Fragment, 但其條件是堆裡面有Fragment。
        mainBinding.titleBarBack.backBtn.setOnClickListener((view) -> {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            } else {
                this.finish();  // 關閉當前(Activity)頁面
            }
        });
    }
}