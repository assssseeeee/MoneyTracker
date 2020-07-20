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

import com.example.moneytracker.DatePickerDialogFragments;
import com.example.moneytracker.ExpensesCursorAdapter;
import com.example.moneytracker.R;
import com.example.moneytracker.data.MoneyTrackerContract.AddingExpenses;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PurchaseHistoryActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,
        View.OnClickListener, DatePickerDialogFragments.DatePickerDialogListener {
    private ExpensesCursorAdapter expensesCursorAdapter;
    private static String selectedDate;
    private ListView productHistoryListView;
    private Button buttonChooseDate;
    private static final int PRODUCT_LOADER_HISTORY = 333;
    private static int selectedMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_history);

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd ww.D.FF HH.mm");
        selectedDate = dateFormat.format(date);
        selectedMenuItem = 1;

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
        String column = AddingExpenses.COLUMN_DATE_REGISTERED;

        if (TextUtils.isEmpty(selectedDate)) {
            selection = null;
        } else {
            if (selectedMenuItem == 1) {
                selection = column + " LIKE '" + selectedDate + "%'";
            } else if (selectedMenuItem == 2) {
                selection = column + " LIKE '2020.07%'";
            }
//            else if (selectedMenuItem == 3) {
//
//            } else if (selectedMenuItem == 4) {
//
//            }
            else if (selectedMenuItem == 5) {
                selection = null;
            } else {
                selection = column + " = ?";
            }
        }

        CursorLoader cursorLoader = new CursorLoader(this, AddingExpenses.CONTENT_URI, projection, selection, null, null);
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
