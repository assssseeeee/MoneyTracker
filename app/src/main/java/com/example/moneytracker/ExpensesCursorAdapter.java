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
    public ExpensesCursorAdapter(Context context, Cursor cursor, boolean autoRequery) {
        super(context, cursor, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.expenses_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView itemNameTextView = view.findViewById(R.id.itemNameTextView);
        TextView itemPriceTextView = view.findViewById(R.id.itemPriceTextView);
        TextView itemDateRegisteredTextView = view.findViewById(R.id.itemDateTextView);

        String itemName = cursor.getString(cursor.getColumnIndexOrThrow(AddingExpenses.COLUMN_PRODUCT_NAME));
        String itemPrice = cursor.getString(cursor.getColumnIndexOrThrow(AddingExpenses.COLUMN_PRODUCT_PRICE));
        String itemPriceSign = cursor.getString(cursor.getColumnIndexOrThrow(AddingExpenses.COLUMN_PRICE_SIGN));
        String itemDateRegistered = cursor.getString(cursor.getColumnIndexOrThrow(AddingExpenses.COLUMN_DATE_REGISTERED));

        if(itemPriceSign.equals("-")){
            itemNameTextView.setBackgroundColor(itemNameTextView.getResources().getColor(R.color.lightRed));
            itemPriceTextView.setBackgroundColor(itemNameTextView.getResources().getColor(R.color.moderatelyRed));
        }else{
            itemNameTextView.setBackgroundColor(itemNameTextView.getResources().getColor(R.color.lightGreen));
            itemPriceTextView.setBackgroundColor(itemNameTextView.getResources().getColor(R.color.moderatelyGreen));
        }

        itemNameTextView.setText(itemName);
        itemPriceTextView.setText(itemPrice);
        itemDateRegisteredTextView.setText(itemDateRegistered);
    }
}
