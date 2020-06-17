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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moneytracker.R;
import com.example.moneytracker.data.MoneyTrackerContract.AddingExpenses;

public class ProductChangeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener {
    private static final int EDIT_PRODUCT_LOADER = 111;
    EditText changeProductNameEditText;
    EditText changeProductPriceEditText;
    Button saveProductButton;
    Button deleteProductButton;
    Uri currentProductUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_change);

        Intent intent = getIntent();
        currentProductUri = intent.getData();
        if (currentProductUri == null) {
            setTitle(R.string.title_add_product);
        } else {
            setTitle(R.string.title_edit_product);
            getSupportLoaderManager().initLoader(EDIT_PRODUCT_LOADER, null, this);
        }

        changeProductNameEditText = findViewById(R.id.changeProductNameEditText);
        changeProductPriceEditText = findViewById(R.id.changeProductPriceEditText);
        saveProductButton = findViewById(R.id.saveProductButton);
        deleteProductButton = findViewById(R.id.deleteProductButton);

        saveProductButton.setOnClickListener(this);
        deleteProductButton.setOnClickListener(this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] progection = {AddingExpenses._ID,
                AddingExpenses.COLUMN_PRODUCT_NAME,
                AddingExpenses.COLUMN_PRODUCT_PRICE};
        return new CursorLoader(this, currentProductUri, progection,
                null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (data.moveToFirst()) {
            int productNameColumnIndex = data.getColumnIndex(AddingExpenses.COLUMN_PRODUCT_NAME);
            int productPriceColumnIndex = data.getColumnIndex(AddingExpenses.COLUMN_PRODUCT_PRICE);

            String productName = data.getString(productNameColumnIndex);
            String productPrice = data.getString(productPriceColumnIndex);
            changeProductNameEditText.setText(productName);
            changeProductPriceEditText.setText(productPrice);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {}

    private void saveProduct() {
        String productName = changeProductNameEditText.getText().toString().trim();
        String productPrice = changeProductPriceEditText.getText().toString().trim();

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
                finish();
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
        switch (v.getId()){
            case R.id.saveProductButton:
                saveProduct();
                break;
            case R.id.deleteProductButton:
                deleteProduct();
                break;
        }
    }
}
