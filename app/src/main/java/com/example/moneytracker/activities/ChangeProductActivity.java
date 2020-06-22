package com.example.moneytracker.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moneytracker.R;
import com.example.moneytracker.data.MoneyTrackerContract.AddingExpenses;

import java.net.URI;

public class ChangeProductActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    Uri currentProductUri;
    EditText editTextChangeProduct, editTextChangePrice;
    Button buttonSaveChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_product);
        Intent intent = getIntent();

        editTextChangeProduct = findViewById(R.id.editTextChangeProduct);
        editTextChangePrice = findViewById(R.id.editTextChangePrice);
        buttonSaveChange = findViewById(R.id.buttonSaveChange);

        buttonSaveChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProduct();
            }
        });
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
        if (data.moveToFirst()) {
            int productNameColumnIndex = data.getColumnIndex(AddingExpenses.COLUMN_PRODUCT_NAME);
            int productPriceColumnIndex = data.getColumnIndex(AddingExpenses.COLUMN_PRODUCT_PRICE);
            String productName = data.getString(productNameColumnIndex);
            String productPrice = data.getString(productPriceColumnIndex);
            editTextChangeProduct.setText(productName);
            editTextChangePrice.setText(productPrice);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    private void saveProduct(){
        String productName = editTextChangeProduct.getText().toString().trim();
        String productPrice = editTextChangePrice.getText().toString().trim();

        if(TextUtils.isEmpty(productName)){
            Toast.makeText(this, R.string.toast_enter_product_name, Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(productPrice)){
            Toast.makeText(this, R.string.toast_enter_product_price, Toast.LENGTH_SHORT).show();
        }



    }

}
