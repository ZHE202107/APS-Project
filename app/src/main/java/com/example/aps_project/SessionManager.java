package com.example.aps_project;

import android.content.Context;
import android.content.SharedPreferences;

import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import io.reactivex.rxjava3.core.Single;

/**
 * Session Manager 從 SharedPreferences 儲存和獲取資料
 */
public class SessionManager {
    private static final String TOKEN = "token";      //存儲"token"    的KEY
    private static final String USER = "user_name";   //儲存"使用者名稱" 的KEY
    private Context context;
    private SharedPreferences prefs;

    public SessionManager(Context context) {
        this.context = context;
        // 只有自己(APS-Project APP)才可以讀寫
        prefs = this.context.getSharedPreferences(String.valueOf(R.string.app_name), Context.MODE_PRIVATE);
    }

    // 儲存token
    public void saveAuthToken(String token) {
        prefs.edit().putString(TOKEN, token).apply(); // 存入SharedPreferences
    }

    // 獲取token
    @Nullable
    public String fetchAuthToken() {
        return prefs.getString(TOKEN, "");
    }

    // 儲存使用者姓名
    public void saveUserName(String userName) {
        prefs.edit().putString(USER, userName).apply(); // 存入SharedPreferences
    }
    // 獲取使用者姓名
    public String fetchUserName() {
        return prefs.getString(USER, "");
    }
}
