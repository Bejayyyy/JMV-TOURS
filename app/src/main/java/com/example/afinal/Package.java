package com.example.afinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.afinal.Booking.Pending;

public class Package extends AppCompatActivity {
    ImageView package_profile_icon;
    LinearLayout packageCarsbtn;
    LinearLayout packageDestinationBtn;

    LinearLayout packageTourLayout;
    LinearLayout cebuTourLayout;
    LinearLayout oslobpackageTourLayout;
    LinearLayout safariTourLayout;
    LinearLayout simalaTourLayout;
    ImageButton packagecartbtn;
    ImageButton packagehomebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_package);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        package_profile_icon = findViewById(R.id.pckageuserbtn);
        packageCarsbtn = findViewById(R.id.packageCarsbtn);
        packageDestinationBtn = findViewById(R.id.packageDestinationBtn);
        packageTourLayout = findViewById(R.id.packageTourLayout);
        cebuTourLayout = findViewById(R.id.cebuTourLayout);
        oslobpackageTourLayout = findViewById(R.id.oslobpackageTourLayout);
        safariTourLayout = findViewById(R.id.safariTourLayout);
        simalaTourLayout = findViewById(R.id.simalaTourLayout);
        packagecartbtn = findViewById(R.id.packagecartbtn);
        packagehomebtn = findViewById(R.id.packagehomebtn);

        Intent intent = getIntent();
        String email = intent.getStringExtra("userEmail");

        packagehomebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Package.this, Home.class);
                intent.putExtra("userEmail",email);
                startActivity(intent);
            }
        });
        packagecartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Package.this, Pending.class);
                intent.putExtra("userEmail",email);
                startActivity(intent);
            }
        });
        package_profile_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Package.this, userProfile.class);
                intent.putExtra("userEmail",email);
                startActivity(intent);
            }
        });
        packageCarsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Package.this, Car.class);
                intent.putExtra("userEmail",email);
                startActivity(intent);
            }
        });

        packageDestinationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Package.this, Destinations.class);
                intent.putExtra("userEmail",email);
                startActivity(intent);
            }
        });

        packageTourLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Package.this, PackageTour.class);
                intent.putExtra("userEmail",email);
                startActivity(intent);
            }
        });

        cebuTourLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Package.this, CebuTour.class);
                intent.putExtra("userEmail",email);
                startActivity(intent);
            }
        });

        oslobpackageTourLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Package.this, OslobTour.class);
                intent.putExtra("userEmail",email);
                startActivity(intent);
            }
        });

        safariTourLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Package.this, SafariTour.class);
                intent.putExtra("userEmail",email);
                startActivity(intent);
            }
        });

        simalaTourLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Package.this, SimalaTour.class);
                intent.putExtra("userEmail",email);
                startActivity(intent);
            }
        });
    }
}