package com.example.moneytracker.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.moneytracker.ExpensesCursorAdapter;
import com.example.moneytracker.R;
import com.example.moneytracker.data.MoneyTrackerContract.AddingExpenses;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PurchaseHistoryActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private CalendarView calendarView;
    private ExpensesCursorAdapter expensesCursorAdapter;
    private static String selectedDate;
    private ListView productHistoryListView;
    private static final int PRODUCT_LOADER_HISTORY = 333;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_history);

        calendarView = findViewById(R.id.calendarView);
        productHistoryListView = findViewById(R.id.productHistoryListView);

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        selectedDate = dateFormat.format(date);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = new StringBuilder().append(year).append(".0").append(month + 1)
                        .append(".").append(dayOfMonth).toString();
                Toast.makeText(PurchaseHistoryActivity.this, selectedDate, Toast.LENGTH_LONG).show();

                getSupportLoaderManager().restartLoader(PRODUCT_LOADER_HISTORY, null, PurchaseHistoryActivity.this);
            }
        });
        expensesCursorAdapter = new ExpensesCursorAdapter(this, null, false);
        productHistoryListView.setAdapter(expensesCursorAdapter);
        productHistoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PurchaseHistoryActivity.this, ChangeProductActivity.class);
                Uri currentProductUri = ContentUris.withAppendedId(AddingExpenses.CONTENT_URI, id);
                intent.setData(currentProductUri);
                startActivity(intent);
            }
        });
        getSupportLoaderManager().initLoader(PRODUCT_LOADER_HISTORY, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {
                AddingExpenses._ID,
                AddingExpenses.COLUMN_PRODUCT_NAME,
                AddingExpenses.COLUMN_PRODUCT_PRICE,
                AddingExpenses.COLUMN_PRICE_SIGN,
                AddingExpenses.COLUMN_PRODUCT_CATEGORY,
                AddingExpenses.COLUMN_DATE_REGISTERED
        };

        String selection;
        String[] selectionArgs = {""};

        if (TextUtils.isEmpty(selectedDate)) {
            selection = null;
            selectionArgs[0] = "";
        } else {
            selection = AddingExpenses.COLUMN_DATE_REGISTERED + " = ?";
            selectionArgs[0] = selectedDate;
        }

        CursorLoader cursorLoader = new CursorLoader(this, AddingExpenses.CONTENT_URI, projection, selection, selectionArgs, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        expensesCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        expensesCursorAdapter.swapCursor(null);
    }
}
