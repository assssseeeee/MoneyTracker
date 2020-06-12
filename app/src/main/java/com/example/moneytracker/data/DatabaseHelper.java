package com.example.moneytracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(Context context) {
        super(context, MoneyTrackerContract.DATA_BASE_NAME, null, MoneyTrackerContract.DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_MEMBERS_TABLE = "CREATE TABLE " + MoneyTrackerContract.AddingExpenses.TABLE_NAME
                + "(" + MoneyTrackerContract.AddingExpenses._ID + " INTEGER PRIMARY KEY,"
                + MoneyTrackerContract.AddingExpenses.COLUMN_PRODUCT_NAME + " TEXT,"
                + MoneyTrackerContract.AddingExpenses.COLUMN_PRODUCT_PRICE + " TEXT" + ")";
        db.execSQL(CREATE_MEMBERS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MoneyTrackerContract.AddingExpenses.TABLE_NAME);
        onCreate(db);
    }
}
