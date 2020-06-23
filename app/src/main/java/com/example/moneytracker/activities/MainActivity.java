package com.example.moneytracker.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.moneytracker.ExpensesCursorAdapter;
import com.example.moneytracker.R;
import com.example.moneytracker.data.MoneyTrackerContract.AddingExpenses;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int PRODUCT_LOADER = 123;
    private EditText editTextProduct, editTextPrice;
    private Button buttonAddProduct;
    ListView listViewProduct;
    ExpensesCursorAdapter expensesCursorAdapter;
    Uri currentProductUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("XXX", "onCreate");
        setContentView(R.layout.activity_main);

        editTextProduct = findViewById(R.id.editTextProduct);
        editTextPrice = findViewById(R.id.editTextPrice);
        buttonAddProduct = findViewById(R.id.buttonAddProduct);
        listViewProduct = findViewById(R.id.productListView);

        expensesCursorAdapter = new ExpensesCursorAdapter(this, null, false);
        listViewProduct.setAdapter(expensesCursorAdapter);
        listViewProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ChangeProductActivity.class);
                Uri currentProductUri = ContentUris.withAppendedId(AddingExpenses.CONTENT_URI, id);
                intent.setData(currentProductUri);
                startActivity(intent);
            }
        });
        getSupportLoaderManager().initLoader(PRODUCT_LOADER, null, this);

        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProduct();
                editTextProduct.setText("");
                editTextPrice.setText("");
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("XXX", "onRestart");
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {
                AddingExpenses._ID,
                AddingExpenses.COLUMN_PRODUCT_NAME,
                AddingExpenses.COLUMN_PRODUCT_PRICE,
        };

        CursorLoader cursorLoader = new CursorLoader(this, AddingExpenses.CONTENT_URI, projection, null, null, null);
        Log.d("XXX", "loader main create");
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        Log.d("XXX", "loader main finished");
        expensesCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        Log.d("XXX", "loader main reset");
        expensesCursorAdapter.swapCursor(null);
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
            case R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveProduct() {
        String productName = editTextProduct.getText().toString().trim();
        String productPrice = editTextPrice.getText().toString().trim();

        if (TextUtils.isEmpty(productName)) {
            Toast.makeText(this, R.string.toast_enter_product_name, Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(productPrice)) {
            Toast.makeText(this, R.string.toast_enter_product_price, Toast.LENGTH_SHORT).show();
            return;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(AddingExpenses.COLUMN_PRODUCT_NAME, productName);
        contentValues.put(AddingExpenses.COLUMN_PRODUCT_PRICE, productPrice);

        if (currentProductUri == null) {
            ContentResolver contentResolver = getContentResolver();
            Uri uri = contentResolver.insert(AddingExpenses.CONTENT_URI, contentValues);

            if (uri == null) {
                Toast.makeText(this, "Insertion of data in the table failed for \" + uri",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, R.string.toast_product_saved,
                        Toast.LENGTH_LONG).show();
            }
        } else {
            int rowsChanget = getContentResolver().update(currentProductUri, contentValues, null, null);
            if (rowsChanget == 0) {
                Toast.makeText(this, "Saving of data in the table failed for \" + uri",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, R.string.toast_product_updated,
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}

