package com.deam.gota.dataBases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLOHelperClients extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "clientes.db";
    public static final String TABLA_CLIENTS = "t_clientes";

    public SQLOHelperClients(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase dataBase) {
        dataBase.execSQL("CREATE TABLE " + TABLA_CLIENTS + "(" +
                "id Integer PRIMARY KEY AUTOINCREMENT,"+
                "name text NOT NULL,"+
                "lastName text NOT NULL,"+
                "addressHome text NOT NULL,"+
                "addressJob text NOT NULL," +
                "phoneNumber text NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLA_CLIENTS);
        onCreate(db);
    }
}
