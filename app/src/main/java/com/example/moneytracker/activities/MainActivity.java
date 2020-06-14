package com.example.moneytracker.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import android.content.ContentResolver;
import android.content.ContentValues;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.moneytracker.ExpensesCursorAdapter;
import com.example.moneytracker.R;
import com.example.moneytracker.data.MoneyTrackerContract.AddingExpenses;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {
    EditText editTextProduct, editTextPrice;
    Button buttonAddProduct;
    ListView listViewProduct;
    ExpensesCursorAdapter expensesCursorAdapter;
    private static final int EDIT_PRODUCT_LOADER = 111;
    private static final int PRODUCT_LOADER = 123;
    Uri currentProductUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        editTextProduct = findViewById(R.id.editTextProduct);
        editTextPrice = findViewById(R.id.editTextPrice);
        buttonAddProduct = findViewById(R.id.buttonAddProduct);
        listViewProduct = findViewById(R.id.productListView);

        expensesCursorAdapter = new ExpensesCursorAdapter(this, null, false);
        listViewProduct.setAdapter(expensesCursorAdapter);
        listViewProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
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
            case R.id.menu_statistics_for_the_month:
                intent = new Intent("android.intent.action.STATISTICS_FOR_THE_MOUTH");
                startActivity(intent);
                break;
            case R.id.menu_statistics_for_the_year:
                intent = new Intent("android.intent.action.STATISTICS_FOR_THE_YEAR");
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {AddingExpenses._ID,
                AddingExpenses.COLUMN_PRODUCT_NAME,
                AddingExpenses.COLUMN_PRODUCT_PRICE};
        return new CursorLoader(this, currentProductUri, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if(data.moveToFirst()){
            int productNameColumnIndex = data.getColumnIndex(AddingExpenses.COLUMN_PRODUCT_NAME);
            int productpriceColumnIndex = data.getColumnIndex(AddingExpenses.COLUMN_PRODUCT_PRICE);

            String productName = data.getString(productNameColumnIndex);
            String productprice = data.getString(productpriceColumnIndex);

            editTextProduct.setText(productName);
            editTextPrice.setText(productprice);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

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

