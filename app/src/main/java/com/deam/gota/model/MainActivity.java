package com.deam.gota.model;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.deam.gota.R;
import com.deam.gota.model.clients.AddClient;
import com.deam.gota.model.clients.ShowClients;
import com.deam.gota.model.loans.ShowClientToLoan;
import com.deam.gota.model.loans.ShowLoans;

public class MainActivity extends AppCompatActivity {

    private int REQUEST_CODE = 200;

    public MainActivity(){
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        verificarPermisos();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.overflow, menu);
        return true;
    }


    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.addClient){

            Intent intent = new Intent(this, AddClient.class);
            startActivity(intent);
            return true;

        } else if (id == R.id.Clinets) {

            Intent intent = new Intent(this, ShowClients.class);
            startActivity(intent);
            return true;

        }else if(id == R.id.addLoan){

            Intent intent = new Intent(this, ShowClientToLoan.class);
            startActivity(intent);
            return true;

        }else
        return super.onOptionsItemSelected(item);
    }

    public void verificarPermisos(){
        int permiso = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.CALL_PHONE);

        if(permiso != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_CODE);
        }
    }


    public void addClientMenu(View view){
        Intent intent = new Intent(this, AddClient.class);
        startActivity(intent);
    }

    public void addLoanMenu(View view){
        Intent intent = new Intent(this, ShowClientToLoan.class);
        startActivity(intent);
    }

    public void showClientMenu(View view){
        Intent intent = new Intent(this, ShowClients.class);
        startActivity(intent);
    }

    public void showLoansMenu(View view){
        Intent intent = new Intent(this, ShowLoans.class);
        startActivity(intent);
    }

}