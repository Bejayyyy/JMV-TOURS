package com.example.afinal.Booking;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.afinal.APIService.APIService;
import com.example.afinal.R;
import com.example.afinal.Retrofit.RetrofitClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Booking extends AppCompatActivity {
    private Spinner packageTypeSpinner, carServiceSpinner;
    private EditText bookingPhoneNumberInput, bookingAddressInput, bookingDepartureDateInput;
    private TextView bookingEmailInput;
    private Button bookNowButton;
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        // Initialize the views
        packageTypeSpinner = findViewById(R.id.packageTypeSpinner);
        carServiceSpinner = findViewById(R.id.carServiceSpinner);
        bookingPhoneNumberInput = findViewById(R.id.BookingPhoneNumberInput);
        bookingAddressInput = findViewById(R.id.BookingAddressInput);
        bookingDepartureDateInput = findViewById(R.id.BookingDepartureDateInput);
        bookingEmailInput = findViewById(R.id.BookingEmailInput);
        bookNowButton = findViewById(R.id.bookNowButton);

        // Get email from Intent
        Intent intent = getIntent();
        String userEmail = intent.getStringExtra("userEmail");
        bookingEmailInput.setText(userEmail);

        // Initialize Retrofit API service
        apiService = RetrofitClient.getRetrofitInstance().create(APIService.class);

        // Setup DatePickerDialog for Departure Date input
        bookingDepartureDateInput.setInputType(InputType.TYPE_NULL);
        bookingDepartureDateInput.setOnClickListener(v -> showDatePicker());

        // Fetch data for PackageType and Car Service spinners
        fetchPackageType();
        fetchCarOptions();

        // Handle "Book Now" button click
        bookNowButton.setOnClickListener(v -> {
            // Validate inputs
            if (validateInputs()) {
                String email = bookingEmailInput.getText().toString();
                String phoneNumber = bookingPhoneNumberInput.getText().toString();
                String address = bookingAddressInput.getText().toString();
                String departureDate = bookingDepartureDateInput.getText().toString();
                String packageType = packageTypeSpinner.getSelectedItem().toString();
                String carType = carServiceSpinner.getSelectedItem().toString();

                // Call the function to send the booking request
                submitBooking(email, packageType, carType, departureDate);
            }
        });
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            // Create a Calendar object with the selected date
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(year1, month1, dayOfMonth);

            // Format the selected date as "YYYY-MM-DD"
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String formattedDate = dateFormat.format(selectedDate.getTime());

            // Set the formatted date to the EditText
            bookingDepartureDateInput.setText(formattedDate);
        }, year, month, day);

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()); // Disable past dates
        datePickerDialog.show();
    }

    private void fetchPackageType() {
        // Fetch available package types from the server
        Call<List<String>> call = apiService.getPackages();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<String> packageTypes = response.body();
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(Booking.this, android.R.layout.simple_spinner_item, packageTypes);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    packageTypeSpinner.setAdapter(adapter);
                } else {
                    Toast.makeText(Booking.this, "Failed to load packages", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(Booking.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchCarOptions() {
        // Fetch available car options from the server
        Call<List<String>> call = apiService.getCars();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<String> carOptions = response.body();
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(Booking.this, android.R.layout.simple_spinner_item, carOptions);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    carServiceSpinner.setAdapter(adapter);
                } else {
                    Toast.makeText(Booking.this, "Failed to load cars", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(Booking.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateInputs() {
        String email = bookingEmailInput.getText().toString().trim();
        String phoneNumber = bookingPhoneNumberInput.getText().toString().trim();
        String address = bookingAddressInput.getText().toString().trim();
        String departureDate = bookingDepartureDateInput.getText().toString().trim();

        // Check if all fields are filled
        if (email.isEmpty() || phoneNumber.isEmpty() || address.isEmpty() || departureDate.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Check if departure date is valid (not in the past)
        if (isDepartureDateInvalid(departureDate)) {
            Toast.makeText(this, "Departure date must be today or in the future", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean isDepartureDateInvalid(String departureDate) {
        // Parse the departure date from the string
        String[] dateParts = departureDate.split("-");
        int year = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]) - 1; // Month is 0-based in Calendar
        int day = Integer.parseInt(dateParts[2]);

        // Create a Calendar instance for the departure date
        Calendar departureCalendar = Calendar.getInstance();
        departureCalendar.set(year, month, day, 0, 0, 0); // Set time to the start of the day

        // Get today's date
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0); // Set time to the start of the day

        // Compare the departure date with today's date
        return departureCalendar.before(today); // Return true if departure date is in the past
    }

    private void submitBooking(String email, String packageType, String carType, String departureDate) {
        // Create a map of parameters to send in the request
        Map<String, String> bookingDetails = new HashMap<>();
        bookingDetails.put("email", email);
        bookingDetails.put("package", packageType);
        bookingDetails.put("car", carType);
        bookingDetails.put("departure_date", departureDate);

        // Make the POST request to send the booking
        Call<Map<String, Object>> call = apiService.submitBooking(bookingDetails);
        call.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    boolean success = (boolean) response.body().get("success");
                    String message = (String) response.body().get("message");

                    Toast.makeText(Booking.this, message, success ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG).show();
                    if (success) {
                        Intent intent = new Intent(Booking.this, Pending.class);
                        intent.putExtra("userEmail", email);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(Booking.this, "Failed to submit booking", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                Toast.makeText(Booking.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}