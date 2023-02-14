package com.example.shoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.shoe.Model.OrderItem;
import com.example.shoe.Prevalent.Prevalent;
import com.example.shoe.ViewHolder.AllOrderAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllOrders extends AppCompatActivity {

    ArrayList<OrderItem> allOrders;

    RecyclerView allOrderRecycler;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_orders);

        allOrderRecycler = findViewById(R.id.allOrderRecycler);

       // reference= FirebaseDatabase.getInstance().getReference(Constants.VEHICLEDATABASE);
        allOrders=new ArrayList<>();

        allOrderRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        AllOrderAdapter adapter=new AllOrderAdapter(AllOrders.this,allOrders);
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

                    if(orderItem.getClientCode().contains(Prevalent.currentOnlineUser.getUsername()))
                    {
                        allOrders.add(orderItem);
                        Log.d("DeliveryStatus",orderItem.getStatus());
                    }
                    allOrderRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    AllOrderAdapter adapter=new AllOrderAdapter(AllOrders.this,allOrders);
                    allOrderRecycler.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}