package com.example.afinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.afinal.APIService.APIService;
import com.example.afinal.Constructor.UserProfileUpdateRequest;
import com.example.afinal.Response.UserProfileResponse;
import com.example.afinal.Retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfile extends AppCompatActivity {
    ImageView editProfileBackBtn;
    TextView doneBtn;
    EditText editProfileName;
    EditText editProfileAddress;
    EditText editProfileContactNumber;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        doneBtn = findViewById(R.id.doneButton);
        editProfileName = findViewById(R.id.editProfileName);
        editProfileAddress = findViewById(R.id.editProfileAddress);
        editProfileContactNumber = findViewById(R.id.editProfileContactNumber);
        editProfileBackBtn = findViewById(R.id.editProfileBackBtn);

        editProfileBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfile.this, userProfile.class);
                intent.putExtra("userEmail", email);
                startActivity(intent);
            }
        });

        // Retrieve the email passed from the previous activity
        Intent intent = getIntent();
        email = intent.getStringExtra("userEmail");

        // Fetch the user profile if email is available
        if (email != null) {
            fetchUserProfile(email);
        } else {
            Toast.makeText(this, "Error: Email not found", Toast.LENGTH_SHORT).show();
        }

        // Set up listener for the done button
        doneBtn.setOnClickListener(view -> updateUserProfile(email));  // Pass the email to updateUserProfile method
    }

    private void fetchUserProfile(String email) {
        APIService apiService = RetrofitClient.getRetrofitInstance().create(APIService.class);
        Call<UserProfileResponse> call = apiService.getUserProfile(email);

        call.enqueue(new Callback<UserProfileResponse>() {
            @Override
            public void onResponse(Call<UserProfileResponse> call, Response<UserProfileResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserProfileResponse userProfile = response.body();
                    if (userProfile.isSuccess()) {
                        // Populate EditTexts with fetched user profile data
                        editProfileName.setText(userProfile.getUserProfileName());
                        editProfileAddress.setText(userProfile.getUserProfileAddress());
                        editProfileContactNumber.setText(userProfile.getUserProfileContactNumber());
                    } else {
                        Toast.makeText(EditProfile.this, "Profile not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Log or show detailed error for debugging
                    Toast.makeText(EditProfile.this, "Error fetching profile: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserProfileResponse> call, Throwable throwable) {
                Toast.makeText(EditProfile.this, "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUserProfile(String email) {
        String name = editProfileName.getText().toString().trim();
        String address = editProfileAddress.getText().toString();
        String contactNumber = editProfileContactNumber.getText().toString();

        UserProfileUpdateRequest request = new UserProfileUpdateRequest(email, name, address, contactNumber);

        APIService apiService = RetrofitClient.getRetrofitInstance().create(APIService.class);
        Call<Void> call = apiService.updateUserProfile(request); // Make sure email is passed

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditProfile.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditProfile.this, userProfile.class);
                    intent.putExtra("userEmail", email); // Pass email to userProfile
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(EditProfile.this, "Error updating profile: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                Toast.makeText(EditProfile.this, "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
