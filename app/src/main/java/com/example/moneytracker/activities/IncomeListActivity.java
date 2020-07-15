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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.moneytracker.ExpensesCursorAdapter;
import com.example.moneytracker.R;
import com.example.moneytracker.data.MoneyTrackerContract.AddingExpenses;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IncomeListActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int PRODUCT_LOADER = 123;
    private EditText editTextProduct, editTextPrice;
    private Button buttonAddProduct;
    ListView listViewProduct;
    ExpensesCursorAdapter expensesCursorAdapter;
    Uri currentProductUri;
    private static String priceSign;
    private boolean state;
    private int productCategory;
    private String dateNow;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_list);

        productCategory = 1;

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        dateNow = dateFormat.format(date);
        Log.d("dateFormat", dateNow);

        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            state = arguments.getBoolean("state");
            if (state == true) {
                setTitle(R.string.label_income_list);
                priceSign = "+";
            } else {
                setTitle(R.string.label_expense_list);
                priceSign = "-";
            }
        } else {
            finish();
        }

        editTextProduct = findViewById(R.id.editTextProduct);
        editTextPrice = findViewById(R.id.editTextPrice);
        buttonAddProduct = findViewById(R.id.buttonAddProduct);
        listViewProduct = findViewById(R.id.productListView);

        expensesCursorAdapter = new ExpensesCursorAdapter(this, null, false);
        listViewProduct.setAdapter(expensesCursorAdapter);
        listViewProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(IncomeListActivity.this, ChangeProductActivity.class);
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
                editTextPrice.setText("");
                editTextProduct.setText("");
            }
        });
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

        String search;
        if(state == true){
            search = "+";
        }else{
            search = "-";
        }

        String selection = null;
        String[] selectionArgs = {""};
        if (TextUtils.isEmpty(search)) {
            selection = null;
            selectionArgs[0] = "";
        } else {
            selection = AddingExpenses.COLUMN_PRICE_SIGN + " = ?";
            selectionArgs[0] = search;
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
        contentValues.put(AddingExpenses.COLUMN_PRICE_SIGN, priceSign);
        contentValues.put(AddingExpenses.COLUMN_PRODUCT_CATEGORY, productCategory);
        contentValues.put(AddingExpenses.COLUMN_DATE_REGISTERED, dateNow);

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

