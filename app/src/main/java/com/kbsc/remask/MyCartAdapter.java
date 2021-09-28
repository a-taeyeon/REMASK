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

    public void setMyCartList(ArrayList<MyCart> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onItemClick(View v, int position) ;
    }
    // 리스너 객체 참조를 저장하는 변수
    private OnItemClickListener mListener = null ;
    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }

    public void onDeleteClick(){
        int iMax = list.size() - 1;
        for(int i = iMax; i >= 0; i--){
            if(list.get(i).getSelected()){
                list.remove(i);
                notifyItemRemoved(i);
                notifyItemRangeChanged(i, list.size());
            }
//            else{
//                int length = list.get(i).get().size() - 1;
//                for (int j = length; j >= 0; j--) {
//                    if (list.get(i).getItems().get(j).ischeck()) {
//                        list.get(i).getItems().remove(j);
//                        notifyItemRemoved(i);
//                        notifyItemRangeChanged(i, list.size());
//                    }
//                }
//            }
        }
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

        final MyCart item = list.get(position);
        holder.cbSelect.setOnCheckedChangeListener(null);
        holder.cbSelect.setChecked(item.getSelected());
        holder.cbSelect.setOnCheckedChangeListener((buttonView, isChecked)
                -> item.setSelected(isChecked)
        );

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView prdtImg;
        TextView prdtName;
        TextView prdtAbbr;
        TextView price;
        TextView quantity;
        CheckBox cbSelect;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            prdtImg= itemView.findViewById(R.id.ivMyCart_prdtImg);
            prdtName = itemView.findViewById(R.id.tvMyCart_prdtName);
            prdtAbbr = itemView.findViewById(R.id.tvMyCart_prdtAbbr);
            price = itemView.findViewById(R.id.tvMyCart_price);
            quantity = itemView.findViewById(R.id.tvMyCart_quantity);
            cbSelect = itemView.findViewById(R.id.cbMyCart);

            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if(pos != RecyclerView.NO_POSITION) {
                    if (mListener != null) {
                        mListener.onItemClick(v, pos);
                    }
                }
            });
        }

        void onBind(MyCart item){
            prdtName.setText(item.getPrdtName());
            prdtAbbr.setText(item.getPrdtAbbr());
            price.setText(String.valueOf(item.getPrice()));
        }

    }
}
