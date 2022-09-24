package com.example.aps_project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aps_project.databinding.ItemScheduleTableBinding;
import com.example.aps_project.service.MOResponse;

import java.util.List;

public class ScheduleTableAdapter extends RecyclerView.Adapter<ScheduleTableAdapter.ViewHolder> {
        public OnItemClickListener mOnItemClickListener;
        private final List<MOResponse> searchResultList;  //進度表搜尋結果

        public ScheduleTableAdapter(List<MOResponse> srList) {
            this.searchResultList = srList;
        }

        // 設置click監聽接口
        public interface OnItemClickListener {
            void OnItemClick(View view, int position);
        }

        public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
            this.mOnItemClickListener = mOnItemClickListener;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private ItemScheduleTableBinding mView;
            public ViewHolder(@NonNull ItemScheduleTableBinding itemView) {
                super(itemView.getRoot());
                this.mView = itemView;
            }
            public void bindView(int position) {
                mView.index.setText(String.valueOf(position+1));
                if (mOnItemClickListener != null) {
                    itemView.setOnClickListener(View -> {
                        mOnItemClickListener.OnItemClick(itemView, position);
                    });
                }

                MOResponse itemData = searchResultList.get(position);
                mView.moId.setText(itemData.getMoId()); //制令單號MO
                mView.soId.setText(itemData.getSoId()); //銷售單號SO

                int letterLimit = 15; //字數限制
                //母件代碼
                String itemId = itemData.getItemId();
                if(itemId.length() > letterLimit)
                    itemId = itemId.substring(0, letterLimit) + "...";
                mView.itemId.setText(itemId);
                //客戶名稱
                String customer = itemData.getCustomer();
                if(customer.length() > letterLimit)
                    customer = customer.substring(0, letterLimit) + "...";
                mView.customer.setText(customer);

                mView.qty.setText("數量：" + itemData.getQuantity()); //數量
                mView.deadline.setText("結關日：" + itemData.getDeadline());  //結關日期
                mView.onlineDate.setText("上線日：" + itemData.getOnlineDate()); //上線日期
                mView.techRoutingName.setText(itemData.getRelatedTechRoute().getTechRouteName());
            }
        }

        @NonNull
        @Override
        public ScheduleTableAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(ItemScheduleTableBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ScheduleTableAdapter.ViewHolder holder, int position) {
            holder.bindView(position);
        }

        @Override
        public int getItemCount() {
            return searchResultList.size();
        }
    }
