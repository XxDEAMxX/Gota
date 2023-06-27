package com.deam.gota.dataBases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.deam.gota.pojos.Loans;

import java.util.ArrayList;


public class DbLoans extends SQLOHelperLoans {

    Context context;


    public DbLoans(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertLoan(int idClient, int route, String date, String quotas, String loan){
        long id = 0;
        try {
            SQLOHelperLoans sqloHelperLoans = new SQLOHelperLoans(context);
            SQLiteDatabase db = sqloHelperLoans.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put("idClient", idClient);
            contentValues.put("route", route);
            contentValues.put("date", date);
            contentValues.put("quotas", quotas);
            contentValues.put("loan", loan);


            id = db.insert(TABLA_LOANS, null, contentValues);
            db.close();
        }catch (Exception e){
            e.toString();
        }
        return id;
    }


    public ArrayList<Loans> showLoans(){

        SQLOHelperLoans sqloHelperLoans = new SQLOHelperLoans(context);
        SQLiteDatabase db = sqloHelperLoans.getWritableDatabase();

        ArrayList<Loans> listLoans = new ArrayList<>();
        Loans loans = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLA_LOANS, null);


        if(cursor.moveToFirst()) {
            do {
                loans = new Loans(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5));

                listLoans.add(loans);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return listLoans;
    }

    public Loans showLoan(int id){

        SQLOHelperLoans sqloHelperLoans = new SQLOHelperLoans(context);
        SQLiteDatabase db = sqloHelperLoans.getWritableDatabase();

        Loans loans = null;
        Cursor cursor;

        cursor = db.rawQuery("SELECT * FROM " + TABLA_LOANS + " WHERE id = '" + id + "' LIMIT 1", null);

        if(cursor.moveToFirst()) {

            loans = loans = new Loans(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5));
        }
        cursor.close();
        return loans;
    }

    public boolean editLoan(int id, int idClient, int route, String date, String quotas, String loan){
        boolean correct = false;

        SQLOHelperLoans sqloHelperLoans = new SQLOHelperLoans(context);
        SQLiteDatabase db = sqloHelperLoans.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLA_LOANS + " SET idClient = '" + idClient + "', " +
                    "route = '" + route + "', " +
                    "date = '" + date + "', " +
                    "quotas = '" + quotas + "', " +
                    "loan = '" + loan + "' WHERE id = '" + id + "'");
            correct = true;

        }catch (Exception e){
            e.toString();
            correct = false;
        } finally {
            db.close();
        }
        return correct;
    }

    public boolean resetRoute(){
        boolean correct = false;

        SQLOHelperLoans sqloHelperLoans = new SQLOHelperLoans(context);
        SQLiteDatabase db = sqloHelperLoans.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLA_LOANS + " SET route = '" + 1000 + "'");
            correct = true;

        }catch (Exception e){
            e.toString();
            correct = false;
        } finally {
            db.close();
        }
        return correct;
    }

    public boolean deleteClient(int id){
        boolean correct = false;

        SQLOHelperLoans sqloHelperLoans = new SQLOHelperLoans(context);
        SQLiteDatabase db = sqloHelperLoans.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLA_LOANS + " WHERE id = '" + id + "'");
            correct = true;

        }catch (Exception e){
            e.toString();
            correct = false;
        } finally {
            db.close();
        }
        return correct;
    }

}
