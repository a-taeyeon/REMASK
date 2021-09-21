package com.kbsc.remask;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class MyPointAdapter extends RecyclerView.Adapter<MyPointAdapter.ViewHolder>{
    private ArrayList<MyPoint> list;

    public void setPointList(ArrayList<MyPoint> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public MyPointAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_mypoint, parent, false);
        return new MyPointAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyPointAdapter.ViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView content;
        TextView date;
        TextView value;
        TextView tvP;
        TextView state;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            content = itemView.findViewById(R.id.tvMypoint_content);
            date = itemView.findViewById(R.id.tvMypoint_date);
            value = itemView.findViewById(R.id.tvMypoint_value);
            tvP = itemView.findViewById(R.id.tvMypoint_p);
            state = itemView.findViewById(R.id.tvMypoint_state);
        }

        void onBind(MyPoint item){
            content.setText(item.getContent());
            date.setText(item.getDate());
            value.setText(String.valueOf(item.getValue()));
            if(item.getValue() > 0){
                value.setTextColor(Color.parseColor("#0090FD"));
                tvP.setText("P");
                tvP.setTextColor(Color.parseColor("#0090FD"));
                state.setText("적립");
            } else{
                state.setText("차감");
            }
        }

    }


}
