package com.example.aps_project.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.aps_project.ApiClient;
import com.example.aps_project.SessionManager;
import com.example.aps_project.contract.LoginContract;
import com.example.aps_project.service.ApiService;
import com.example.aps_project.service.LoginRequest;
import com.example.aps_project.service.LoginResponse;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class LoginPresenter implements LoginContract.IPresenter {
    private static final String TAG = "LoginPresenter";
    private final LoginContract.IView view;
    @Inject
    public ApiService apiService;
    @Inject
    public SessionManager sessionManager;

    @Inject
    public LoginPresenter(LoginContract.IView view) {
        this.view = view;
//        apiService = new ApiClient().getApiService();
        Log.e(TAG, "init LoginPresenter");
    }

    //帳號登入
    @Override
    public void login(String account, String password) {
        // 帳號、密碼是否輸入
        if (account.trim().equals("") || password.trim().equals("")) {
            Log.e(TAG, "account null| password null");
            Toast.makeText((Context)view, "帳號密碼不得為空", Toast.LENGTH_SHORT).show();
            return; //結束
        }
        // 向api發送login請求
        apiService.login(new LoginRequest(account, password))
            .subscribeOn(Schedulers.io())  // 在IO線程進行網路請求
            .observeOn(AndroidSchedulers.mainThread()) // 回到主線程處理請求結果
            .subscribe(new DisposableSingleObserver<Response<LoginResponse>>() {
                @Override
                public void onSuccess(@NonNull Response<LoginResponse> response) {
                    if (response.code() == 200 && response.body() != null) {
                        LoginResponse body = response.body();
                        if (body.getStatus() == 0) {    //登入成功
                            view.showLoginSuccess();
                            // ------- 儲存Token ---------
//                            sessionManager = new SessionManager((Context)view);
                            sessionManager.saveAuthToken(body.getToken());
                            //-------- 取得登入人員資訊 --------
                            getUserInfo(body.getToken());
                        } else {
                            Log.e(TAG, "200 伺服器維修中(未知狀況)");
                        }
                    } else if (response.code() == 401) { //登入失敗
                        view.showLoginFailure();
                    } else {
                        Log.e(TAG, "伺服器維修中(未知狀況)");
                    }
                }

                @Override
                public void onError(@NonNull Throwable t) {
                    Log.e(TAG, t.getLocalizedMessage());
                    if(t instanceof ConnectException) {
                        Log.e(TAG, "請連上網路!" + t.getMessage());
                    } else if(t instanceof SocketTimeoutException) {
                        Log.e(TAG, "連不上伺服器!!!");
                    } else {
                        Log.e(TAG, "Login Failure：" + t);
                    }
                }
            });
    }

    //取得登入人員資訊
    public void getUserInfo(String token) {
        apiService.fetchUserInfo(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new DisposableSingleObserver<Response<LoginResponse>>() {
                @Override
                public void onSuccess(@NonNull Response<LoginResponse> response) {
                    if(response.code() == 200 && response.body() != null) {
                        Log.e(TAG, "response.body().getUserName()" + response.body().getUserName());
                        sessionManager.saveUserName(response.body().getUserName());
                        Log.e(TAG, "sessionManager：" + sessionManager.fetchUserName());
                    } else {
                        Log.e(TAG, "獲取登入人員資訊失敗!!!\n" + response.toString());
                    }
                }

                @Override
                public void onError(@NonNull Throwable t) {
                    Log.e(TAG, "獲取登入人員資訊失敗!!!\n" + t);
                }
            });
    }
}
