package com.deam.gota.dataBases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.deam.gota.pojos.Clients;
import com.deam.gota.pojos.Expenses;

import java.util.ArrayList;


public class DbExpenses extends SQLOHelperExpenses {

    Context context;

    public DbExpenses(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertExpense(int amount, String date, String comment){
        long id = 0;
        try {
            SQLOHelperExpenses sqloHelperExpenses = new SQLOHelperExpenses(context);
            SQLiteDatabase db = sqloHelperExpenses.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put("amount", amount);
            contentValues.put("date", date);
            contentValues.put("comment", comment);

            id = db.insert(TABLA_EXPENSES, null, contentValues);
            db.close();
        }catch (Exception e){
            e.toString();
        }
        return id;
    }


    public ArrayList<Expenses> showExpenses(){

        SQLOHelperExpenses sqloHelperExpenses = new SQLOHelperExpenses(context);
        SQLiteDatabase db = sqloHelperExpenses.getWritableDatabase();

        ArrayList<Expenses> listExpenses = new ArrayList<>();
        Expenses expenses = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLA_EXPENSES, null);


        if(cursor.moveToFirst()) {
            do {
                expenses = new Expenses(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3));

                listExpenses.add(expenses);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return listExpenses;
    }

    public Expenses showExpense(int id){

        SQLOHelperExpenses sqloHelperExpenses = new SQLOHelperExpenses(context);
        SQLiteDatabase db = sqloHelperExpenses.getWritableDatabase();

        Expenses expenses = null;
        Cursor cursor;

        cursor = db.rawQuery("SELECT * FROM " + TABLA_EXPENSES + " WHERE id = '" + id + "' LIMIT 1", null);

        if(cursor.moveToFirst()) {

            expenses = new Expenses(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getString(3));

        }
        cursor.close();
        return expenses;
    }

    public boolean editExpense(int id, int amount, String date, String comment){
        boolean correct = false;

        SQLOHelperExpenses sqloHelperExpenses = new SQLOHelperExpenses(context);
        SQLiteDatabase db = sqloHelperExpenses.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLA_EXPENSES + " SET amount = '" + amount + "', " +
                    "date = '" + date + "', " +
                    "comment = '" + comment + "' WHERE id = '" + id + "'");
            correct = true;

        }catch (Exception e){
            e.toString();
            correct = false;
        } finally {
            db.close();
        }
        return correct;
    }

    public boolean deleteExpense(int id){
        boolean correct = false;

        SQLOHelperExpenses sqloHelperExpenses = new SQLOHelperExpenses(context);
        SQLiteDatabase db = sqloHelperExpenses.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLA_EXPENSES + " WHERE id = '" + id + "'");
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
