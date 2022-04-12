package com.example.aps_project.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.aps_project.fragment.MsgNotifyFragment;
import com.example.aps_project.fragment.ProductionScheduleFragment;
import com.example.aps_project.fragment.TodayScheduleFragment;

import java.util.Arrays;
import java.util.List;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private final List<Fragment> fragmentList = Arrays.asList(
        ProductionScheduleFragment.newInstance("",""),
        TodayScheduleFragment.newInstance("",""),
        MsgNotifyFragment.newInstance("","")
    );

    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }
}
