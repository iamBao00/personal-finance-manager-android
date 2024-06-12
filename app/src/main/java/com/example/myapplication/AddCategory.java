package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Models.Category;
import com.example.myapplication.Models.CategoryRequest;
import com.example.myapplication.Services.CategoryAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCategory extends AppCompatActivity {
    private Spinner typeSpinner;
    private EditText tvCategoryName;
    private String selectedType;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_category);

        // Lấy token từ Intent
        Intent myIntent = getIntent();
        token = myIntent.getStringExtra("token");

        tvCategoryName = findViewById(R.id.tvCategoryName);
        typeSpinner = findViewById(R.id.typeSpinner);

        // Define the string array directly
        String[] typeArray = {"expense", "income"};

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, typeArray);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        typeSpinner.setAdapter(adapter);

        // Set the selected item listener
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedType = (String) parentView.getItemAtPosition(position);
//                Toast.makeText(AddCategory.this, "Selected: " + selectedType, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        findViewById(R.id.btnAddTransaction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle button click
                String categoryName = tvCategoryName.getText().toString();
//                Toast.makeText(AddCategory.this, "Category: " + categoryName + ", Type: " + selectedType, Toast.LENGTH_LONG).show();
//                System.out.println("TOKEN: "+ token);
                addNewCategory();
            }
        });




    }

    private void addNewCategory() {
        // Lấy các giá trị từ EditText và Spinner
        String categoryName = tvCategoryName.getText().toString();
        String type = selectedType;

        // Kiểm tra các trường có trống không
        if (categoryName.isEmpty() || type.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo đối tượng CategoryRequest
        CategoryRequest categoryRequest = new CategoryRequest(categoryName, type);

        // Gọi API để thêm danh mục
        CategoryAPI categoryAPI = CategoryAPI.categoryAPI;
        Call<Category> call = categoryAPI.addCategory(token, categoryRequest);
        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Xử lý khi thêm danh mục thành công
                    Toast.makeText(AddCategory.this, "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                    tvCategoryName.setText("");
                } else {
                    // Xử lý khi thêm danh mục không thành công
                    Toast.makeText(AddCategory.this, "Failed to add category", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                // Xử lý khi gặp lỗi kết nối
                Toast.makeText(AddCategory.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
}}