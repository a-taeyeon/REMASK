package com.kbsc.remask;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder>{

    private ArrayList<MyOrder> list;

    public void setMyOrderList(ArrayList<MyOrder> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public MyOrderAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_myorder, parent, false);
        return new MyOrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyOrderAdapter.ViewHolder holder, int position) {
        holder.onBind(list.get(position));

        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();

            Intent intent = new Intent(context, MyOrderDetailActivity.class);
            intent.putExtra("orderId", list.get(position).getOrder_id());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView prdtImg;
        TextView orderId;
        TextView prdtName;
        TextView state;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            prdtImg= itemView.findViewById(R.id.ivMyOrder_prdtImg);
            orderId = itemView.findViewById(R.id.tvMyOrderDetail_orderId);
            prdtName = itemView.findViewById(R.id.tvMyOrderDetail_prdtName);
            state = itemView.findViewById(R.id.tvMyOrderDetail_state);
        }

        void onBind(MyOrder item){

            orderId.setText(item.getOrder_id());
            prdtName.setText(item.getPrdtName());
            state.setText(item.getState());
        }
    }
}

