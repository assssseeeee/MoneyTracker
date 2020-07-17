package com.example.moneytracker.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.app.Dialog;
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
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.moneytracker.ExpensesCursorAdapter;
import com.example.moneytracker.R;
import com.example.moneytracker.data.MoneyTrackerContract.AddingExpenses;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PurchaseHistoryActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener, DatePickerDialogFragments.DataPickerDialogListener{
    private ExpensesCursorAdapter expensesCursorAdapter;
    private static String selectedDate;
    private ListView productHistoryListView;
    private Button buttonChooseDate;
    private static final int PRODUCT_LOADER_HISTORY = 333;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_history);


        buttonChooseDate = findViewById(R.id.buttonChooseDate);
        buttonChooseDate.setOnClickListener(this);
        productHistoryListView = findViewById(R.id.productHistoryListView);

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        selectedDate = dateFormat.format(date);

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

        //getSupportLoaderManager().restartLoader(PRODUCT_LOADER_HISTORY, null, PurchaseHistoryActivity.this);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
        switch (id){
            case R.id.menu_history_for_day:
                break;
            case R.id.menu_history_for_week:
                break;
            case R.id.menu_history_for_month:
                break;
            case R.id.menu_menu_history_for_year:
                break;
            case R.id.menu_menu_history_all:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {

    }

    public void showDatePickerDialog(){
        DialogFragment newFragment = new DatePickerDialogFragments();
        newFragment.show(getSupportFragmentManager(), "missiles");
    }
}
