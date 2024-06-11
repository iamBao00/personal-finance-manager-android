package com.example.myapplication;

import android.os.Build;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.TransactionAdapter;
import com.example.myapplication.Models.ViewTransaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListingTransactionActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listing_transaction);
        // (long amount, String description, Date date, String categoryName, String type)
        List<ViewTransaction> viewTransactionList = new ArrayList<ViewTransaction>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            viewTransactionList.add(new ViewTransaction(100000L, "abc", LocalDate.now(),"Tien Thue Nha", "expense"));
            viewTransactionList.add(new ViewTransaction(200000L, "Luong Thang 11", LocalDate.now(),"Tien Luong", "income"));
            viewTransactionList.add(new ViewTransaction(300000L, "abcd", LocalDate.now(),"Tien Thue Nha", "expense"));
        }

        recyclerView = findViewById(R.id.rycyclerViewTransaction);

        TransactionAdapter transactionAdapter = new TransactionAdapter(viewTransactionList);
        recyclerView.setAdapter(transactionAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
    }
}