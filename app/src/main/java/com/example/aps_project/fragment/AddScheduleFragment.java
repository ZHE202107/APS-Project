package com.example.aps_project.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.aps_project.R;
import com.example.aps_project.adapter.FuzzyQueryAdapter;
import com.example.aps_project.contract.ScheduleTableSearchContract;
import com.example.aps_project.databinding.FragmentAddScheduleBinding;
import com.example.aps_project.presenter.ScheduleTableSearchPresenter;
import com.example.aps_project.service.FuzzyQueryResponse;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddScheduleFragment extends Fragment implements ScheduleTableSearchContract.IVew {

    private FragmentAddScheduleBinding binding;
    private ScheduleTableSearchContract.IPresenter presenter;
    private FuzzyQueryAdapter fuzzyQueryAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddScheduleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddScheduleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddScheduleFragment newInstance(String param1, String param2) {
        AddScheduleFragment fragment = new AddScheduleFragment();
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
        binding = FragmentAddScheduleBinding.inflate(inflater, container, false);
        presenter = new ScheduleTableSearchPresenter(this);
        init(); //初始化
        return binding.getRoot();
    }

    private void init() {
        //設定recyclerView布局
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fuzzyQueryAdapter = new FuzzyQueryAdapter(this, binding);
        binding.recyclerView.setAdapter(fuzzyQueryAdapter);

        // "確定"按鈕點擊事件
        binding.okBtn.setOnClickListener(View -> {
            //根據此頁面輸入欄位 去呼叫API搜尋
            presenter.getSearcherResult(
                binding.onlineDate.getText().toString(),
                binding.soId.getText().toString(),
                binding.customer.getText().toString()
            );
        });

        // 點選relativeLayout外圍，可以取消顯示
        binding.body.setOnClickListener(View -> {
            closeDialog(); //關閉互動視窗
        });

        // 顯示日期選擇器
        binding.calendarBtn.setOnClickListener(View -> {
            binding.relativeLayout.setVisibility(View.VISIBLE); // 可見
            binding.calendarView.setVisibility(View.VISIBLE);
        });

        // 日期被選擇的事件處理
        binding.calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                @SuppressLint("DefaultLocale")
                String date = String.format("%4d-%02d-%02d", i, i1, i2); // 轉換為規定的日期格式
                binding.onlineDate.setText(date);
                closeDialog();  //選擇完後關閉互動視窗
            }
        });

        // 顯示模糊查詢"訂單單號"
        binding.querySaleOrderBtn.setOnClickListener(View -> {
            //傳入要搜尋的訂單號(可以是空字串)
            presenter.getQuerySaleOrder(binding.soId.getText().toString());
        });

        // 顯示模糊查詢"客戶名稱"
        binding.queryCustomerNameBtn.setOnClickListener(View -> {
            //傳入要搜尋的客戶名稱(可以是空字串)
            presenter.getQueryCustomerName(binding.customer.getText().toString());
        });

    }

    // 顯示模糊搜尋結果清單
    @Override
    public void showQueryResultList(List<FuzzyQueryResponse> resultList, String key) {
        fuzzyQueryAdapter.setData(resultList, key);
        showDialog();   //顯示查詢結果"視窗"
    }

    // 關閉互動視窗
    @Override
    public void closeDialog() {
        binding.relativeLayout.setVisibility(View.GONE);
        // 隱藏所有子元件
        for(int i=0; i<binding.relativeLayout.getChildCount(); i++)
            binding.relativeLayout.getChildAt(i).setVisibility(View.GONE);
    }

    // 顯示互動式窗
    public void showDialog() {
        binding.relativeLayout.setVisibility(View.VISIBLE);
        binding.recyclerOutView.setVisibility(View.VISIBLE);
        binding.recyclerView.setVisibility(View.VISIBLE);
    }

    // 跳轉到要顯示查詢結果的Fragment上
    @Override
    public void toScheduleTableFragment() {
        getParentFragmentManager().beginTransaction()
            .replace(R.id.fragmentContainerView, new ScheduleTableFragment())
            .addToBackStack("1")
            .commit();
    }

    // 提示查無結果
    @Override
    public void tipNoResult() {
        Toast.makeText(getContext(), "查無結果", Toast.LENGTH_SHORT).show();
    }
}