package com.example.myapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.Toast;

import com.example.myapplication.Models.LoginRequest;
import com.example.myapplication.Models.UserRespond;
import com.example.myapplication.R.id;
import com.example.myapplication.Services.UserAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText edtUsername, edtPassword;
    Button btnLogin;
    TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        setControl();
        setEvent();
    }

    private void setControl() {
        edtUsername = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(id.tvRegister);
    }

    private void setEvent() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtUsername.getText().toString())){
                    edtUsername.setError("Please enter your username");
                    edtUsername.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(edtPassword.getText().toString())){
                    edtPassword.setError("Please enter your password");
                    edtPassword.requestFocus();
                    return;
                }

                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                loginUser(username,password);

            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(myIntent);
            }
        });
    }

    public void loginUser(String username, String password) {
        UserAPI userAPI = UserAPI.userAPI;
        LoginRequest requestBody = new LoginRequest(username,password);
        Call<UserRespond> call = userAPI.login(requestBody);
        call.enqueue(new Callback<UserRespond>() {
            @Override
            public void onResponse(Call<UserRespond> call, Response<UserRespond> response) {
                if (response.isSuccessful()) {
                    UserRespond userRespond = response.body();
                    System.out.println("TOKEN" + userRespond.getToken());
                    System.out.println("ID" + userRespond.getUserDetail().getId());
                    System.out.println("USERNAME" + userRespond.getUserDetail().getUsername());
                    System.out.println("EMAIL" + userRespond.getUserDetail().getEmail());
                    System.out.println("BALANCE" + userRespond.getUserDetail().getBalance());

                     //Xử lý logic khi đăng nhập thành công, ví dụ chuyển sang MainActivity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("token", userRespond.getToken());
                    intent.putExtra("id", userRespond.getUserDetail().getId());
                    intent.putExtra("username", userRespond.getUserDetail().getUsername());
                    intent.putExtra("email", userRespond.getUserDetail().getEmail());
                    intent.putExtra("balance", userRespond.getUserDetail().getBalance());
                    startActivity(intent);
                    finish();
                } else {
                    // Xử lý logic khi đăng nhập thất bại, ví dụ hiển thị thông báo lỗi
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<UserRespond> call, Throwable t) {
                // Xử lý logic khi xảy ra lỗi, ví dụ hiển thị thông báo lỗi
                Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}