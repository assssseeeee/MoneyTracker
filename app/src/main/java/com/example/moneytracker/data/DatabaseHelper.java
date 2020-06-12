package com.example.moneytracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.moneytracker.data.MoneyTrackerContract.AddingExpenses;



public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(Context context) {
        super(context, MoneyTrackerContract.DATA_BASE_NAME, null, MoneyTrackerContract.DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String CREATE_EXPENSES_TABLE = "CREATE TABLE " + AddingExpenses.TABLE_NAME
                + "(" + AddingExpenses._ID + " INTEGER PRIMARY KEY,"
                + AddingExpenses.COLUMN_PRODUCT_NAME + " TEXT,"
                + AddingExpenses.COLUMN_PRODUCT_PRICE + " TEXT" + ")";
        db.execSQL(CREATE_EXPENSES_TABLE);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + AddingExpenses.TABLE_NAME);
        onCreate(db);

    }
}
