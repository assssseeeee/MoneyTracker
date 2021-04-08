package com.example.moneytracker.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import com.example.moneytracker.DateHandler;
import com.example.moneytracker.DatePickerDialogFragments;
import com.example.moneytracker.ExpensesCursorAdapter;
import com.example.moneytracker.R;
import com.example.moneytracker.data.MoneyTrackerContract;
import com.example.moneytracker.data.MoneyTrackerContract.AddingExpenses;
import java.util.ArrayList;

public class PurchaseHistoryActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,
        View.OnClickListener, DatePickerDialogFragments.DatePickerDialogListener {
    private ExpensesCursorAdapter expensesCursorAdapter;
    private static String selectedDate;
    private ListView productHistoryListView;
    private Button buttonChooseDate;
    DateHandler dateHandler;
    private static final int PRODUCT_LOADER_HISTORY = 333;
    private static int selectedMenuItem = 1;
    private static int amountDays = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_history);

        dateHandler = new DateHandler();
        selectedDate = dateHandler.dateFormatYyyyMmDdHhMm();

        buttonChooseDate = findViewById(R.id.buttonChooseDate);
        buttonChooseDate.setOnClickListener(this);
        productHistoryListView = findViewById(R.id.productHistoryListView);

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
        String column = AddingExpenses.COLUMN_DATE_REGISTERED;
        String dbQuery = " LIKE '%' || ? || '%' OR ";
        String dbQueryEnd = " LIKE '%' || ? || '%'";
        String value;

        if (TextUtils.isEmpty(selectedDate)) {
            selection = null;
        } else {
            if (selectedMenuItem == 1) {
                selection = column + " LIKE '" + selectedDate.substring(0, MoneyTrackerContract.LIMIT_DATE_DAY) + "%'";
                selectionArgs = null;
            } else if (selectedMenuItem == 2) {
                selection = column + dbQuery
                        + column + dbQuery
                        + column + dbQuery
                        + column + dbQuery
                        + column + dbQuery
                        + column + dbQuery
                        + column + dbQueryEnd;

                amountDays = 7;
                ArrayList<String> stringArrayList = new ArrayList<>(dateHandler.dateTimeFormatter(selectedDate, amountDays));
                selectionArgs = new String[stringArrayList.size()];

                for (int i = 0; i < stringArrayList.size(); i++) {
                    int count = stringArrayList.get(i).length();
                    value = String.valueOf(stringArrayList.get(i));
                    selectionArgs[i] = value.substring(0, count - 6);
                }

            } else if (selectedMenuItem == 3) {
                selection = column + dbQuery
                        + column + dbQuery
                        + column + dbQuery
                        + column + dbQuery
                        + column + dbQuery
                        + column + dbQuery
                        + column + dbQuery
                        + column + dbQuery
                        + column + dbQuery
                        + column + dbQuery
                        + column + dbQuery
                        + column + dbQuery
                        + column + dbQuery
                        + column + dbQuery
                        + column + dbQuery
                        + column + dbQuery
                        + column + dbQuery
                        + column + dbQuery
                        + column + dbQuery
                        + column + dbQuery
                        + column + dbQuery
                        + column + dbQuery
                        + column + dbQuery
                        + column + dbQuery
                        + column + dbQuery
                        + column + dbQuery
                        + column + dbQuery
                        + column + dbQuery
                        + column + dbQuery
                        + column + dbQuery
                        + column + dbQueryEnd;

                amountDays = 30;
                ArrayList<String> stringArrayList = new ArrayList<>(dateHandler.dateTimeFormatter(selectedDate, amountDays));
                selectionArgs = new String[stringArrayList.size()];

                for (int i = 0; i < stringArrayList.size(); i++) {
                    int count = stringArrayList.get(i).length();
                    value = String.valueOf(stringArrayList.get(i));
                    selectionArgs[i] = value.substring(0, count - 6);
                }

            } else if (selectedMenuItem == 4) {
                selection = null;
                selectionArgs = null;
            } else if (selectedMenuItem == 5) {
                selection = null;
                selectionArgs = null;
            } else {
                selection = null;
                selectionArgs = null;
            }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonChooseDate:
                showDatePickerDialog();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_history_for_day:
                selectedMenuItem = 1;
                item.setChecked(true);
                getSupportLoaderManager().restartLoader(PRODUCT_LOADER_HISTORY, null, this);
                break;
            case R.id.menu_history_for_week:
                selectedMenuItem = 2;
                item.setChecked(true);
                getSupportLoaderManager().restartLoader(PRODUCT_LOADER_HISTORY, null, this);
                break;
            case R.id.menu_history_for_month:
                selectedMenuItem = 3;
                item.setChecked(true);
                getSupportLoaderManager().restartLoader(PRODUCT_LOADER_HISTORY, null, this);
                break;
            case R.id.menu_menu_history_for_year:
                selectedMenuItem = 4;
                item.setChecked(true);
                getSupportLoaderManager().restartLoader(PRODUCT_LOADER_HISTORY, null, this);
                break;
            case R.id.menu_menu_history_all:
                selectedMenuItem = 5;
                item.setChecked(true);
                getSupportLoaderManager().restartLoader(PRODUCT_LOADER_HISTORY, null, this);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerDialogFragments();
        newFragment.show(getSupportFragmentManager(), "missiles");
    }

    @Override
    public void onDialogPositiveClick(String dialogSelectedDate) {
        selectedDate = dialogSelectedDate;
        getSupportLoaderManager().restartLoader(PRODUCT_LOADER_HISTORY, null, PurchaseHistoryActivity.this);
    }
}
