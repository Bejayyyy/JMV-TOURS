package com.example.afinal.Constructor;

public class BookingResponse {
    private int bookingId;          // Unique identifier for the booking
    private String userEmail;       // Email of the user who made the booking
    private String packageName;     // Name of the package booked
    private String car;             // Car associated with the booking
    private String departureDate;   // Departure date for the booking
    private String status;          // Status of the booking (e.g., Pending, Completed, Cancelled)


    // Parameterized constructor
    public BookingResponse(int bookingId, String userEmail, String packageName, String car, String departureDate, String status) {
        this.bookingId = bookingId;
        this.userEmail = userEmail;
        this.packageName = packageName;
        this.car = car;
        this.departureDate = departureDate;
        this.status = status;
    }
    // Getters and Setters
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}