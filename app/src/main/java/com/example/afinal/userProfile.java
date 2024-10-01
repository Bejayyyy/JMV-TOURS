package com.example.afinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.afinal.APIService.APIService;
import com.example.afinal.Response.UserProfileResponse;
import com.example.afinal.Retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class userProfile extends AppCompatActivity {
    ImageView editIcon;
    TextView  userProfileName;
    TextView userProfileEmail;
    TextView userProfileAddress;
    TextView userProfileContactNumber;
    TextView profileName;
    TextView userProfileprofileEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        editIcon = findViewById(R.id.editIcon);
        userProfileName = findViewById(R.id.userProfileName);
        userProfileEmail = findViewById(R.id.userProfileEmail);
        userProfileAddress = findViewById(R.id.userProfileAddress);
        userProfileContactNumber = findViewById(R.id.userProfileContactNumber);
        profileName = findViewById(R.id.usereName);
        userProfileprofileEmail = findViewById(R.id.userProfileprofileEmail);

        Intent intent = getIntent();
        String email = intent.getStringExtra("userEmail");

        if (email != null){
            fetchUserProfile(email);
        }else {
            Toast.makeText(userProfile.this,"Error: Email not Found", Toast.LENGTH_SHORT).show();
        }
        editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(userProfile.this, EditProfile.class);
                intent.putExtra("userEmail", email);
                startActivity(intent);
            }
        });
    }

    private  void fetchUserProfile(String email){
        APIService apiService = RetrofitClient.getRetrofitInstance().create(APIService.class);
        Call<UserProfileResponse> call = apiService.getUserProfile(email);

        call.enqueue(new Callback<UserProfileResponse>() {
            @Override
            public void onResponse(Call<UserProfileResponse> call, Response<UserProfileResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    UserProfileResponse userProfile = response.body();
                    if (userProfile.isSuccess()){
                        userProfileName.setText(userProfile.getUserProfileName());
                        userProfileEmail.setText(userProfile.getUserProfileEmail());
                        userProfileAddress.setText(userProfile.getUserProfileAddress());
                        userProfileContactNumber.setText(userProfile.getUserProfileContactNumber());
                        profileName.setText(userProfile.getUserProfileName());
                        userProfileprofileEmail.setText(userProfile.getUserProfileEmail());

                    }else{
                        Toast.makeText(userProfile.this,"Profile not found", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(userProfile.this,"Error Fetching Profile", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserProfileResponse> call, Throwable throwable) {
                Toast.makeText(userProfile.this,"Error" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}