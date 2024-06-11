package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        TextView tvUserInfo = findViewById(R.id.tvUserInfo);
        getDataFromIntent();


    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        String token = intent.getStringExtra("token");
        String username = intent.getStringExtra("token");
        String id = intent.getStringExtra("id");
        String email = intent.getStringExtra("email");
    }


}