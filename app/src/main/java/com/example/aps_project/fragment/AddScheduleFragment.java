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
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.aps_project.ApiClient;
import com.example.aps_project.R;
import com.example.aps_project.SessionManager;
import com.example.aps_project.databinding.FragmentAddScheduleBinding;
import com.example.aps_project.service.ApiService;
import com.example.aps_project.service.FuzzyQueryResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddScheduleFragment extends Fragment {

    private FragmentAddScheduleBinding binding;
    private List<FuzzyQueryResponse> resultList;    //模糊搜尋結果

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
        init(); //初始化
        return binding.getRoot();
    }

    private void init() {
        binding.okBtn.setOnClickListener(View -> {
            getParentFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, new ScheduleTableFragment())
                .addToBackStack("1")
                .commit();
        });

        // 點選relativeLayout外圍，可以取消顯示
        binding.body.setOnClickListener(View -> {
            closeDialog(); //關閉互動視窗
        });

        binding.calendarBtn.setOnClickListener(View -> {
            binding.relativeLayout.setVisibility(View.VISIBLE); // 可見
            binding.calendarView.setVisibility(View.VISIBLE);
        });

        binding.calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String date = String.format("%4d-%02d-%02d", i, i1, i2); // 轉換為規定的日期格式
                binding.onlineDate.setText(date);
                closeDialog();  //選擇完後關閉互動視窗
            }
        });

        // 顯示模糊查詢"訂單單號"
        binding.querrySaleOrderBtn.setOnClickListener(View -> {
            SessionManager sessionManager = new SessionManager(getContext());
            String token = sessionManager.fetchAuthToken();
            ApiService apiService = new ApiClient().getApiService();

            //呼叫API模糊查詢
            apiService.fuzzyQuerySaleOrder(token, binding.soId.getText().toString())
                .enqueue(new Callback<List<FuzzyQueryResponse>>() {
                    @Override
                    public void onResponse(Call<List<FuzzyQueryResponse>> call, Response<List<FuzzyQueryResponse>> response) {
                        Log.e("www", "binding.soId.toString()：" + binding.soId.getText());

                        if (response.code() == 200 && response.body() != null) {
                            List<FuzzyQueryResponse> resultList = response.body();
                            binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            QueryResultAdapter queryResultAdapter = new QueryResultAdapter(resultList, "so_id"); //傳入訂單單號List
                            binding.recyclerView.setAdapter(queryResultAdapter);

                            binding.relativeLayout.setVisibility(View.VISIBLE);
                            binding.recyclerOutView.setVisibility(View.VISIBLE);
                            binding.recyclerView.setVisibility(View.VISIBLE);
                        }else{
                            Log.e("www", "顯示模糊查詢訂單單號結果 獲取失敗!!!!");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<FuzzyQueryResponse>> call, Throwable t) {
                        Log.e("www", "模糊查詢訂單_抓取失敗!!!!!\n" + t);
                    }
                });
        });

        // 顯示模糊查詢"客戶名稱"
        binding.querryCustmoerNameBtn.setOnClickListener(View -> {
            SessionManager sessionManager = new SessionManager(getContext());
            String token = sessionManager.fetchAuthToken();
            ApiService apiService = new ApiClient().getApiService();

            //呼叫API
            apiService.fuzzyQueryCustomer(token, binding.customer.getText().toString())
                    .enqueue(new Callback<List<FuzzyQueryResponse>>() {
                        @Override
                        public void onResponse(Call<List<FuzzyQueryResponse>> call, Response<List<FuzzyQueryResponse>> response) {
                            if (response.code() == 200 && response.body() != null) {
                                resultList = response.body();   //儲存結果

                                binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                QueryResultAdapter queryResultAdapter = new QueryResultAdapter(resultList, "customer_name"); //傳入客戶List
                                binding.recyclerView.setAdapter(queryResultAdapter);

                                // 顯示查詢結果
                                binding.relativeLayout.setVisibility(View.VISIBLE);
                                binding.recyclerOutView.setVisibility(View.VISIBLE);
                                binding.recyclerView.setVisibility(View.VISIBLE);
                            }else{
                                Log.e("www", "顯示模糊查詢客戶名稱 獲取失敗!!!!");
                            }
                        }

                        @Override
                        public void onFailure(Call<List<FuzzyQueryResponse>> call, Throwable t) {
                            Log.e("www", "模糊查詢客戶名稱_抓取失敗!!!!!\n" + t);
                        }
                    });
        });
    }

    // 關閉互動視窗
    void closeDialog() {
        binding.relativeLayout.setVisibility(View.GONE);
        // 隱藏所有子元件
        for(int i=0; i<binding.relativeLayout.getChildCount(); i++)
            binding.relativeLayout.getChildAt(i).setVisibility(View.GONE);
    }

    /** -----------------------
     * QueryResultAdapter
     * ------------------------ */
    class QueryResultAdapter extends RecyclerView.Adapter<QueryResultAdapter.ViewHolder> {
        private List<FuzzyQueryResponse> resultList; //查詢結果清單
        private String key;

        public QueryResultAdapter(List<FuzzyQueryResponse> resultList, String key) {
            this.resultList = resultList;
            this.key = key;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView item;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                item = itemView.findViewById(R.id.item);
            }
            void bind(int position){
                item.setText(resultList.get(position).getItemValue(key)); //根據指定key來顯示對應的value
                item.setOnClickListener(View -> {
                    switch (key) {
                        case "customer_name":
                            binding.customer.setText(resultList.get(position).getItemValue(key));
                            break;
                        case "so_id":
                            binding.soId.setText(resultList.get(position).getItemValue(key));
                            break;
                    }
                    closeDialog();  //關閉互動視窗
                });
            }
        }

        @NonNull
        @Override
        public QueryResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_simple, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return resultList.size();
        }
    }
}