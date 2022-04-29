package com.example.aps_project.contract;

public class LoginContract {
    public interface IView{
        // 登入成功
        void showLoginSuccess();

        // 登入失敗
        void showLoginFailure();
    }

    public interface IPresenter {
        void login(String account, String password);
    }
}
