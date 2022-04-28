package com.example.aps_project;

import android.content.Context;
import android.content.SharedPreferences;

import org.jetbrains.annotations.Nullable;

/**
 * Session Manager 從 SharedPreferences 儲存和獲取資料
 */
public class SessionManager {
    private static String TOKEN = "token";  //欄位
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
}
