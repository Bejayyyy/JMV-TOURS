package com.example.afinal.Constructor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afinal.R;
import com.example.afinal.Response.HistoryBookingResponse;

import java.util.List;

public class HistoryBookingAdapter extends RecyclerView.Adapter<HistoryBookingAdapter.HistoryBookingViewHolder> {
    private List<HistoryBookingResponse> bookingList;

    // Corrected constructor name
    public HistoryBookingAdapter(List<HistoryBookingResponse> bookingList) {
        this.bookingList = bookingList;
    }

    @NonNull
    @Override
    public HistoryBookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking_history, parent, false);
        return new HistoryBookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryBookingViewHolder holder, int position) {
        // Get the booking for the current position
        HistoryBookingResponse booking = bookingList.get(position);

        // Bind data to the TextViews
        holder.packageName.setText(booking.getPackageName());
        holder.historypriceTextView.setText(String.valueOf(booking.getPrice())); // Assuming getPrice() returns the price
        holder.departureDate.setText(booking.getDepartureDate());
        holder.car.setText(booking.getCarName());
    }


    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    // ViewHolder class to hold the view elements
    static class HistoryBookingViewHolder extends RecyclerView.ViewHolder {
        private TextView packageName;
        private TextView departureDate;
        private TextView car;
        private  TextView historypriceTextView;
        public HistoryBookingViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize TextViews using the correct IDs from the layout
            packageName = itemView.findViewById(R.id.packageNameTextView); // Ensure these match your layout
            departureDate = itemView.findViewById(R.id.departureDateTextView); // Ensure these match your layout
            car = itemView.findViewById(R.id.carTextView); // Ensure these match your layout
            historypriceTextView = itemView.findViewById(R.id.historypriceTextView);
        }
    }
}
