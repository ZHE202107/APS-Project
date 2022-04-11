package com.example.aps_project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aps_project.databinding.FragmentScheduleTableBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScheduleTableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleTableFragment extends Fragment {
    private FragmentScheduleTableBinding binding;

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
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ScheduleTableAdapter mAdapter = new ScheduleTableAdapter();
        mAdapter.setOnItemClickListener((view, position) -> {
//            Toast.makeText(getContext(), "點擊了"+String.valueOf(position), Toast.LENGTH_SHORT).show(); //Debug用!!!
            getParentFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, new DetailsFragment())
                .addToBackStack("1")
                .commit();
        });
        binding.recyclerView.setAdapter(mAdapter);
    }

    /** -----------------------
     * ScheduleTableAdapter
     * ------------------------ */
    private static class ScheduleTableAdapter extends RecyclerView.Adapter<ScheduleTableAdapter.ViewHolder> {
        public OnItemClickListener mOnItemClickListener;

        // 設置click監聽接口
        public interface OnItemClickListener {
            void OnItemClick(View view, int position);
        }

        public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
            this.mOnItemClickListener = mOnItemClickListener;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView index;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                index = itemView.findViewById(R.id.indTextView);
            }
        }

        @NonNull
        @Override
        public ScheduleTableAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.schedule_table_rv_item_layout, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ScheduleTableAdapter.ViewHolder holder, int position) {
            holder.index.setText(String.valueOf(position+1));
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(View -> {
                    mOnItemClickListener.OnItemClick(holder.itemView, position+1);
                });
            }
        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }
}