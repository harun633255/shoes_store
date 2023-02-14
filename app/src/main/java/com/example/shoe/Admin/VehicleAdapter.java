package com.example.shoe.Admin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoe.Constants;
import com.example.shoe.Model.Vehicle;
import com.example.shoe.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Vehicle> vehicleList;

    public VehicleAdapter(Context context, ArrayList<Vehicle> vehicleList) {
        this.context = context;
        this.vehicleList = vehicleList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_vehicle, parent, false);
        return new VehicleAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            Vehicle vehice=vehicleList.get(position);


            holder.carCode.setText(vehice.getCarcode());
            holder.carType.setText(vehice.getCartype());
            holder.carCapacity.setText(vehice.getCapacity());
            holder.carStatus.setText(vehice.getStatus());
          Picasso.get().load(vehice.getImage()).into(holder.carImgae);
          holder.itemView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  if(DeliveryOrderAdapter.isDelivery)
                  {
                      FinalDelivery.vehicle = vehicleList.get(position);
                      String status = vehicleList.get(position).getStatus();

                      int c = Integer.valueOf(vehicleList.get(position).getCapacity());
                      if(c > 0)
                      {
                          context.startActivity(new Intent(context,FinalDelivery.class));
                      }else
                      {
                          Toast.makeText(context, "Car is unvailable", Toast.LENGTH_SHORT).show();
                      }

                  }
              }
          });


    }

    @Override
    public int getItemCount() {
        return vehicleList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView carCode,carType,carCapacity,carStatus;
        ImageView carImgae;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            carCode = itemView.findViewById(R.id.carCodeTv);
            carType = itemView.findViewById(R.id.carTypeTv);
            carCapacity = itemView.findViewById(R.id.carCapacityTv);
            carStatus = itemView.findViewById(R.id.carStatusTv);
            carImgae = itemView.findViewById(R.id.carImage);
        }


    }
}