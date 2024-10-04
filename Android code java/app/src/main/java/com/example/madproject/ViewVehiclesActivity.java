package com.example.madproject;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewVehiclesActivity extends AppCompatActivity {

    RecyclerView vehicleRecyclerView;
    VehicleAdapter vehicleAdapter;
    List<Vehicle> vehicleList;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String userID;
    TextView noVehiclesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_vehicles);

        // Initialize views
        vehicleRecyclerView = findViewById(R.id.vehicleRecyclerView);
        noVehiclesText = findViewById(R.id.noVehiclesText);

        vehicleList = new ArrayList<>();
        vehicleAdapter = new VehicleAdapter(vehicleList);

        // Set up RecyclerView
        vehicleRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        vehicleRecyclerView.setAdapter(vehicleAdapter);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        // Fetch vehicles from Firestore
        fStore.collection("vehicles")
                .whereEqualTo("userID", userID)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Toast.makeText(ViewVehiclesActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (value != null && !value.isEmpty()) {
                            vehicleList.clear();
                            for (DocumentSnapshot doc : value.getDocuments()) {
                                Vehicle vehicle = doc.toObject(Vehicle.class);
                                vehicleList.add(vehicle);
                            }
                            vehicleAdapter.notifyDataSetChanged();
                            noVehiclesText.setVisibility(View.GONE); // Hide "no vehicles" message
                        } else {
                            noVehiclesText.setVisibility(View.VISIBLE); // Show "no vehicles" message
                        }
                    }
                });
    }
}
