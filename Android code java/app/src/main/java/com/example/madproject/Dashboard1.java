package com.example.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Dashboard1 extends AppCompatActivity {

    TextView fullName, email, phone;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);  // You can remove this if it's not relevant to your project
        setContentView(R.layout.activity_dashboard1);

        // Set window insets for edge-to-edge layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        fullName = findViewById(R.id.ptofileName);
        phone = findViewById(R.id.ptofilePhone);
        email = findViewById(R.id.ptofileEmail);

        // Initialize Firebase Auth and Firestore
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userID = fAuth.getCurrentUser().getUid();

        // Retrieve user data from Firestore
        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null && value.exists()) {
                    phone.setText(value.getString("phone"));
                    fullName.setText(value.getString("fName"));
                    email.setText(value.getString("email"));
                } else if (error != null) {
                    // Handle the error
                    error.printStackTrace();
                }
            }
        });
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }

    public void About_Us(View view) {
//        startActivity(new Intent(getApplicationContext(), about_us.class));
        startActivity(new Intent(getApplicationContext(), ViewVehiclesActivity.class));
    }

    public void Add_Vehicle(View view) {
        startActivity(new Intent(getApplicationContext(), register_vehicle.class));
    }

    public void Book_Appointment(View view) {
        startActivity(new Intent(getApplicationContext(), book_appointment.class));
    }

    public void settings(View view) {
        startActivity(new Intent(getApplicationContext(), settings.class));
    }
}
