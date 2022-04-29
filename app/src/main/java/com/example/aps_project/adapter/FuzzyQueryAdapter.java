package com.example.aps_project.adapter;
/* -----------------------
  QueryResultAdapter
  模糊搜尋結果的Adapter
  ------------------------ */

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aps_project.R;
import com.example.aps_project.contract.ScheduleTableSearchContract;
import com.example.aps_project.databinding.FragmentAddScheduleBinding;
import com.example.aps_project.service.FuzzyQueryResponse;

import java.util.ArrayList;
import java.util.List;

public class FuzzyQueryAdapter extends RecyclerView.Adapter<FuzzyQueryAdapter.ViewHolder> {
    private List<FuzzyQueryResponse> resultList = new ArrayList<>(); //查詢結果清單
    private FragmentAddScheduleBinding binding;
    private ScheduleTableSearchContract.IVew view;
    private String key;

    public FuzzyQueryAdapter(ScheduleTableSearchContract.IVew view, FragmentAddScheduleBinding binding) {
        this.view = view;
        this.binding = binding;
    }

    //傳入要顯示的結果清單，和Response關鍵字欄位(用來指定哪一個資料)
    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<FuzzyQueryResponse> resultList, String key) {
        this.resultList = resultList;
        this.key = key;
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
        }
        void bind(int position){
            item.setText(resultList.get(position).getItemValue(key)); //根據指定key來顯示對應的value

//            當item被點擊
            item.setOnClickListener(View -> {
                switch (key) {
                    case FuzzyQueryResponse.CUSTOMER_NAME_KEY:
                        binding.customer.setText(resultList.get(position).getItemValue(key));
                        break;
                    case FuzzyQueryResponse.SO_ID_KEY:
                        binding.soId.setText(resultList.get(position).getItemValue(key));
                        break;
                }
                view.closeDialog();  //關閉互動視窗
            });
        }
    }

    @NonNull
    @Override
    public FuzzyQueryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
