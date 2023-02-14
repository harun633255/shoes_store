package com.example.shoe.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.shoe.Constants;
import com.example.shoe.Model.Vehicle;
import com.example.shoe.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewVehicle extends AppCompatActivity {

    RecyclerView vehicleRecycler;
    DatabaseReference reference;
    ArrayList<Vehicle> vehicesList;
    TextView vehicleTextLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_vehicle);

        vehicleRecycler = findViewById(R.id.vehicleRecyclerView);
        vehicleTextLabel = findViewById(R.id.vehicleTextLabel);
        if(DeliveryOrderAdapter.isDelivery)
        {
            vehicleTextLabel.setText("All vehicle available for delivery");
        }

        reference= FirebaseDatabase.getInstance().getReference(Constants.VEHICLEDATABASE);
        vehicesList=new ArrayList<>();

        vehicleRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        VehicleAdapter adapter=new VehicleAdapter(ViewVehicle.this,vehicesList);
        vehicleRecycler.setAdapter(adapter);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot post:snapshot.getChildren())
                {

                    Vehicle vehice = post.getValue(Vehicle.class);
                    vehicesList.add(vehice);
                }
                vehicleRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                VehicleAdapter adapter=new VehicleAdapter(ViewVehicle.this,vehicesList);
                vehicleRecycler.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}