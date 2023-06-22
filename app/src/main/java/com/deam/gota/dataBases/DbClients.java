package com.deam.gota.dataBases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.deam.gota.pojos.Clients;

import java.util.ArrayList;


public class DbClients extends SQLOHelperClients {

    Context context;


    public DbClients(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertClient(String name, String lastName, String addressHome, String addressJob, String phoneNumber){
        long id = 0;
        try {
            SQLOHelperClients adminSQLiteOpenHelper = new SQLOHelperClients(context);
            SQLiteDatabase db = adminSQLiteOpenHelper.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put("name", name);
            contentValues.put("lastName", lastName);
            contentValues.put("addressHome", addressHome);
            contentValues.put("addressJob", addressJob);
            contentValues.put("phoneNumber", phoneNumber);


            id = db.insert(TABLA_CLIENTS, null, contentValues);
            db.close();
        }catch (Exception e){
            e.toString();
        }
        return id;
    }


    public ArrayList<Clients> showClients(){

        SQLOHelperClients adminSQLiteOpenHelper = new SQLOHelperClients(context);
        SQLiteDatabase db = adminSQLiteOpenHelper.getWritableDatabase();

        ArrayList<Clients> listClients = new ArrayList<>();
        Clients clients = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLA_CLIENTS, null);


        if(cursor.moveToFirst()) {
            do {
                clients = new Clients(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5));

                listClients.add(clients);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return listClients;
    }

    public Clients showClient(int id){

        SQLOHelperClients sqloHelperClients = new SQLOHelperClients(context);
        SQLiteDatabase db = sqloHelperClients.getWritableDatabase();

        Clients clients = null;
        Cursor cursor;

        cursor = db.rawQuery("SELECT * FROM " + TABLA_CLIENTS + " WHERE id = '" + id + "' LIMIT 1", null);

        if(cursor.moveToFirst()) {

            clients = new Clients(cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5));

        }
        cursor.close();
        return clients;
    }

    public boolean editClient(int id, String name, String lastName, String addressHome, String addressJob, String phoneNumber){
        boolean correct = false;

        SQLOHelperClients adminSQLiteOpenHelper = new SQLOHelperClients(context);
        SQLiteDatabase db = adminSQLiteOpenHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLA_CLIENTS + " SET name = '" + name + "', " +
                    "lastName = '" + lastName + "', " +
                    "addressHome = '" + addressHome + "', " +
                    "addressJob = '" + addressJob + "', " +
                    "phoneNumber = '" + phoneNumber + "' WHERE id = '" + id + "'");
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

        SQLOHelperClients adminSQLiteOpenHelper = new SQLOHelperClients(context);
        SQLiteDatabase db = adminSQLiteOpenHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLA_CLIENTS + " WHERE id = '" + id + "'");
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
