package com.example.shoe.Admin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

public class DeliveryOrderAdapter extends RecyclerView.Adapter<DeliveryOrderAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<OrderItem> orderList;
    public static  boolean isDelivery = false;

    public DeliveryOrderAdapter(Context context, ArrayList<OrderItem> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_delivery, parent, false);
        return new DeliveryOrderAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
           final OrderItem orderItem=orderList.get(position);


            holder.orderCode.setText("OrderCode:"+orderItem.getOrderCode());
            holder.clientCode.setText("ClientCode:"+orderItem.getClientCode());
            holder.orderDate.setText("OrderDate:"+orderItem.getOderDate());
            holder.orderTime.setText("OrderTime:"+orderItem.getTimeOfOrder());
            holder.orderStatus.setText("Status:"+orderItem.getStatus());

            if(orderItem.getStatus().contains(Constants.ORDERDEVILVERD))
            {
                holder.orderDelivery.setVisibility(View.GONE);
            }
            if(orderItem.getStatus().contains(Constants.ORDERNOTDEVILVERD))
            {
                holder.orderDelivery.setVisibility(View.VISIBLE);
            }
            holder.orderDelivery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isDelivery = true;
                    FinalDelivery.orderCode = orderList.get(position).getOrderCode();
                    FinalDelivery.orderItem = orderList.get(position);
                    context.startActivity(new Intent(context,ViewVehicle.class));
                }
            });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView orderCode,clientCode,orderDate,orderTime,orderStatus;
        Button orderDelivery;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
           orderCode = itemView.findViewById(R.id.orderCodeTv);
           clientCode = itemView.findViewById(R.id.clientCodeTv);
           orderDate = itemView.findViewById(R.id.orderDateTv);
           orderTime = itemView.findViewById(R.id.orderTimeTv);
           orderStatus = itemView.findViewById(R.id.orderStatusTv);
            orderDelivery = itemView.findViewById(R.id.deliveryBtn);
        }


    }
}