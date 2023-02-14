package com.example.shoe.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoe.Constants;
import com.example.shoe.R;
import com.example.shoe.loginActivity;
import com.example.shoe.registerActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class AddVehicle extends AppCompatActivity {

    ImageView carImage;
    TextView carCodeTv;
    EditText carTypeTv;
    EditText capacityTv;
    Button addCar;
    private static final int galleryPicture = 1;
    private Uri imageUri;
    private StorageReference vehicleImagesRef;
    ProgressDialog loadingBar;
    private String saveCurrentDate, saveCurrentTime;
    private String profileRandomKey, downloadImageUrl;

    String carCode;
    String carType;
    String capaCity;
    String status= Constants.AVAILABLE;

    boolean isCarSelect = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);
        //initialize all
        carImage = findViewById(R.id.carImageView);
        carCodeTv = findViewById(R.id.carCode);
        carTypeTv = findViewById(R.id.carType);
        capacityTv = findViewById(R.id.carCapacity);
        addCar = findViewById(R.id.addVehicle);
        loadingBar = new ProgressDialog(this);
        vehicleImagesRef = FirebaseStorage.getInstance().getReference().child("Vehicle Images");

        int code = new Random().nextInt(10000) + new Random().nextInt(10000);
        carCode = String.valueOf(code);
        carCodeTv.setText("Car code :"+carCode);


        carImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        addCar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                carType = carTypeTv.getText().toString();
                capaCity = capacityTv.getText().toString();
                if(!isCarSelect)
                {
                    Toast.makeText(AddVehicle.this, "Please select car Images", Toast.LENGTH_SHORT).show();
                }else if(carType.length() == 0)
                {
                    Toast.makeText(AddVehicle.this, "Please enter car type", Toast.LENGTH_SHORT).show();
                }else if(capaCity.length() == 0)
                {
                    Toast.makeText(AddVehicle.this, "Please enter car capacity", Toast.LENGTH_SHORT).show();
                }else
                {
                    storeProfilePicture(carCode,carType,capaCity);
                }

            }
        });



    }
    private void openGallery()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, galleryPicture);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == galleryPicture && resultCode == RESULT_OK && data != null)
        {
            imageUri = data.getData();
            carImage.setImageURI(imageUri);
            isCarSelect = true;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void storeProfilePicture(final String carCode, final String carType, final String capaCity)
    {

        loadingBar.setTitle("Adding New Vehicle");
        loadingBar.setMessage("Please wait, Vehicle is being added...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("EEE, d MMM yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss Z");
        saveCurrentTime = currentTime.format(calendar.getTime());

        profileRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = vehicleImagesRef.child(imageUri.getLastPathSegment() + profileRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(imageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(AddVehicle.this, "Error" + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(AddVehicle.this, "Product image uploaded Successfully...", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if (task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();

                            Toast.makeText(AddVehicle.this, "getting Vehicle image URL Successfully...", Toast.LENGTH_SHORT).show();

                            saveProfileInfoToDatabase(carCode,carType,capaCity,status,downloadImageUrl);
                        }
                    }
                });
            }
        });
    }
    private void saveProfileInfoToDatabase(final String carCode,final String carType,final String capaCity, final String status,String downloadImageUrl)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> vehicleDataMap = new HashMap<>();
        vehicleDataMap.put("image", downloadImageUrl);
        vehicleDataMap.put("carcode", carCode);
        vehicleDataMap.put("cartype", carType);
        vehicleDataMap.put("capacity", capaCity);
        vehicleDataMap.put("status", status);




        RootRef.child("Vehicles").child(carCode).updateChildren(vehicleDataMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(AddVehicle.this, "Vehicle Added Successfully...", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            int code = new Random().nextInt(10000) + new Random().nextInt(10000);
                            String carCode1 = String.valueOf(code);
                            carCodeTv.setText("Car code :"+carCode1);
                            carTypeTv.setText("");
                            capacityTv.setText("");

                           /* Intent intent = new Intent(AddVehicle.this, loginActivity.class);
                            startActivity(intent);*/
                        }
                        else
                        {
                            loadingBar.dismiss();
                            Toast.makeText(AddVehicle.this, "Network Error, Check Your Internet connection and Try again in a while...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}