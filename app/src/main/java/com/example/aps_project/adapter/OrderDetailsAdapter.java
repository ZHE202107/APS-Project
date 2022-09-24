package com.example.aps_project.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aps_project.databinding.ItemOrderDetailsBinding;
import com.example.aps_project.service.CurrStageMOResponse;

import java.util.List;

/**
 *  OrderDetailsAdapter
 * */
public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder> {
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
        return new OrderDetailsAdapter.ViewHolder(ItemOrderDetailsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
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