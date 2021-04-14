package com.example.moneytracker.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.moneytracker.R;
import com.example.moneytracker.RecyclerViewAdapter;
import com.example.moneytracker.data.MoneyTrackerContract;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    private Button buttonIncomeList, buttonExpenseList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonIncomeList = findViewById(R.id.buttonIncomeList);
        buttonExpenseList = findViewById(R.id.buttonExpenseList);
        buttonIncomeList.setOnClickListener(this);
        buttonExpenseList.setOnClickListener(this);

        recyclerView = findViewById(R.layout.recycler_view);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, IncomeListActivity.class);
        switch (v.getId()) {
            case R.id.buttonIncomeList:
                intent.putExtra("state", true);
                startActivity(intent);
                break;
            case R.id.buttonExpenseList:
                intent.putExtra("state", false);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case R.id.menu_purchase_history:
                intent = new Intent("android.intent.action.PURCHASE_HISTORY");
                startActivity(intent);
                break;
            case R.id.menu_statistics:
                intent = new Intent("android.intent.action.STATISTICS");
                startActivity(intent);
                break;
            case R.id.menu_settings:
                intent = new Intent("android.intent.action.SETTINGS");
                startActivity(intent);
                break;
            case R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {
                MoneyTrackerContract.AddingExpenses._ID,
                MoneyTrackerContract.AddingExpenses.COLUMN_PRODUCT_NAME,
                MoneyTrackerContract.AddingExpenses.COLUMN_PRODUCT_PRICE,
                MoneyTrackerContract.AddingExpenses.COLUMN_PRICE_SIGN,
                MoneyTrackerContract.AddingExpenses.COLUMN_PRODUCT_CATEGORY,
                MoneyTrackerContract.AddingExpenses.COLUMN_DATE_REGISTERED
        };

        String selection = null;

        CursorLoader cursorLoader = new CursorLoader(this, MoneyTrackerContract.AddingExpenses.CONTENT_URI,
                projection, selection, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
