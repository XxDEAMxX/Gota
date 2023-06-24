package com.deam.gota.dataBases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.deam.gota.pojos.Payments;

import java.util.ArrayList;


public class DbPayments extends SQLOHelperPayments {

    Context context;


    public DbPayments(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertPayments(int idLoan, String date, int amount){
        long id = 0;
        try {
            SQLOHelperPayments sqloHelperPayments = new SQLOHelperPayments(context);
            SQLiteDatabase db = sqloHelperPayments.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put("idLoan", idLoan);
            contentValues.put("date", date);
            contentValues.put("amount", amount);

            id = db.insert(TABLA_PAYMENTS, null, contentValues);
            db.close();
        }catch (Exception e){
            e.toString();
        }
        return id;
    }


    public ArrayList<Payments> showPayments(){

        SQLOHelperPayments sqloHelperPayments = new SQLOHelperPayments(context);
        SQLiteDatabase db = sqloHelperPayments.getWritableDatabase();

        ArrayList<Payments> listPayment = new ArrayList<>();
        Payments payments;
        Cursor cursor;

        cursor = db.rawQuery("SELECT * FROM " + TABLA_PAYMENTS, null);


        if(cursor.moveToFirst()) {
            do {
                payments = new Payments(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getInt(3));

                listPayment.add(payments);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return listPayment;
    }

    public Payments showPayment(int id){

        SQLOHelperPayments sqloHelperPayments = new SQLOHelperPayments(context);
        SQLiteDatabase db = sqloHelperPayments.getWritableDatabase();

        Payments payments = null;
        Cursor cursor;

        cursor = db.rawQuery("SELECT * FROM " + TABLA_PAYMENTS + " WHERE id = '" + id + "' LIMIT 1", null);

        if(cursor.moveToFirst()) {

            payments = new Payments(
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getString(2),
                cursor.getInt(3)
            );

        }
        cursor.close();
        return payments;
    }

    public boolean editPayments(int id, String date, String quotas, String loan){
        boolean correct = false;

        SQLOHelperLoans sqloHelperLoans = new SQLOHelperLoans(context);
        SQLiteDatabase db = sqloHelperLoans.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLA_PAYMENTS + " SET date = '" + date + "', " +
                    "quotas = '" + quotas + "', " +
                    "loan = '" + loan + "'");
            correct = true;

        }catch (Exception e){
            e.toString();
            correct = false;
        } finally {
            db.close();
        }
        return correct;
    }

    public boolean deletePayment(int id){
        boolean correct = false;

        SQLOHelperLoans sqloHelperLoans = new SQLOHelperLoans(context);
        SQLiteDatabase db = sqloHelperLoans.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLA_PAYMENTS + " WHERE id = '" + id + "'");
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
