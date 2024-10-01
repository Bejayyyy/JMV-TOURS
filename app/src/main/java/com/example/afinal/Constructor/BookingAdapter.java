package com.example.afinal.Constructor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afinal.Booking.Pending;
import com.example.afinal.R;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {
    private List<BookingResponse> bookingList;
    private Pending pendingActivity; // Reference to Pending activity to call completeBooking()

    // Constructor to accept the list of bookings and the Pending activity context
    public BookingAdapter(List<BookingResponse> bookingList, Pending pendingActivity) {
        this.bookingList = bookingList;
        this.pendingActivity = pendingActivity;  // Store reference to Pending activity
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item_booking layout for each item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        // Get the booking for the current position
        BookingResponse booking = bookingList.get(position);

        // Bind data to the TextViews
        holder.packageName.setText(booking.getPackageName());
        holder.departureDate.setText(booking.getDepartureDate());
        holder.car.setText(booking.getCar());

        // Handle Complete Button click
        holder.completeButton.setOnClickListener(v -> {
            // Call completeBooking method in Pending activity to complete this booking
            pendingActivity.completeBooking(booking.getBookingId(), position);
        });
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    // ViewHolder class to hold the view elements
    static class BookingViewHolder extends RecyclerView.ViewHolder {
        private TextView packageName;
        private TextView departureDate;
        private TextView car;
        private Button completeButton;  // Complete Button for completing the booking

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize TextViews and Button using the correct IDs from the layout
            packageName = itemView.findViewById(R.id.packageNameTextView); // ID from item_booking.xml
            departureDate = itemView.findViewById(R.id.departureDateTextView); // ID from item_booking.xml
            car = itemView.findViewById(R.id.carTextView); // ID from item_booking.xml
            completeButton = itemView.findViewById(R.id.package_complete_button); // Ensure this ID matches your layout
        }
    }
}