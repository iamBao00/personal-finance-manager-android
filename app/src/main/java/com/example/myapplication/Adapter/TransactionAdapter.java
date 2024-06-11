package com.example.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.ViewTransaction;
import com.example.myapplication.R;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MyViewHolder> {
    private List<ViewTransaction> viewTransactionList;

    public TransactionAdapter(List <ViewTransaction> list){
        this.viewTransactionList = list;
    }

    // Chỉ định Item sư dụng trong Recycler View
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_item, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return viewTransactionList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ViewTransaction transaction = viewTransactionList.get(position);
        holder.tvAmount.setText(String.valueOf(transaction.getAmount()));
        holder.tvDate.setText(String.valueOf(transaction.getDate()));
        holder.tvDescription.setText(transaction.getDescription());
        holder.tvCategoryName.setText(transaction.getCategoryName());
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tvAmount, tvDescription, tvCategoryName, tvDate;
        private RelativeLayout itemTransaction;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            setControl();
        }

        private void setControl() {
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            tvDate = itemView.findViewById(R.id.tvDate);

            // Dung su ly su kien click vao 1 Transaction trong list
            itemTransaction = itemView.findViewById(R.id.itemTransaction);
        }
    }
}
