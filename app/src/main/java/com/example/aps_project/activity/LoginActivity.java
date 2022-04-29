package com.example.aps_project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.aps_project.contract.LoginContract;
import com.example.aps_project.databinding.ActivityLoginMainBinding;
import com.example.aps_project.presenter.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements LoginContract.IView {
    private ActivityLoginMainBinding binding;
    private static LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        presenter = new LoginPresenter(this);
    }

    // 登入按鈕, click如果登入成功則跳轉到主頁面(MainActivity)
    public void login(View v) {
        presenter.login(binding.accountEditText.getText().toString(),
            binding.passwordEditText.getText().toString());
    }

    // 登入成功
    @Override
    public void showLoginSuccess() {
        Toast.makeText(this, "成功登入", Toast.LENGTH_SHORT).show(); //提示成功登入
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
    }

    // 登入失敗
    @Override
    public void showLoginFailure() {
        Toast.makeText(this, "帳號或密碼錯誤", Toast.LENGTH_SHORT).show(); //提示登入失敗
        binding.passwordEditText.setText("");
    }
}