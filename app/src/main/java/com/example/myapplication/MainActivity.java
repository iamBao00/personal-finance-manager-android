package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Models.User;
import com.example.myapplication.Models.User;
import com.example.myapplication.Services.UserAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    String tokenFinal;
    TextView tvBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // get data from intent from loginActivity
        Intent intent = getIntent();
        String token = intent.getStringExtra("token");
        tokenFinal = "Bearer " + token;

        tvBalance = findViewById(R.id.tvBalance);

        TextView tvDanhSachThuChi = findViewById(R.id.tvDanhSachThuChi);
        tvDanhSachThuChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, ListingTransactionActivity.class);
                myIntent.putExtra("token", tokenFinal);
                startActivity(myIntent);
            }
        });

        Button addTransaction = findViewById(R.id.btnAddTransaction);
        addTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, AddTransactionActivity.class);
                myIntent.putExtra("token", tokenFinal);
                startActivity(myIntent);
            }
        });

        // Gọi API để lấy thông tin người dùng hiện tại
        getCurrentUser();
    }

    private void getCurrentUser() {
        UserAPI userAPI = UserAPI.userAPI;
        Call<User> call = userAPI.current(tokenFinal);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User currentUser = response.body();
                    System.out.println("ĐÃ GỌI API CURRENT");
                    System.out.println("USERNAME: "+ currentUser.getUsername());
                    System.out.println("BALANCE: "+ currentUser.getBalance());
                    // Cập nhật lại balance trong TextView
                    tvBalance.setText(String.valueOf(currentUser.getBalance()));
                } else {
                    // Xử lý khi không thể lấy được thông tin người dùng hiện tại
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Xử lý khi gặp lỗi kết nối
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh lại thông tin người dùng khi quay lại MainActivity
        getCurrentUser();
    }
}
