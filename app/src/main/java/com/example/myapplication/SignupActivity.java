package com.example.myapplication;
//
//import android.os.Bundle;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//public class SignupActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_signup);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//    }
//}

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Models.SignupRequest;
import com.example.myapplication.Models.UserRespond;
import com.example.myapplication.Services.UserAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    EditText edtUsername, edtEmail, edtPassword;
    Button btnSignUp;
    TextView tvLogin1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        setControl();
        setEvent();
    }

    private void setControl() {
        edtUsername = findViewById(R.id.edtUserName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvLogin1 = findViewById(R.id.tvLogin1);
    }

    private void setEvent() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtUsername.getText().toString())){
                    edtUsername.setError("Please enter your username");
                    edtUsername.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(edtEmail.getText().toString())){
                    edtEmail.setError("Please enter your email");
                    edtEmail.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(edtPassword.getText().toString())){
                    edtPassword.setError("Please enter your password");
                    edtPassword.requestFocus();
                    return;
                }

                String username = edtUsername.getText().toString();
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                signUpUser(username, email, password);
            }
        });

        tvLogin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logic to navigate back to login activity
                finish();
            }
        });
    }

    public void signUpUser(String username, String email, String password) {
        UserAPI userAPI = UserAPI.userAPI;
        SignupRequest requestBody = new SignupRequest(username, email, password);
        Call<UserRespond> call = userAPI.signUp(requestBody);
        call.enqueue(new Callback<UserRespond>() {
            @Override
            public void onResponse(Call<UserRespond> call, Response<UserRespond> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserRespond userRespond = response.body();
                    Toast.makeText(SignupActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();

                    // Xử lý logic khi đăng ký thành công, ví dụ chuyển sang LoginActivity
                    finish();
                } else {
                    // Xử lý logic khi đăng ký thất bại
                    Toast.makeText(SignupActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserRespond> call, Throwable t) {
                // Xử lý logic khi xảy ra lỗi
                Toast.makeText(SignupActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
