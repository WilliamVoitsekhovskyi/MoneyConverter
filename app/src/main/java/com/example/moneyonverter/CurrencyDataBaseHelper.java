package com.example.moneyonverter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CurrencyDataBaseHelper extends SQLiteOpenHelper {
    private static final String DB_Name = "CurrencyRateDB";
    private static final String TABLE_NAME = "Currency_Rate_table";
    private static final int DB_Version = 1;
    private static final String COL1_ID = "ID";
    private static final String COL2_CURRENCY_NAME = "CURRENCY_NAME";
    private static final String COL3_RATE = "RATE";
    private static final String COL4_TIME = "DATE";


    public CurrencyDataBaseHelper(@Nullable Context context, @Nullable String name, int version) {
        super(context, DB_Name, null, DB_Version);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + "("
                + COL1_ID + " INTEGER PRIMARY KEY,"
                + COL2_CURRENCY_NAME + " TEXT,"
                + COL3_RATE + " REAL,"
                + COL4_TIME + " NUMERIC" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Currency_Rate_table");
        onCreate(sqLiteDatabase);
    }
}
