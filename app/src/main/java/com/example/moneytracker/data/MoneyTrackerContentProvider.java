package com.example.moneytracker.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.moneytracker.data.MoneyTrackerContract.AddingExpenses;

public class MoneyTrackerContentProvider extends ContentProvider {

    DatabaseHelper databaseHelper;

    private static final int EXPENSES = 111;
    private static final int EXPENSES_ID = 222;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(MoneyTrackerContract.AUTHORITY, MoneyTrackerContract.PATH_EXPENSES, EXPENSES);
        uriMatcher.addURI(MoneyTrackerContract.AUTHORITY, MoneyTrackerContract.PATH_EXPENSES + "/#", EXPENSES_ID);
    }

    @Override
    public boolean onCreate() {
        databaseHelper = new DatabaseHelper(getContext());
        databaseHelper.loadDatabaseFromFile();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = databaseHelper.open();
        Cursor cursor;

        int match = uriMatcher.match(uri);
        switch (match) {
            case EXPENSES:
                cursor = db.query(AddingExpenses.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case EXPENSES_ID:
                selection = AddingExpenses._ID + " = ?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(AddingExpenses.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Can't query incorrect URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        int match = uriMatcher.match(uri);

        switch (match) {
            case EXPENSES:
                return AddingExpenses.CONTENT_MULTIPLE_ITEMS;
            case EXPENSES_ID:
                return AddingExpenses.CONTENT_SINGLE_ITEM;
            default:
                throw new IllegalArgumentException("Unknow URI " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        String productName = values.getAsString(AddingExpenses.COLUMN_PRODUCT_NAME);
        if (productName == null) {
            throw new IllegalArgumentException("You have to input name");
        }

        String productPrice = values.getAsString(AddingExpenses.COLUMN_PRODUCT_PRICE);
        if (productPrice == null) {
            throw new IllegalArgumentException("You have to input price");
        }

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);

        switch (match) {
            case EXPENSES:
                long id = db.insert(AddingExpenses.TABLE_NAME, null, values);
                if (id == -1) {
                    Log.i("insertMethod", "Insertion of data in the table failed for " + uri);
                    return null;
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);

            default:
                Toast.makeText(getContext(), "Uncorrect URI", Toast.LENGTH_LONG).show();
                throw new IllegalArgumentException("Can't query incorect URI " + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int rowsDeleted;
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);
        switch (match) {

            case EXPENSES:
                rowsDeleted = db.delete(AddingExpenses.TABLE_NAME, selection, selectionArgs);
                break;
            case EXPENSES_ID:
                selection = AddingExpenses._ID + " = ?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = db.delete(AddingExpenses.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Can't delete incorrect URI " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (values.containsKey(AddingExpenses.COLUMN_PRODUCT_NAME)) {
            String productName = values.getAsString(AddingExpenses.COLUMN_PRODUCT_NAME);
            if (productName == null) {
                throw new IllegalArgumentException("You have to input product name ");
            }
        }

        if (values.containsKey(AddingExpenses.COLUMN_PRODUCT_PRICE)) {
            String productPrice = values.getAsString(AddingExpenses.COLUMN_PRODUCT_PRICE);
            if (productPrice == null) {
                throw new IllegalArgumentException("You have to input product price");
            }
        }

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case EXPENSES:
                rowsUpdated = db.update(AddingExpenses.TABLE_NAME, values, selection, selectionArgs);
                break;
            case EXPENSES_ID:
                selection = AddingExpenses._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsUpdated = db.update(AddingExpenses.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Can't update nicorrect URI " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}
