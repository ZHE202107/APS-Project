package com.example.aps_project.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aps_project.R;

public class TodayScheduleAdapter extends RecyclerView.Adapter<TodayScheduleAdapter.ViewHolder> {
    private OnItemClickListener mOnItemClickListener;

    //設置Callback接口
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvIndex;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIndex = itemView.findViewById(R.id.index);
        }
    }

    @NonNull
    @Override
    public TodayScheduleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_today_schedule, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodayScheduleAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvIndex.setText(String.valueOf(position+1));
        // 設置點擊事件
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(View -> {
                mOnItemClickListener.onItemClick(holder.itemView, position+1);
            });
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
