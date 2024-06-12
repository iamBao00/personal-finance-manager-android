package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.TransactionAdapter;
import com.example.myapplication.Models.Category;
import com.example.myapplication.Models.Transaction;
import com.example.myapplication.Models.ViewTransaction;
import com.example.myapplication.Services.CategoryAPI;
import com.example.myapplication.Services.TransactionAPI;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListingTransactionActivity extends AppCompatActivity {
    private ArrayList<Transaction> transactionsReponded = new ArrayList<>();
    private ArrayList<ViewTransaction> viewTransactions = new ArrayList<>();
    private RecyclerView recyclerView;
    private EditText fromDate;
    private EditText toDate;
    private Button btnFilterByDate;
    private TransactionAdapter transactionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listing_transaction);

        fromDate = findViewById(R.id.fromDate);
        toDate = findViewById(R.id.toDate);
        btnFilterByDate = findViewById(R.id.btnFilterByDate);
        recyclerView = findViewById(R.id.rycyclerViewTransaction);

        fromDate.setOnClickListener(v -> showDatePickerDialog(fromDate));
        toDate.setOnClickListener(v -> showDatePickerDialog(toDate));

        Intent myIntent = getIntent();
        String token = myIntent.getStringExtra("token");

        // Initialize RecyclerView and Adapter
        transactionAdapter = new TransactionAdapter(viewTransactions);
        recyclerView.setAdapter(transactionAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        // Load initial transactions
        getListTransaction(token).thenCompose(result -> processTransactions(token))
                .thenRun(this::updateRecyclerView)
                .exceptionally(throwable -> {
                    throwable.printStackTrace();
                    return null;
                });

        // Set up filter button
        btnFilterByDate.setOnClickListener(v -> {
            String startDate = fromDate.getText().toString();
            String endDate = toDate.getText().toString();
            if (!startDate.isEmpty() && !endDate.isEmpty()) {
                getTransactionsWithFilterDate(token, startDate, endDate)
                        .thenCompose(result -> processTransactions(token))
                        .thenRun(this::updateRecyclerView)
                        .exceptionally(throwable -> {
                            throwable.printStackTrace();
                            return null;
                        });
            }
        });
    }

    private void showDatePickerDialog(EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                ListingTransactionActivity.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // +1 because January is 0
                    String date = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                    editText.setText(date);
                },
                year, month, day);
        datePickerDialog.show();
    }

    private CompletableFuture<Void> getListTransaction(String token) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        TransactionAPI transactionAPI = TransactionAPI.transactionAPI;
        Call<ArrayList<Transaction>> call = transactionAPI.getTransactions(token);
        call.enqueue(new Callback<ArrayList<Transaction>>() {
            @Override
            public void onResponse(Call<ArrayList<Transaction>> call, Response<ArrayList<Transaction>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    transactionsReponded = response.body();
                    future.complete(null);
                } else {
                    future.completeExceptionally(new RuntimeException("API Get List Transaction Response not success or response body empty"));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Transaction>> call, Throwable t) {
                future.completeExceptionally(t);
            }
        });
        return future;
    }

    private CompletableFuture<Void> getTransactionsWithFilterDate(String token, String startDate, String endDate) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        TransactionAPI transactionAPI = TransactionAPI.transactionAPI;
        Call<ArrayList<Transaction>> call = transactionAPI.getTransactionsWithFilterDate(token, startDate, endDate);
        call.enqueue(new Callback<ArrayList<Transaction>>() {
            @Override
            public void onResponse(Call<ArrayList<Transaction>> call, Response<ArrayList<Transaction>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    transactionsReponded = response.body();
                    future.complete(null);
                } else {
                    future.completeExceptionally(new RuntimeException("API Get Transactions With Filter Date Response not success or response body empty"));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Transaction>> call, Throwable t) {
                future.completeExceptionally(t);
            }
        });
        return future;
    }

    private CompletableFuture<Void> processTransactions(String token) {
        List<CompletableFuture<Void>> categoryFutures = new ArrayList<>();
        viewTransactions.clear();

        for (Transaction transaction : transactionsReponded) {
            CompletableFuture<Void> categoryFuture = getCategoryByID(token, transaction.getCategoryId())
                    .thenAccept(category -> {
                        synchronized (viewTransactions) {
                            viewTransactions.add(new ViewTransaction(transaction, category));
                        }
                    }).exceptionally(throwable -> {
                        throwable.printStackTrace();
                        return null;
                    });

            categoryFutures.add(categoryFuture);
        }

        return CompletableFuture.allOf(categoryFutures.toArray(new CompletableFuture[0]));
    }

    private void updateRecyclerView() {
        runOnUiThread(() -> {
            transactionAdapter.notifyDataSetChanged();
            recyclerView.scrollToPosition(0);
        });
    }

    private CompletableFuture<Category> getCategoryByID(String token, String id) {
        CompletableFuture<Category> future = new CompletableFuture<>();
        CategoryAPI categoryAPI = CategoryAPI.categoryAPI;

        Call<Category> call = categoryAPI.getCategoryById(token, id);
        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Category category = response.body();
                    future.complete(category);
                } else {
                    future.completeExceptionally(new RuntimeException("API Get Category By ID Response not success or response body empty"));
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                future.completeExceptionally(t);
            }
        });
        return future;
    }
}

