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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moneytracker.R;
import com.example.moneytracker.data.MoneyTrackerContract.AddingExpenses;

public class ChangeProductActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener {

    Uri currentProductUri;
    private static final int PRODUCT_LOADER = 123;
    private EditText editTextChangeProduct, editTextChangePrice;
    private Button buttonSaveChange, buttonDeleteChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_product);
        Intent intent = getIntent();
        currentProductUri = intent.getData();

        if (currentProductUri == null) {
            setTitle("Добавить запись");
            invalidateOptionsMenu();
        } else {
            setTitle("Изменить запись");
            getSupportLoaderManager().initLoader(PRODUCT_LOADER, null, this);
        }

        editTextChangeProduct = findViewById(R.id.editTextChangeProduct);
        editTextChangePrice = findViewById(R.id.editTextChangePrice);
        buttonSaveChange = findViewById(R.id.buttonSaveChange);
        buttonDeleteChange = findViewById(R.id.buttonDeleteChange);
        buttonSaveChange.setOnClickListener(this);
        buttonDeleteChange.setOnClickListener(this);
    }

    private void saveProduct() {
        String productNameChange = editTextChangeProduct.getText().toString().trim();
        String productPriceChange = editTextChangePrice.getText().toString().trim();

        if (TextUtils.isEmpty(productNameChange)) {
            Toast.makeText(this, R.string.toast_enter_product_name, Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(productPriceChange)) {
            Toast.makeText(this, R.string.toast_enter_product_price, Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(AddingExpenses.COLUMN_PRODUCT_NAME, productNameChange);
        contentValues.put(AddingExpenses.COLUMN_PRODUCT_PRICE, productPriceChange);

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
            int rowsChanged = getContentResolver().update(currentProductUri, contentValues, null, null);
            if (rowsChanged == 0) {
                Toast.makeText(this, "Saving of data in the table failed for \" + uri",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, R.string.toast_product_updated,
                        Toast.LENGTH_LONG).show();
            }

        }
    }

    private void deleteProduct() {
        if (currentProductUri != null) {
            int rowsDeleted = getContentResolver().delete(currentProductUri, null, null);
            if (rowsDeleted == 0) {
                Toast.makeText(this, R.string.toast_product_delete_failed,
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, R.string.toast_product_delete_succsess,
                        Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSaveChange:
                saveProduct();
                break;
            case R.id.buttonDeleteChange:
                deleteProduct();
                break;
        }
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {AddingExpenses._ID,
                AddingExpenses.COLUMN_PRODUCT_NAME,
                AddingExpenses.COLUMN_PRODUCT_PRICE
        };
        Log.d("XXX", "loader change create");
        return new CursorLoader(this, currentProductUri,
                projection, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (data.moveToFirst()) {
            int productNameColumnIndex = data.getColumnIndex(AddingExpenses.COLUMN_PRODUCT_NAME);
            int productPriceColumnIndex = data.getColumnIndex(AddingExpenses.COLUMN_PRODUCT_PRICE);
            String productName = data.getString(productNameColumnIndex);
            String productPrice = data.getString(productPriceColumnIndex);
            editTextChangeProduct.setText(productName);
            editTextChangePrice.setText(productPrice);
        }
        Log.d("XXX", "loader change finished");
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        Log.d("XXX", "loader change reset");
    }
}
