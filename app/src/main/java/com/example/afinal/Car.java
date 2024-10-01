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

public class Car extends AppCompatActivity {
    LinearLayout carsPackagebtn;
    LinearLayout carsDestinationbtn;
    ImageButton destinationcartbtn;
    ImageButton destinationhomebtn;
    ImageButton destinationmuserbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_car);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        carsPackagebtn = findViewById(R.id.carsPackagebtn);
        carsDestinationbtn = findViewById(R.id.carsDestinationbtn);
        destinationcartbtn = findViewById(R.id.destinationcartbtn);
        destinationhomebtn = findViewById(R.id.destinationhomebtn);
        destinationmuserbtn = findViewById(R.id.destinationmuserbtn);
        Intent intent = getIntent();
        String email = intent.getStringExtra("userEmail");

        destinationcartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Car.this, Pending.class);
                intent.putExtra("userEmail",email);
                startActivity(intent);
            }
        });

        destinationmuserbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Car.this, userProfile.class);
                intent.putExtra("userEmail",email);
                startActivity(intent);
            }
        });
        destinationhomebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Car.this, Home.class);
                intent.putExtra("userEmail",email);
                startActivity(intent);
            }
        });
        carsPackagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Car.this, Package.class);
                intent.putExtra("userEmail",email);
                startActivity(intent);
            }
        });

        carsDestinationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Car.this, Destinations.class);
                intent.putExtra("userEmail",email);
                startActivity(intent);
            }
        });
    }
}