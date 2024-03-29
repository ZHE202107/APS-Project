package com.example.aps_project.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aps_project.R;
import com.example.aps_project.activity.TodayScheduleActivity;
import com.example.aps_project.adapter.TodayScheduleAdapter;
import com.example.aps_project.databinding.FragmentTodayScheduleBinding;

/**
 * 當日進度表Fragment |
 * A simple {@link Fragment} subclass.
 * Use the {@link TodayScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodayScheduleFragment extends Fragment {
    private FragmentTodayScheduleBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TodayScheduleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TodayScheduleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TodayScheduleFragment newInstance(String param1, String param2) {
        TodayScheduleFragment fragment = new TodayScheduleFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTodayScheduleBinding.inflate(inflater, container, false);
        init(); // 初始化
        return binding.getRoot();
    }


    private void init() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        TodayScheduleAdapter mAdapter = new TodayScheduleAdapter();
        mAdapter.setOnItemClickListener((view, position) ->  {
            // Toast.makeText(binding.getRoot().getContext(), "點擊了"+String.valueOf(position), Toast.LENGTH_SHORT).show(); //!!Debug用
            Intent intent = new Intent(getActivity(), TodayScheduleActivity.class);
            startActivity(intent);
        });
        binding.recyclerView.setAdapter(mAdapter);

    }
}