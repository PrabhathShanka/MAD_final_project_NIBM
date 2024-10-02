package com.example.madproject;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class register_vehicle extends AppCompatActivity {

    // Declare UI elements
    EditText vehicleModel, licensePlate;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_vehicle);

        // Initialize views
        vehicleModel = findViewById(R.id.vehicleModel);
        licensePlate = findViewById(R.id.licensePlate);
        progressBar = findViewById(R.id.progressBar);

        // Initialize Firebase Auth and Firestore
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        // Get the current user's ID
        userID = fAuth.getCurrentUser().getUid();
    }

    // Method to handle the vehicle registration button click
    public void registerVehicle(View view) {
        // Show progress bar while saving
        progressBar.setVisibility(View.VISIBLE);

        // Get the vehicle details from the input fields
        String vehicleModelText = vehicleModel.getText().toString();
        String licensePlateText = licensePlate.getText().toString();

        // Ensure the input fields are not empty
        if (vehicleModelText.isEmpty() || licensePlateText.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }

        // Create a map to hold the vehicle data
        Map<String, Object> vehicle = new HashMap<>();
        vehicle.put("vehicleModel", vehicleModelText);
        vehicle.put("licensePlate", licensePlateText);
        vehicle.put("userID", userID);

        // Save the vehicle data in Firestore under a "vehicles" collection
        fStore.collection("vehicles").add(vehicle).addOnSuccessListener(documentReference -> {
            Toast.makeText(this, "Vehicle registered successfully!", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Failed to register vehicle. Try again.", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        });
    }

    // Method to go back to the previous activity
    public void Back(View view) {
        finish();  // Close the current activity and return to the previous one
    }
}
