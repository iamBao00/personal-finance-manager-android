package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Models.Category;
import com.example.myapplication.Models.TransactionRequest;
import com.example.myapplication.Services.CategoryAPI;
import com.example.myapplication.Services.TransactionAPI;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTransactionActivity extends AppCompatActivity {
    // Khai báo biến cho các controls
    Spinner categorySpinner;
    EditText tvAmount, tvDescription, toDate;
    Button btnAddTransaction;
    ArrayList<Category> categories;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        // Lấy token từ Intent
        Intent myIntent = getIntent();
        token = myIntent.getStringExtra("token");

        // Ánh xạ và thiết lập controls
        setControl();
        getListCategories(token);
    }

    private void setControl() {
        // Ánh xạ controls
        categorySpinner = findViewById(R.id.categorySpinner);
        tvAmount = findViewById(R.id.tvAmount);
        tvDescription = findViewById(R.id.tvDescription);
        toDate = findViewById(R.id.toDate);
        btnAddTransaction = findViewById(R.id.btnAddTransaction);

        // Set sự kiện khi click vào trường chọn ngày
        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // Set sự kiện khi click vào nút Thêm giao dịch
        btnAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thêm mới giao dịch
                addNewTransaction();
            }
        });
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        // +1 because January is 0
                        String date = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                        toDate.setText(date);
                    }
                },
                year, month, day);
        datePickerDialog.show();
    }

    private void getListCategories(String token) {
        CategoryAPI categoryAPI = CategoryAPI.categoryAPI;
        Call<ArrayList<Category>> call = categoryAPI.getCategories(token);
        call.enqueue(new Callback<ArrayList<Category>>() {
            @Override
            public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Lưu danh sách categories
                    categories = response.body();
                    // Thiết lập Spinner
                    setupCategorySpinner(categories);
                } else {
                    // Xử lý khi không lấy được danh sách danh mục
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Category>> call, Throwable t) {
                // Xử lý khi gặp lỗi kết nối
            }
        });
    }

    private void setupCategorySpinner(ArrayList<Category> categories) {
        ArrayList<String> displayList = new ArrayList<>();
        for (Category category : categories) {
            String displayText = category.getName()+ "(" + category.getType() + ")";
            displayList.add(displayText);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, displayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
    }

    private void addNewTransaction() {

        // Lấy category id từ Spinner
        int selectedPosition = categorySpinner.getSelectedItemPosition();
        Category selectedCategory = categories.get(selectedPosition);
        String categoryId = selectedCategory.getId();

        // Lấy các giá trị từ EditText
        String amount = tvAmount.getText().toString();
        String description = tvDescription.getText().toString();
        String date = toDate.getText().toString();

        // Kiểm tra các trường có trống không
        if (categoryId.isEmpty() || amount.isEmpty() || description.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        long amountL;
        try {
            amountL = Long.parseLong(amount);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid amount", Toast.LENGTH_SHORT).show();
            return;
        }
        // Tạo đối tượng TransactionRequest
        TransactionRequest transactionRequest = new TransactionRequest(categoryId, amountL, description, date);

        // Gọi API để thêm giao dịch
        TransactionAPI transactionAPI = TransactionAPI.transactionAPI;
        Call<TransactionRequest> call = transactionAPI.addTransaction(token, transactionRequest);
        call.enqueue(new Callback<TransactionRequest>() {
            @Override
            public void onResponse(Call<TransactionRequest> call, Response<TransactionRequest> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Xử lý khi thêm giao dịch thành công
                    Toast.makeText(AddTransactionActivity.this, "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                    tvAmount.setText("");
                    tvDescription.setText("");
                    toDate.setText("");
                } else {
                    // Xử lý khi thêm giao dịch không thành công
                    Toast.makeText(AddTransactionActivity.this, "Failed to add transaction", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TransactionRequest> call, Throwable t) {
                // Xử lý khi gặp lỗi kết nối
                Toast.makeText(AddTransactionActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
