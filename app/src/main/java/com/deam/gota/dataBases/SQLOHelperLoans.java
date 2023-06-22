package com.deam.gota.dataBases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLOHelperLoans extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "prestamos.db";
    public static final String TABLA_LOANS = "t_prestamos";

    public SQLOHelperLoans(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase dataBase) {
        dataBase.execSQL("CREATE TABLE " + TABLA_LOANS + "(" +
                "id Integer PRIMARY KEY AUTOINCREMENT,"+
                "idClient  text NOT NULL,"+
                "route Integer NOT NULL,"+
                "date text NOT NULL," +
                "quotas text NOT NULL," +
                "loan text NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLA_LOANS);
        onCreate(db);
    }
}
