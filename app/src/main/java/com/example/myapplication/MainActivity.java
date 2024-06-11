package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // get data from intent from loginActivity
        Intent intent = getIntent();
        String token = intent.getStringExtra("token");
        String username = intent.getStringExtra("token");
        String id = intent.getStringExtra("id");
        String email = intent.getStringExtra("email");
        Long balance = intent.getLongExtra("balance",-1);


        TextView tvBalance = findViewById(R.id.tvBalance);
        tvBalance.setText(String.valueOf(balance));

        TextView tvDanhSachThuChi = findViewById(R.id.tvDanhSachThuChi);
        tvDanhSachThuChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, ListingTransactionActivity.class);
                startActivity(myIntent);
            }
        });

    }



}