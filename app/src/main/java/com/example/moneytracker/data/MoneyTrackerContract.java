package com.example.moneytracker.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class MoneyTrackerContract {

    private MoneyTrackerContract() {
    }

    public static final int DATA_BASE_VERSION = 1;
    public static final String DATA_BASE_NAME = "moneydata.db";
    public static String DATA_BASE_PATH;
    public static final String SHEME = "content://";
    public static final String AUTHORITY = "com.example.moneytracker";
    public static final String PATH_EXPENSES = "expenses";
    public static final Uri BASE_CONTENT_URI = Uri.parse(SHEME + AUTHORITY);

    public static final class AddingExpenses implements BaseColumns {
        public static final String TABLE_NAME = "expenses";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_PRODUCT_NAME = "productName";
        public static final String COLUMN_PRODUCT_PRICE = "productPrice";
        public static final String COLUMN_PRODUCT_CATEGORY = "productCategory";
        public static final String COLUMN_DATE_REGISTRED = "dateRegistred";


        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_EXPENSES);
        public static final String CONTENT_MULTIPLE_ITEMS = ContentResolver.CURSOR_DIR_BASE_TYPE
                + "/" + AUTHORITY + "/" + PATH_EXPENSES;
        public static final String CONTENT_SINGLE_ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE
                + "/" + AUTHORITY + "/" + PATH_EXPENSES;


    }
}
