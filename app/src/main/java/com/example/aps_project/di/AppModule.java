package com.example.aps_project.di;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.example.aps_project.activity.LoginActivity;
import com.example.aps_project.contract.LoginContract;
import com.example.aps_project.contract.ScheduleTableSearchContract;
import com.example.aps_project.fragment.AddScheduleFragment;
import com.example.aps_project.presenter.LoginPresenter;
import com.example.aps_project.presenter.ScheduleTableSearchPresenter;
import com.example.aps_project.repository.ScheduleTableSearchRepository;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.components.FragmentComponent;

@InstallIn(value = {ActivityComponent.class, FragmentComponent.class})
@Module
public abstract class AppModule {

    @Binds
    public abstract LoginContract.IView bindActivity(LoginActivity activity);
    @Binds
    public abstract LoginContract.IPresenter bindPresenter(LoginPresenter impl);

//    @Binds
//    public abstract ScheduleTableSearchContract.IVew bindView(AddScheduleFragment fragment);
    @Binds
    public abstract ScheduleTableSearchContract.IPresenter bindPresenterA(ScheduleTableSearchPresenter impl);
    @Binds
    public abstract ScheduleTableSearchContract.IRepository bindRepositoryA(ScheduleTableSearchRepository impl);
}

@InstallIn(ActivityComponent.class)
@Module
final class ActivityModule {
    @Provides
    public LoginActivity provideLoginActivity(Activity activity) {
        return (LoginActivity)activity;
    }
}

@InstallIn(FragmentComponent.class)
@Module
final class FragmentModule {
    @Provides
    public AddScheduleFragment provideAddScheduleFragment(Fragment fragment) {
        return (AddScheduleFragment)fragment;
    }
}


