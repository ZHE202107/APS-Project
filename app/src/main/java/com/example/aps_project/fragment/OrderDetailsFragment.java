package com.example.aps_project.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aps_project.SessionManager;
import com.example.aps_project.adapter.OrderDetailsAdapter;
import com.example.aps_project.databinding.FragmentOrderDetailsBinding;
import com.example.aps_project.repository.ScheduleTableSearchRepository;
import com.example.aps_project.service.ApiService;
import com.example.aps_project.service.CurrStageMOResponse;
import com.example.aps_project.service.MOResponse;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

@AndroidEntryPoint
public class OrderDetailsFragment extends Fragment {
    @Inject
    public ApiService apiService;
    @Inject
    public SessionManager sessionManager;

    private FragmentOrderDetailsBinding binding;
    private int position = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOrderDetailsBinding.inflate(inflater, container, false);

        //當有初始資料才作動
        if(getArguments() != null) {
            //取得初始值
            position = getArguments().getInt("position");
            ScheduleTableSearchRepository repository = new ScheduleTableSearchRepository(getContext());
            MOResponse itemMO = repository.getItemSearchResult(position);
            Log.e("www", "[OrderDetailsFragment]初始資料 position：" + position);
            //設置DataBinding資料
            binding.setItemMO(itemMO);
            //初始化
            if(itemMO != null) {
                init(itemMO.getItemId());
            } else {
                Log.e("www", "[OrderDetailsFragment] itemMO製令為 null !!!!!");
            }
        }

        return binding.getRoot();
    }


    private void init(String itemId) {
//        apiService = new ApiClient().getApiService();
//        sessionManager = new SessionManager(getContext());

        //recyclerView設置
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //呼叫API獲取明細(本階製令)
        apiService.getCurrStageMO(sessionManager.fetchAuthToken(), itemId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new DisposableSingleObserver<Response<List<CurrStageMOResponse>>>() {
                @Override
                public void onSuccess(@NonNull Response<List<CurrStageMOResponse>> response) {
                    if(response.code() == 200 && response.body() != null) {
                        binding.recyclerView.setAdapter(new OrderDetailsAdapter(response.body()));
                    } else {
                        Log.e("www", "[OrderDetailsFragment-onSuccess]獲取明細失敗!!!\n" + response.toString());
                    }
                }

                @Override
                public void onError(@NonNull Throwable t) {
                    Log.e("www", "[OrderDetailsFragment-onError]獲取明細失敗!!!\n" + t);
                }
            });
    }
}