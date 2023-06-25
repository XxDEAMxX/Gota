package com.deam.gota.dataBases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLOHelperExpenses extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "expenses.db";
    public static final String TABLA_EXPENSES = "t_expenses";

    public SQLOHelperExpenses(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase dataBase) {
        dataBase.execSQL("CREATE TABLE " + TABLA_EXPENSES + "(" +
                "id Integer PRIMARY KEY AUTOINCREMENT,"+
                "amount Integer NOT NULL,"+
                "date text NOT NULL,"+
                "comment text NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLA_EXPENSES);
        onCreate(db);
    }
}
