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

import com.example.aps_project.ApiClient;
import com.example.aps_project.R;
import com.example.aps_project.SessionManager;
import com.example.aps_project.databinding.FragmentOrderDetailsBinding;
import com.example.aps_project.databinding.ItemOrderDetailsBinding;
import com.example.aps_project.repository.ScheduleTableSearchRepository;
import com.example.aps_project.service.CurrStageMOResponse;
import com.example.aps_project.service.MOResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderDetailsFragment extends Fragment {
    private FragmentOrderDetailsBinding binding;
    private ApiClient apiClient;
    private SessionManager sessionManager;
    private int position = -1;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OrderDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderDetailsFragment newInstance(String param1, String param2) {
        OrderDetailsFragment fragment = new OrderDetailsFragment();
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
        // Inflate the layout for this fragment
        binding = FragmentOrderDetailsBinding.inflate(inflater, container, false);

        //當有初始資料才作動
        if(getArguments() != null) {
            //取得初始值
            position = getArguments().getInt("position");
            ScheduleTableSearchRepository repository = new ScheduleTableSearchRepository(this);
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
        apiClient = new ApiClient();
        sessionManager = new SessionManager(getContext());

        //recyclerView設置
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //呼叫API獲取明細(本階製令)
        apiClient.getApiService().getCurrStageMO(sessionManager.fetchAuthToken(), itemId)
            .enqueue(new Callback<List<CurrStageMOResponse>>() {
                @Override
                public void onResponse(Call<List<CurrStageMOResponse>> call, Response<List<CurrStageMOResponse>> response) {
                    if(response.code() == 200 && response.body() != null) {
                        binding.recyclerView.setAdapter(new OrderDetailsAdapter(response.body()));
                    } else {
                        Log.e("www", "[OrderDetailsFragment]獲取明細失敗!!!\n" + response.toString());
                    }
                }
                @Override
                public void onFailure(Call<List<CurrStageMOResponse>> call, Throwable t) {
                    Log.e("www", "[OrderDetailsFragment]獲取明細失敗!!!\n" + t);
                }
            });
    }


/**
 *  OrderDetailsAdapter
 * */
class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder> {
    private List<CurrStageMOResponse> dataList;

    public OrderDetailsAdapter(List<CurrStageMOResponse> dataList) {
        this.dataList = dataList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemOrderDetailsBinding mView;
        public ViewHolder(@NonNull ItemOrderDetailsBinding itemView) {
            super(itemView.getRoot());  //ViewBinding
            this.mView = itemView;
        }

        public void bindView(int position) {
            CurrStageMOResponse data = dataList.get(position);
            mView.index.setText(String.valueOf(position + 1)); //序號
            mView.materialId.setText(data.getParent().getMaterialId()); //材料編號
            mView.bomkeyName.setText(data.getParent().getBomkeyName()); //品名規格
            mView.unitQty.setText(data.getUnitQty() + ".00"); //單位用量
            mView.nuseQty.setText(data.getNuseQty() + ".00"); //需領用量
            mView.unitId.setText(data.getUnitId()); //單位
        }
    }

    @NonNull
    @Override
    public OrderDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemOrderDetailsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailsAdapter.ViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
}