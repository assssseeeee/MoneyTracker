package com.example.moneytracker;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.example.moneytracker.data.MoneyTrackerContract.AddingExpenses;

public class ExpensesCursorAdapter extends CursorAdapter {
    public ExpensesCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.expenses_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView itemNameTextView = view.findViewById(R.id.itemNameTextView);
        TextView itemPriceTextView = view.findViewById(R.id.itemPriceTextView);

        String itemName = cursor.getString(cursor.getColumnIndexOrThrow(AddingExpenses.COLUMN_PRODUCT_NAME));
        String itemPrice = cursor.getString(cursor.getColumnIndexOrThrow(AddingExpenses.COLUMN_PRODUCT_PRICE));

        itemNameTextView.setText(itemName);
        itemPriceTextView.setText(itemPrice);
    }
}
