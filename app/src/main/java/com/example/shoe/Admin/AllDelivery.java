package com.example.shoe.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.shoe.AllOrders;
import com.example.shoe.Constants;
import com.example.shoe.Model.DeliveryItem;
import com.example.shoe.Model.OrderItem;
import com.example.shoe.Model.Vehicle;
import com.example.shoe.Prevalent.Prevalent;
import com.example.shoe.R;
import com.example.shoe.ViewHolder.AllOrderAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllDelivery extends AppCompatActivity {
    DatabaseReference reference;
    public static ArrayList<Vehicle> vehicesList;
    ArrayList<DeliveryItem> allDeliveries;
    RecyclerView deliveryRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_delivery);

        reference= FirebaseDatabase.getInstance().getReference(Constants.VEHICLEDATABASE);
        vehicesList=new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot post:snapshot.getChildren())
                {

                    Vehicle vehice = post.getValue(Vehicle.class);
                    vehicesList.add(vehice);
                }
                deliveryRecyclerView = findViewById(R.id.deliveryRecyclerView);

                // reference= FirebaseDatabase.getInstance().getReference(Constants.VEHICLEDATABASE);
                allDeliveries=new ArrayList<>();

                deliveryRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                DeliveryStatusAdapter adapter=new DeliveryStatusAdapter(AllDelivery.this,allDeliveries);
                deliveryRecyclerView.setAdapter(adapter);

                setAllOrders();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




}

    public void setAllOrders() {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("delivery");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allDeliveries=new ArrayList<>();
                for (DataSnapshot postshot : snapshot.getChildren()) {
                    DeliveryItem deliveryItem = postshot.getValue(DeliveryItem.class);

                    allDeliveries.add(deliveryItem);

                }
                deliveryRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                DeliveryStatusAdapter adapter=new DeliveryStatusAdapter(AllDelivery.this,allDeliveries);
                deliveryRecyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}