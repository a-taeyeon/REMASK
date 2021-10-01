package com.kbsc.remask;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder>{

    private ArrayList<MyCart> list;
    private ArrayList<Integer> positionsList = new ArrayList<>();

    public void setMyCartList(ArrayList<MyCart> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public ArrayList<Integer> toArray(){
        return positionsList;
    }

    @NonNull
    @NotNull
    @Override
    public MyCartAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_mycart, parent, false);
        return new MyCartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyCartAdapter.ViewHolder holder, int position) {
        holder.onBind(list.get(position));

//        final MyCart item = list.get(position);
//        holder.cbSelect.setOnCheckedChangeListener(null);
//        holder.cbSelect.setChecked(item.getSelected());
//        holder.cbSelect.setOnCheckedChangeListener((buttonView, isChecked)
//                -> item.setSelected(isChecked)
//        );

//        holder.cbSelect.setOnClickListener(v -> { //CheckBox
//            if(!item.getSelected()) { //체크 되어있을 때 눌러서 선택해제
//                item.setSelected(false);
////                positionsList.remove(new Integer(position));
//            }
//            else { //체크박스 선택
//                item.setSelected(true);
//                positionsList.add(position);
//            }
//        });
    }

//    public void removeAt(int position) { //CheckBox
//        if(list.get(position).getSelected()){
//            list.remove(position);
//            notifyItemRemoved(position);
//            notifyItemRangeChanged(position, list.size());
//        }
//    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView prdtImg;
        TextView prdtName;
        TextView prdtAbbr;
        TextView price;
//        TextView quantity;
//        CheckBox cbSelect;
        ImageView star;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            prdtImg= itemView.findViewById(R.id.ivMyCart_prdtImg);
            prdtName = itemView.findViewById(R.id.tvMyCart_prdtName);
            prdtAbbr = itemView.findViewById(R.id.tvMyCart_prdtAbbr);
            price = itemView.findViewById(R.id.tvMyCart_price);
//            quantity = itemView.findViewById(R.id.tvMyCart_quantity);
//            cbSelect = itemView.findViewById(R.id.cbMyCart);
            star = itemView.findViewById(R.id.ivMyCart_star);
            star.setOnClickListener(v -> {
                list.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
                notifyItemRangeChanged(getAdapterPosition(), list.size());
            });

//            itemView.setOnClickListener(v -> {
//                int pos = getAdapterPosition();
//                if(pos != RecyclerView.NO_POSITION) {
//                    if (mListener != null) {
//                        mListener.onItemClick(v, pos);
//                    }
//                }
//            });
        }

        void onBind(MyCart item){
            prdtName.setText(item.getPrdtName());
            prdtAbbr.setText(item.getPrdtAbbr());
            price.setText(String.valueOf(item.getPrice()));
        }

    }
}
