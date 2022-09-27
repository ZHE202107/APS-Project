package com.example.aps_project.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aps_project.R;
import com.example.aps_project.adapter.ScheduleTableAdapter;
import com.example.aps_project.databinding.FragmentScheduleTableBinding;
import com.example.aps_project.databinding.ItemScheduleTableBinding;
import com.example.aps_project.repository.ScheduleTableSearchRepository;
import com.example.aps_project.service.MOResponse;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScheduleTableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleTableFragment extends Fragment {
    private FragmentScheduleTableBinding binding;
    private ScheduleTableSearchRepository repository;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ScheduleTableFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScheduleTableFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScheduleTableFragment newInstance(String param1, String param2) {
        ScheduleTableFragment fragment = new ScheduleTableFragment();
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
        binding = FragmentScheduleTableBinding.inflate(inflater, container, false);
        init(); //初始化
        return binding.getRoot();
    }

    private void init() {
        repository = new ScheduleTableSearchRepository(getContext());

        List<MOResponse> searchResultList = repository.loadScheduleTable();  //載入搜尋結果
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ScheduleTableAdapter mAdapter = new ScheduleTableAdapter(searchResultList);



        //recyclerView 的item點擊事件，跳轉至另一個Fragment
        mAdapter.setOnItemClickListener((view, position) -> {
            //準備跳轉到該Fragment的'資料'
            Bundle bundle = new Bundle();
            Log.e("www", "跳轉前的position紀錄："+position);
            bundle.putInt("position", position);

            //設置要傳送的資料
            DetailsFragment df = new DetailsFragment();
            df.setArguments(bundle);

            //Toast.makeText(getContext(), "點擊了"+String.valueOf(position), Toast.LENGTH_SHORT).show(); //Debug用!!!
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, df)
                    .addToBackStack("1")
                    .commit();
        });

        binding.recyclerView.setAdapter(mAdapter);
        //顯示總比數
        binding.totalNum.setText("共" + searchResultList.size() + "筆");
    }
}