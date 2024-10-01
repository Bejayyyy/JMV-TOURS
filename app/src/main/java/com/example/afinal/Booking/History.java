package com.example.afinal.Booking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afinal.APIService.APIService;
import com.example.afinal.CebuTour;
import com.example.afinal.Constructor.HistoryBookingAdapter;
import com.example.afinal.Package;
import com.example.afinal.R;
import com.example.afinal.Response.HistoryBookingResponse;
import com.example.afinal.Response.ApiResponse; // Import your API response class
import com.example.afinal.Retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class History extends AppCompatActivity {
    private RecyclerView recyclerViewHistory;
    private HistoryBookingAdapter adapter;
    private List<HistoryBookingResponse> bookingList = new ArrayList<>();
    TextView tabPending1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);

        // Initialize the RecyclerView
        recyclerViewHistory = findViewById(R.id.recyclerViewHistory); // Ensure this ID matches your XML
        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(this));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tabPending1 = findViewById(R.id.tabPending1);
        // Get email from the Intent
        String userEmail = getIntent().getStringExtra("userEmail");

        // Fetch completed bookings
        fetchCompletedBookings(userEmail);

        tabPending1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(History.this, Pending.class);
                intent.putExtra("userEmail",userEmail);
                startActivity(intent);
            }
        });
    }

    private void fetchCompletedBookings(String email) {
        APIService apiService = RetrofitClient.getRetrofitInstance().create(APIService.class);
        Call<ApiResponse> call = apiService.fetchCompletedBookings(email); // Change to call ApiResponse

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if ("success".equals(response.body().getStatus())) { // Check for success status
                        bookingList = response.body().getData(); // Extract the data array
                        adapter = new HistoryBookingAdapter(bookingList);
                        recyclerViewHistory.setAdapter(adapter);
                    } else {
                        // Handle the error message from the API
                        Toast.makeText(History.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Log error and show message
                    Log.e("History API", "Response not successful: " + response.message());
                    Toast.makeText(History.this, "Failed to load bookings: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable throwable) {
                Toast.makeText(History.this, "Failed to Load Bookings: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("History API", "Error: " + throwable.getMessage());
            }
        });
    }
}
