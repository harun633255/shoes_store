package com.example.shoe.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoe.ConfirmFinalOrderActivity;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.net.ssl.SSLEngineResult;

public class FinalDelivery extends AppCompatActivity {

    TextView deliverCodeTv, deliveryDateTv, deliveryHourTv, carCodeTv, orderCodeTv;

    Button deliveryNowBtn;
    String deliveryCode, deliveryDate, deliveryHour;
    public static String carCode, orderCode;

    public static OrderItem orderItem;
    public static Vehicle vehicle;
    ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_delivery);
        deliverCodeTv = findViewById(R.id.deliveryCodeTv);
        deliveryDateTv = findViewById(R.id.deliveryDateTv);
        deliveryHourTv = findViewById(R.id.deliveryHourTv);
        carCodeTv = findViewById(R.id.carCodeTv);
        orderCodeTv = findViewById(R.id.OrderCodeTv);
        deliveryNowBtn = findViewById(R.id.deliveryNowBtn);
        loadingBar = new ProgressDialog(this);

        //initialize time.........................
        Calendar calForDate;
        calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("EEE, d MMM yyyy");
        deliveryDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss Z");
        deliveryHour = currentTime.format(calForDate.getTime());
        //......................................
        int code = new Random().nextInt(10000) + new Random().nextInt(10000);
        deliveryCode = String.valueOf(code);

        deliverCodeTv.setText("Delivery Code:" + deliveryCode);
        deliveryDateTv.setText("Delivery Date:" + deliveryDate);
        deliveryHourTv.setText("Delivery Hour:" + deliveryHour);
        carCodeTv.setText("Car Code:" + vehicle.getCarcode());
        orderCodeTv.setText("Order Code:" + orderItem.getOrderCode());

        final DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference().child("Orders");
        final DatabaseReference deliveryRef = FirebaseDatabase.getInstance().getReference().child("delivery");
        final DatabaseReference deliveryDetailsRef = FirebaseDatabase.getInstance().getReference().child("deliverydetails");
        final DatabaseReference vehicleRef = FirebaseDatabase.getInstance().getReference().child("Vehicles");

        deliveryNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingBar.setTitle("Delivery processing");
                loadingBar.setMessage("Please wait, Delivery finishing..");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
                orderItem.setStatus(Constants.ORDERDEVILVERD);
                Map<String, Object> orderMap = new HashMap<>();
                orderMap.put("orderCode", orderItem.getOrderCode());
                orderMap.put("oderDate", orderItem.getOderDate());
                orderMap.put("clientCode", orderItem.getClientCode());
                orderMap.put("timeOfOrder", orderItem.getTimeOfOrder());
                orderMap.put("orderSum", orderItem.getOrderSum());
                orderMap.put("status", Constants.ORDERDEVILVERD);
                orderMap.put("paymentDetails", orderItem.getPaymentDetails());

                orderRef.child(orderCode)
                        .updateChildren(orderMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Map<String, Object> vehicleMap = new HashMap<>();

                                    String capacity = vehicle.getCapacity().trim();
                                    int c = Integer.parseInt(capacity) - 1;
                                    String loadCap;
                                    String status;
                                    if(c == 0)
                                    {
                                        loadCap = String.valueOf(c);
                                        status = Constants.UNAVAILABLE;
                                    }else
                                    {
                                        loadCap = String.valueOf(c);
                                        status = Constants.AVAILABLE;
                                    }


                                    vehicleMap.put("carcode", vehicle.getCarcode());
                                    vehicleMap.put("cartype", vehicle.getCartype());
                                    vehicleMap.put("capacity", loadCap);
                                    vehicleMap.put("status", status);
                                    vehicleMap.put("image", vehicle.getImage());
                                    vehicleRef.child(vehicle.getCarcode())
                                            .updateChildren(vehicleMap)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        DeliveryItem deliveryItem = new DeliveryItem(deliveryCode, deliveryDate, deliveryHour, vehicle.getCarcode(),Constants.INCOMPLETEDEVILVERD);
                                                        deliveryRef.child(deliveryCode).setValue(deliveryItem).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    DeliveryDetails deliveryDetails = new DeliveryDetails(deliveryCode, orderItem.getOrderCode());
                                                                    deliveryDetailsRef.child(deliveryCode).setValue(deliveryDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if (task.isSuccessful()) {
                                                                                loadingBar.dismiss();
                                                                                Toast.makeText(FinalDelivery.this, "Successfully deliveried", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        }
                                                                    });

                                                                }
                                                            }
                                                        });
                                                    }
                                                }
                                            });
                                }
                            }
                        });
            }
        });
    }
}