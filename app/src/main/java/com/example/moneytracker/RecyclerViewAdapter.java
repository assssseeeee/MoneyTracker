package com.example.moneytracker;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneytracker.data.MoneyTrackerContract;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewViewHolder> {
    Context context;
    Cursor cursor;

    public static class RecyclerViewViewHolder extends RecyclerView.ViewHolder {
        TextView itemNameTextViewCardView;
        TextView itemPriceTextViewCardView;
        TextView itemDateTextViewCardView;

        public RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameTextViewCardView = itemView.findViewById(R.id.itemNameTextViewCardView);
            itemDateTextViewCardView = itemView.findViewById(R.id.itemDateTextViewCardView);
            itemPriceTextViewCardView = itemView.findViewById(R.id.itemPriceTextViewCardView);
        }

        public void bindCursor(Cursor cursor){
            String itemName = cursor.getString(cursor.getColumnIndexOrThrow(MoneyTrackerContract.AddingExpenses.COLUMN_PRODUCT_NAME));
            String itemPrice = cursor.getString(cursor.getColumnIndexOrThrow(MoneyTrackerContract.AddingExpenses.COLUMN_PRODUCT_PRICE));
            String itemDateRegistered = cursor.getString(cursor.getColumnIndexOrThrow(MoneyTrackerContract.AddingExpenses.COLUMN_DATE_REGISTERED));

            itemNameTextViewCardView.setText(itemName);
            itemDateTextViewCardView.setText(itemDateRegistered);
            itemPriceTextViewCardView.setText(itemPrice);
        }
    }

    public RecyclerViewAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public RecyclerViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view, parent,
                false);
        RecyclerViewViewHolder recyclerViewViewHolder = new RecyclerViewViewHolder(view);
        return recyclerViewViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewViewHolder holder, int position) {
        cursor.moveToPosition(position);
        holder.bindCursor(cursor);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }
}
