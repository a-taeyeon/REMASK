package com.kbsc.remask;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MyMaskOrderListAdapter extends RecyclerView.Adapter<MyMaskOrderListAdapter.ViewHolder>{
    private ArrayList<MyMask> list;

    public void setMyMaskOrderList(ArrayList<MyMask> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public MyMaskOrderListAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_mymask_order_list, parent, false);
        return new MyMaskOrderListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyMaskOrderListAdapter.ViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvDate;
        TextView tvFabricCnt;
        TextView tvRingCnt;
        TextView tvWireCnt;
        TextView tvResult;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvMymaskOrderList_date);
            tvFabricCnt = itemView.findViewById(R.id.tvMyMaskOrderList_fabricCnt);
            tvRingCnt = itemView.findViewById(R.id.tvMyMaskOrderList_ringCnt);
            tvWireCnt = itemView.findViewById(R.id.tvMyMaskOrderList_wireCnt);
            tvResult= itemView.findViewById(R.id.tvMyMaskOrderList_result);
        }

        void onBind(MyMask item){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:MM:ss");
            tvDate.setText(simpleDateFormat.format(Long.valueOf(item.getDate())));

            tvFabricCnt.setText(String.valueOf(item.getFabricCnt()));
            tvRingCnt.setText(String.valueOf(item.getRingCnt()));
            tvWireCnt.setText(String.valueOf(item.getWireCnt()));

            tvResult.setText(item.getResult());
            if(item.getResult().equals("승인")){
                tvResult.setBackgroundResource(R.drawable.tvbg_mymask_order_list_approval);
            }

        }

    }

}
