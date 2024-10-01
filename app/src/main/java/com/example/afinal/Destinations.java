package com.example.afinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.afinal.Booking.Pending;

public class Destinations extends AppCompatActivity {
    LinearLayout destinationPackagebtn;
    LinearLayout destinationCarsbtn;
    ImageButton destinationcartbtn;
    ImageButton destinationhomebtn;
    ImageButton destinationuserbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_destinations);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        destinationPackagebtn = findViewById(R.id.packagesbtn3);
        destinationCarsbtn = findViewById(R.id.destinationCarsbtn);
        destinationcartbtn = findViewById(R.id.destinationcartbtn);
        destinationhomebtn = findViewById(R.id.destinationhomebtn);
        destinationuserbtn = findViewById(R.id.destinationuserbtn);
        Intent intent = getIntent();
        String email = intent.getStringExtra("userEmail");

        destinationhomebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Destinations.this, Home.class);
                intent.putExtra("userEmail",email);
                startActivity(intent);
            }
        });
        destinationcartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Destinations.this, Pending.class);
                intent.putExtra("userEmail",email);
                startActivity(intent);
            }
        });

        destinationuserbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Destinations.this, userProfile.class);
                intent.putExtra("userEmail",email);
                startActivity(intent);
            }
        });
        destinationPackagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Destinations.this, Package.class);
                intent.putExtra("userEmail",email);
                startActivity(intent);
            }
        });

        destinationCarsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Destinations.this, Car.class);
                intent.putExtra("userEmail",email);
                startActivity(intent);
            }
        });
    }
}