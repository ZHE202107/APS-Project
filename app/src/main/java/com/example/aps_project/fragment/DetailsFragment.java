package com.example.aps_project.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.example.aps_project.R;
import com.example.aps_project.databinding.FragmentDetailsBinding;
import com.example.aps_project.databinding.LayoutDetailsSwitchABinding;
import com.example.aps_project.databinding.LayoutDetailsSwitchBBinding;
import com.example.aps_project.repository.ScheduleTableSearchRepository;
import com.example.aps_project.service.MOResponse;
import com.google.android.material.internal.VisibilityAwareImageButton;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    private FragmentDetailsBinding binding;
    private List<RadioButton> radioButtons;
    private MOResponse itemMO; //準備給detailsLayoutSwitch 的DataBinding資料
    private int radioButtonsIndex;
    private int itemPosition = -1;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsFragment newInstance(String param1, String param2) {
        DetailsFragment fragment = new DetailsFragment();
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
        binding = FragmentDetailsBinding.inflate(inflater, container, false);

        //取得初始資料
        if(getArguments() != null) {
            itemPosition = getArguments().getInt("position");
            Log.e("www", "[DetailsFragment] 取得初始資料 position：" + itemPosition);

            ScheduleTableSearchRepository repository = new ScheduleTableSearchRepository(getContext());
            //根據上一個頁面傳進來的position，去向repository拿資料
            itemMO = repository.getItemSearchResult(itemPosition);
        } else {
            Log.e("www", "[DetailsFragment] 無法獲取初始資料!!!");
        }

        init(); //元件初始化
        return binding.getRoot();
    }

    private void init() {
        /** ---o-o--- 設定所有radioButton選擇更動的監聽事件 (radioButtons是全域變數) ---o-o--- */
        // radioButtons內的item必須按照UI上的介面順序
        radioButtons = Arrays.asList(binding.prevStageBtn, binding.currStageBtn, binding.nextStageBtn, binding.assemblyStageBtn, binding.salesRadioBtn);
        for (RadioButton radioButton: radioButtons) {
            radioButton.setOnCheckedChangeListener(this);
        }
        radioButtonsSelect(binding.currStageBtn);  //!!! 選擇初始化面預設按鈕 !!!! (本階製令)
        // 前一頁&下一頁按鈕的點擊監聽事件
        binding.previousPageBtn.setOnClickListener(this);
        binding.nextPageBtn.setOnClickListener(this);
        /** ---x-x-----------------------x-x */
    }

    private void radioButtonsSelect(RadioButton radioButton) {
        radioButtonsIndex = radioButtons.indexOf(radioButton);
        // 下一頁&前一頁，隨著index變動來決定是否為可見(visible)
        binding.previousPageBtn.setVisibility(VisibilityAwareImageButton.VISIBLE);
        binding.nextPageBtn.setVisibility(VisibilityAwareImageButton.VISIBLE);
        if (radioButtonsIndex == 0) //當前是第一頁，隱藏previousPageBtn的可見性
            binding.previousPageBtn.setVisibility(VisibilityAwareImageButton.INVISIBLE);
        else if (radioButtonsIndex == radioButtons.size() - 1) //當前是最後一頁，隱藏nextPageBtn的可見性
            binding.nextPageBtn.setVisibility(VisibilityAwareImageButton.INVISIBLE);
        radioButton.setChecked(true);  //更改選擇項

        //準備要傳送到底細項Fragment的初始資料
        Bundle bundle = new Bundle();
        bundle.putInt("position", itemPosition);
        //設置初始值
        OrderDetailsFragment orderDetailsFragment = new OrderDetailsFragment();
        orderDetailsFragment.setArguments(bundle);

        getChildFragmentManager().beginTransaction()
                .replace(R.id.fragment_order_details, orderDetailsFragment)
                .commit();
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (!b) return; //如果都沒有被選擇，則直接結束
        // 更改布局
        LayoutDetailsSwitchABinding aView = LayoutDetailsSwitchABinding.inflate(getLayoutInflater());
        LayoutDetailsSwitchBBinding bView = LayoutDetailsSwitchBBinding.inflate(getLayoutInflater());
        Log.e("www", "[DetailsFragment 的 aView] itemMO是否為空：" + (itemMO == null));
        aView.setItemMO(itemMO);

        binding.detailsLayoutSwitch.removeAllViews();
        if (compoundButton == binding.salesRadioBtn) {
            binding.detailsLayoutSwitch.addView(bView.getRoot());
        } else {
            binding.detailsLayoutSwitch.addView(aView.getRoot());
        }

        radioButtonsSelect((RadioButton)compoundButton);
    }

    @Override
    public void onClick(View view) {
        if (view == binding.previousPageBtn && radioButtonsIndex != 0) {
            radioButtonsSelect(radioButtons.get(radioButtonsIndex-1));
        } else if (view == binding.nextPageBtn && radioButtonsIndex != radioButtons.size()-1) {
            radioButtonsSelect(radioButtons.get(radioButtonsIndex+1));
        }
    }
}