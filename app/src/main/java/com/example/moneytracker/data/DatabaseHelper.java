package com.example.moneytracker.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.moneytracker.data.MoneyTrackerContract.AddingExpenses;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    Context myContext;

    public DatabaseHelper(Context context) {
        super(context, MoneyTrackerContract.DATA_BASE_NAME, null, MoneyTrackerContract.DATA_BASE_VERSION);
        this.myContext = context;
        MoneyTrackerContract.DATA_BASE_PATH = context.getFilesDir().getPath() + MoneyTrackerContract.DATA_BASE_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EXPENSES_TABLE = "CREATE TABLE " + AddingExpenses.TABLE_NAME
                + "(" + AddingExpenses._ID + " INTEGER PRIMARY KEY,"
                + AddingExpenses.COLUMN_PRODUCT_NAME + " TEXT,"
                + AddingExpenses.COLUMN_PRODUCT_PRICE + " TEXT,"
                + AddingExpenses.COLUMN_PRODUCT_CATEGORY + " INTEGER,"
                + AddingExpenses.COLUMN_DATE_REGISTERED + " INTEGER"
                + ")";
        db.execSQL(CREATE_EXPENSES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + AddingExpenses.TABLE_NAME);
        onCreate(db);
    }

    public void loadDatabaseFromFile() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            File file = new File(MoneyTrackerContract.DATA_BASE_PATH);
            if (!file.exists()) {
                this.getReadableDatabase();
                inputStream = myContext.getAssets().open(MoneyTrackerContract.DATA_BASE_NAME);
                String outFileName = MoneyTrackerContract.DATA_BASE_PATH;
                outputStream = new FileOutputStream(outFileName);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.flush();
                outputStream.close();
                inputStream.close();
            }
        } catch (IOException ex) {
            Log.d("DatabaseHelper", ex.getMessage());
        }
    }

    public SQLiteDatabase open() throws SQLException {
        return SQLiteDatabase.openDatabase(MoneyTrackerContract.DATA_BASE_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }
}
