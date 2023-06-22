package com.deam.gota.dataBases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLOHelperPayments extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "payments.db";
    public static final String TABLA_PAYMENTS = "t_payments";

    public SQLOHelperPayments(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase dataBase) {
        dataBase.execSQL("CREATE TABLE " + TABLA_PAYMENTS + "(" +
                "id Integer PRIMARY KEY AUTOINCREMENT,"+
                "idLoan int NOT NULL,"+
                "date text NOT NULL,"+
                "amount int NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLA_PAYMENTS);
        onCreate(db);
    }
}
