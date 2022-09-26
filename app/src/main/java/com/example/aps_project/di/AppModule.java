package com.example.aps_project.di;

import android.app.Activity;

import com.example.aps_project.activity.LoginActivity;
import com.example.aps_project.contract.LoginContract;
import com.example.aps_project.presenter.LoginPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;

@InstallIn(ActivityComponent.class)
@Module
public abstract class AppModule {

    @Binds
    public abstract LoginContract.IView bindActivity(LoginActivity activity);

    @Binds
    public abstract LoginContract.IPresenter bindPresenter(LoginPresenter impl);
}

@InstallIn(ActivityComponent.class)
@Module
final class ActivityModule {
    @Provides
    public LoginActivity provideLoginActivity(Activity activity) {
        return (LoginActivity)activity;
    }
}


