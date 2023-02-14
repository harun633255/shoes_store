package com.example.shoe.Admin;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoe.Constants;
import com.example.shoe.Model.DeliveryDetails;
import com.example.shoe.Model.DeliveryItem;
import com.example.shoe.Model.OrderItem;
import com.example.shoe.Model.Vehicle;
import com.example.shoe.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DeliveryStatusAdapter extends RecyclerView.Adapter<DeliveryStatusAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<DeliveryItem> deliveryItems;
    public static  boolean isDelivery = false;
    ProgressDialog loadingBar;

    public DeliveryStatusAdapter(Context context, ArrayList<DeliveryItem> deliveryItems) {
        this.context = context;
        this.deliveryItems = deliveryItems;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_delivery_status, parent, false);
        return new DeliveryStatusAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
           final DeliveryItem deliveryItem=deliveryItems.get(position);


            holder.delieryCode.setText("Delivery Code: "+deliveryItem.getDeliveryCode());
            holder.deliveryDate.setText("Delivery Date: "+deliveryItem.getDeliveryDate());
            holder.deliveryHour.setText("Delivery Hour: "+deliveryItem.getDeliveryHour());
            holder.deliveryCarCode.setText("Car Code: "+deliveryItem.getCarCode());


          /*  if()

            Log.d("jdlkfjdlkjflkd",AllDelivery.vehicesList.get(position).getCarcode()+" : "+deliveryItem.getCarCode());
            Log.d("jdlkfjdlkjflkd",AllDelivery.vehicesList.get(position).getStatus()+" : "+Constants.UNAVAILABLE);*/

          /* for(Vehicle vehicle : AllDelivery.vehicesList)
           {
               if(vehicle.getCarcode().contains(deliveryItem.getCarCode()))
               {
                   int c = Integer.parseInt(vehicle.getCapacity());
                   String loadCap;
                   String status;

                   loadCap = String.valueOf(c);
                   if(vehicle.getStatus().contains(Constants.UNAVAILABLE))
                   {
                       holder.completeDelivery.setVisibility(View.VISIBLE);
                   }else {
                       holder.completeDelivery.setVisibility(View.GONE);
                   }
               }
           }*/
        if(deliveryItem.getStatus().contains(Constants.INCOMPLETEDEVILVERD))
        {
            holder.completeDelivery.setVisibility(View.VISIBLE);
        }else {
            holder.completeDelivery.setVisibility(View.GONE);
        }

        holder.completeDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Order Finished and Vehicle available", Toast.LENGTH_SHORT).show();
                loadingBar = new ProgressDialog(context);
                loadingBar.setTitle("Delivery finishing...");
                loadingBar.setMessage("Please wait,car will avialable");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
                final DatabaseReference vehicleRef = FirebaseDatabase.getInstance().getReference().child("Vehicles");
                final DatabaseReference deliveryRef = FirebaseDatabase.getInstance().getReference().child("delivery");

                Vehicle vehicle=new Vehicle();
                for(Vehicle vehicle1 : AllDelivery.vehicesList)
                {
                    if(vehicle1.getCarcode().contains(deliveryItems.get(position).getCarCode()))
                    {
                        vehicle = vehicle1;
                    }
                }

                Map<String, Object> vehicleMap = new HashMap<>();

                String capacity = vehicle.getCapacity().trim();
                int c = Integer.parseInt(capacity) + 1;
                String loadCap;
                String status;

                loadCap = String.valueOf(c);
                status = Constants.AVAILABLE;

                vehicleMap.put("carcode", vehicle.getCarcode());
                vehicleMap.put("cartype", vehicle.getCartype());
                vehicleMap.put("capacity",loadCap);
                vehicleMap.put("status",status);
                vehicleMap.put("image", vehicle.getImage());
                vehicleRef.child(vehicle.getCarcode())
                        .updateChildren(vehicleMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Map<String, Object> deliveryMap = new HashMap<>();
                                    deliveryMap.put("deliveryCode", deliveryItem.getDeliveryCode());
                                    deliveryMap.put("deliveryDate", deliveryItem.getDeliveryDate());
                                    deliveryMap.put("deliveryHour",deliveryItem.getDeliveryHour());
                                    deliveryMap.put("carCode",deliveryItem.getCarCode());
                                    deliveryMap.put("status", Constants.COMPLETEDEVILVERD);

                                    deliveryRef.child(deliveryItem.getDeliveryCode())
                                            .updateChildren(deliveryMap)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        loadingBar.dismiss();
                                                        Toast.makeText(context, "Order Finished and Vehicle available", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });


                                }
                            }
                        });
            }
        });

    }

    @Override
    public int getItemCount() {
        return deliveryItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView delieryCode,deliveryDate,deliveryHour,deliveryCarCode;
        Button completeDelivery;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            delieryCode = itemView.findViewById(R.id.deliveryCodeTv);
            deliveryDate = itemView.findViewById(R.id.deliveryDateTv);
            deliveryHour = itemView.findViewById(R.id.deliveryHourTv);
            deliveryCarCode = itemView.findViewById(R.id.deliveryCarCodeTv);
            completeDelivery = itemView.findViewById(R.id.completeDeliveryBtn);
        }


    }
}