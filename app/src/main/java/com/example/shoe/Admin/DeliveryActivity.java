package com.example.shoe.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.shoe.AllOrders;
import com.example.shoe.Model.OrderItem;
import com.example.shoe.Prevalent.Prevalent;
import com.example.shoe.R;
import com.example.shoe.ViewHolder.AllOrderAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeliveryActivity extends AppCompatActivity {
    ArrayList<OrderItem> allOrders;

    RecyclerView allOrderRecycler;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        allOrderRecycler = findViewById(R.id.deliveryRecyclerView);

        // reference= FirebaseDatabase.getInstance().getReference(Constants.VEHICLEDATABASE);
        allOrders=new ArrayList<>();

        allOrderRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        DeliveryOrderAdapter adapter=new DeliveryOrderAdapter(DeliveryActivity.this,allOrders);
        allOrderRecycler.setAdapter(adapter);

        setAllOrders();


    }

    public void setAllOrders() {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Orders");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allOrders=new ArrayList<>();
                for (DataSnapshot postshot : snapshot.getChildren()) {
                    OrderItem orderItem = postshot.getValue(OrderItem.class);

                    allOrders.add(orderItem);
                    allOrderRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    DeliveryOrderAdapter adapter=new DeliveryOrderAdapter(DeliveryActivity.this,allOrders);
                    allOrderRecycler.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}