package com.example.aps_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aps_project.databinding.FragmentProductionScheduleBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductionScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductionScheduleFragment extends Fragment {

    private FragmentProductionScheduleBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductionScheduleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductionScheduleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductionScheduleFragment newInstance(String param1, String param2) {
        ProductionScheduleFragment fragment = new ProductionScheduleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductionScheduleBinding.inflate(inflater, container, false);
        init(); //初始化
        return binding.getRoot(); // Inflate the layout for this fragment
    }

    public void init() {
        // 當SettingBtn被按下,則跳轉到SettingActivity頁面
        Intent intent = new Intent();
        binding.settingBtn.setOnClickListener(View -> {
            intent.setClass(getActivity(), SettingActivity.class);
            startActivity(intent); //跳轉畫面
        });
        // 當scheduleTableBtn被按下，則跳轉到ScheduleTableInquireActivity(進度表查詢頁面)
        binding.scheduleTableBtn.setOnClickListener(View -> {
            intent.setClass(getActivity(), ScheduleTableInquireActivity.class);
            startActivity(intent); //跳轉畫面
        });
    }
}