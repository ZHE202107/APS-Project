package com.example.aps_project.presenter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.viewpager.widget.PagerAdapter;

import com.example.aps_project.ApiClient;
import com.example.aps_project.R;
import com.example.aps_project.SessionManager;
import com.example.aps_project.contract.LoginContract;
import com.example.aps_project.databinding.BaseTitleBarBinding;
import com.example.aps_project.service.ApiService;
import com.example.aps_project.service.LoginRequest;
import com.example.aps_project.service.LoginResponse;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter implements LoginContract.IPresenter {
    private LoginContract.IView view;
    private ApiService apiService;
    private SessionManager sessionManager;

    public LoginPresenter(LoginContract.IView view) {
        this.view = view;
        apiService = new ApiClient().getApiService();
        Log.e("www", "init LoginPresenter");
    }

    //帳號登入
    @Override
    public void login(String account, String password) {
        // 帳號、密碼是否輸入
        if (account.trim().equals("") || password.trim().equals("")) {
            Log.e("www", "account null| password null");
            Toast.makeText((Context)view, "帳號密碼不得為空", Toast.LENGTH_SHORT);
            return; //結束
        }
        // 向api發送login請求
        apiService.login(new LoginRequest(account, password))
            .enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.code() == 200 && response.body() != null) {
                        LoginResponse body = response.body();
                        if (body.getStatus() == 0) {    //登入成功
                            view.showLoginSuccess();
                            // ------- 儲存Token ---------
                            sessionManager = new SessionManager((Context)view);
                            sessionManager.saveAuthToken(body.getToken());
                            //-------- 取得登入人員資訊 --------
                            getUserInfo(body.getToken());
                        } else {
                            Log.e("www", "200 伺服器維修中(未知狀況)");
                        }
                    } else if (response.code() == 401) { //登入失敗
                        view.showLoginFailure();
                    } else {
                        Log.e("www", "伺服器維修中(未知狀況)");
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    if(t instanceof ConnectException) {
                        Log.e("www", "請連上網路!");
                    } else if(t instanceof SocketTimeoutException) {
                        Log.e("www", "連不上伺服器!!!");
                    } else {
                        Log.e("www", "Login Failure：" + t);
                    }
                }
            });
    }

    //取得登入人員資訊
    public void getUserInfo(String token) {
        apiService.fetchUserInfo(token)
            .enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if(response.code() == 200 && response.body() != null) {
                        Log.e("www", "response.body().getUserName()" + response.body().getUserName());
                        sessionManager.saveUserName(response.body().getUserName());
                        Log.e("www", "sessionManager：" + sessionManager.fetchUserName());
                    } else {
                        Log.e("www", "獲取登入人員資訊失敗!!!\n" + response.toString());
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Log.e("www", "獲取登入人員資訊失敗!!!\n" + t);
                }
            });
    }
}
