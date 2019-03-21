package com.softwareengineering;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHandler extends SQLiteOpenHelper {
    private static SQLiteHandler instance;

    private static final String DB_NAME = "software_engineering.db";
    private static final int DB_VERSION = 1;

    private SQLiteHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // Singleton instance of SQLiteHandler
    public static SQLiteHandler getInstance(Context context) {
        if (instance == null)
            instance = new SQLiteHandler(context);
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {}

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}
}
