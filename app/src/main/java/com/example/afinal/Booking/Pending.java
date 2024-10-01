package com.example.afinal.Booking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afinal.APIService.APIService;
import com.example.afinal.CebuTour;
import com.example.afinal.Constructor.BookingAdapter;
import com.example.afinal.Constructor.BookingResponse;
import com.example.afinal.Constructor.PendingResponse;
import com.example.afinal.Home;
import com.example.afinal.Package;
import com.example.afinal.R;
import com.example.afinal.Retrofit.RetrofitClient;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Pending extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BookingAdapter bookingAdapter;
    private List<BookingResponse> bookingList;
    private String userEmail;
    ImageView PendingbackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending);

        // Edge-to-edge handling
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get email from Intent
        Intent intent = getIntent();
        userEmail = intent.getStringExtra("userEmail");

        // Initialize RecyclerView and BookingAdapter
        recyclerView = findViewById(R.id.recyclerViewPending); // Ensure this ID exists in your XML
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookingList = new ArrayList<>();

        // Pass both bookingList and the context (Pending.this)
        bookingAdapter = new BookingAdapter(bookingList, Pending.this); // 'Pending.this' is the context
        recyclerView.setAdapter(bookingAdapter);

        // Set up click listener for tabHistory2 to navigate to History activity
        TextView tabHistory2 = findViewById(R.id.tabHistory1); // Find the TextView
        tabHistory2.setOnClickListener(v -> {
            Intent historyIntent = new Intent(Pending.this, History.class);
            historyIntent.putExtra("userEmail", userEmail); // Pass the email to History activity
            startActivity(historyIntent); // Start History activity
        });
        // Fetch the bookings
        fetchPendingBookings();

        PendingbackButton = findViewById(R.id.PendingbackButton);

        PendingbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Pending.this, Home.class);
                intent.putExtra("userEmail",userEmail);
                startActivity(intent);
            }
        });
    }

    // Function to call the API to complete a booking
    public void completeBooking(int bookingId, int position) {
        JsonObject bookingStatus = new JsonObject();
        bookingStatus.addProperty("booking_id", bookingId);
        bookingStatus.addProperty("status", "completed");

        // Call the API to update booking status to "completed"
        APIService apiService = RetrofitClient.getRetrofitInstance().create(APIService.class);
        Call<JsonObject> call = apiService.updateBookingStatus(bookingStatus);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                // Log the response to see what is returned
                Log.d("API Response", "Response code: " + response.code());
                if (response.isSuccessful()) {
                    Log.d("API Response", "Response body: " + response.body());

                    if (response.body() != null && response.body().has("success")) {
                        if (response.body().get("success").getAsBoolean()) {
                            // Successfully updated status
                            Toast.makeText(Pending.this, "Booking completed!", Toast.LENGTH_SHORT).show();

                            // Remove the completed booking from the list and update RecyclerView
                            bookingList.remove(position);
                            bookingAdapter.notifyItemRemoved(position);
                            bookingAdapter.notifyItemRangeChanged(position, bookingList.size());
                        } else {
                            Toast.makeText(Pending.this, "Failed to complete booking!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e("API Response", "Response body is null or doesn't contain 'success'");
                        Toast.makeText(Pending.this, "Unexpected response format!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("PendingBookings", "Response not successful: " + response.message());
                    Toast.makeText(Pending.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("API Call Failure", t.getMessage());
                Toast.makeText(Pending.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void fetchPendingBookings() {
        // Call API to get pending bookings and populate bookingList
        APIService apiService = RetrofitClient.getRetrofitInstance().create(APIService.class);

        Call<PendingResponse> call = apiService.getUserBookings(userEmail);
        call.enqueue(new Callback<PendingResponse>() {
            @Override
            public void onResponse(Call<PendingResponse> call, Response<PendingResponse> response) {
                if (response.isSuccessful()) {
                    PendingResponse pendingResponse = response.body();
                    if (pendingResponse != null) {
                        List<BookingResponse> pendingBookings = pendingResponse.getPendingBookings();
                        if (pendingBookings != null) {
                            bookingList.clear();
                            bookingList.addAll(pendingBookings);
                            bookingAdapter.notifyDataSetChanged(); // Notify adapter about data changes
                        } else {
                            Log.e("PendingBookings", "pendingBookings is null");
                        }
                    } else {
                        Log.e("PendingBookings", "pendingResponse is null");
                    }
                } else {
                    Log.e("PendingBookings", "Response not successful: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<PendingResponse> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }
}

