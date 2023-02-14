package com.example.shoe.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoe.Constants;
import com.example.shoe.Model.OrderItem;
import com.example.shoe.R;

import java.util.ArrayList;

public class AllOrderAdapter extends RecyclerView.Adapter<AllOrderAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<OrderItem> orderList;

    public AllOrderAdapter(Context context, ArrayList<OrderItem> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new AllOrderAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
           OrderItem orderItem=orderList.get(position);


            holder.orderCode.setText("OrderCode:"+orderItem.getOrderCode());
            holder.orderDate.setText("OrderDate:"+orderItem.getOderDate());
            holder.orderTime.setText("OrderTime:"+orderItem.getTimeOfOrder());
            holder.orderStatus.setText("Status:"+orderItem.getStatus());
            /*if(orderItem.getStatus().contains(Constants.ORDERDEVILVERD))
            {
                holder.orderAccept.setVisibility(View.VISIBLE);
            }else {

            }*/
        holder.orderAccept.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView orderCode,orderDate,orderTime,orderStatus;
        Button orderAccept;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
           orderCode = itemView.findViewById(R.id.orderCodeTv);
           orderDate = itemView.findViewById(R.id.orderDateTv);
           orderTime = itemView.findViewById(R.id.orderTimeTv);
           orderStatus = itemView.findViewById(R.id.orderStatusTv);
           orderAccept = itemView.findViewById(R.id.acceptDeliveryBtn);
        }


    }
}