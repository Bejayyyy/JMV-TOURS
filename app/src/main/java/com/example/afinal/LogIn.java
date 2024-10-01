package com.example.afinal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.afinal.APIService.APIService;
import com.example.afinal.Booking.Booking;
import com.example.afinal.Booking.Pending;
import com.example.afinal.Constructor.LoginRequest;
import com.example.afinal.Response.LoginResponse;
import com.example.afinal.Retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogIn extends AppCompatActivity {
    Button signInBtn;
    EditText mainInputEmail;
    EditText mainInputPassword;
    TextView logIndontHaveAccountText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_in);

        View mainView = findViewById(R.id.main);
ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
        Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
        return insets;



         });

        signInBtn = findViewById(R.id.logsignInButton);
        mainInputEmail = findViewById(R.id.loginEmailInput);
        mainInputPassword = findViewById(R.id.loginPasswordInput);
        logIndontHaveAccountText = findViewById(R.id.logIndontHaveAccountText);

        logIndontHaveAccountText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogIn.this, SignUp.class);
                startActivity(intent);
            }
        });
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mainInputEmail.getText().toString().trim();
                String password = mainInputPassword.getText().toString();

                if (email.isEmpty() || password.isEmpty()){
                    Toast.makeText(LogIn.this, "PLease fill in all fields",Toast.LENGTH_SHORT).show();
                    return;
                }

                LoginRequest loginRequest = new LoginRequest(email,password);

                APIService apiService = RetrofitClient.getRetrofitInstance().create(APIService.class);
                Call<LoginResponse> call = apiService.loginUser(loginRequest);

                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()){
                            LoginResponse loginResponse = response.body();
                            if (loginResponse != null && "success".equals(loginResponse.getStatus())){
                                Toast.makeText(LogIn.this, "Login Succesful", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(LogIn.this, Home.class);
                                intent.putExtra("userEmail",email);
                                startActivity(intent);


                                finish();
                            }else {
                                Toast.makeText(LogIn.this,"Login Failed" + loginResponse.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(LogIn.this,"Login Failed" + response.message(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable throwable) {
                        Log.e("Login", "Login Failed",throwable);
                        Toast.makeText(LogIn.this,"Error" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });






            }
        });
    }
}