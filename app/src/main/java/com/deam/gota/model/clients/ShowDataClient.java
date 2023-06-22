package com.deam.gota.model.clients;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.deam.gota.R;
import com.deam.gota.dataBases.DbClients;
import com.deam.gota.pojos.Clients;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ShowDataClient extends AppCompatActivity {

    private EditText name;
    private EditText lastName;
    private EditText addressHome;
    private EditText addressJob;
    private EditText phoneNumber;
    private FloatingActionButton fabEdit, fabDelete;

    private Clients clients;
    private int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data_client);

        name        =   findViewById(R.id.showDataClientNameText);
        lastName    =   findViewById(R.id.showDataClientLastNameText);
        addressHome =   findViewById(R.id.showDataClientAddressHomeText);
        addressJob  =   findViewById(R.id.showDataClietnAddressJobText);
        phoneNumber =   findViewById(R.id.showDataClientPhoneNumberText);
        fabEdit     =   findViewById(R.id.fabEdit);
        fabDelete   =   findViewById(R.id.fabDelete);

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("ID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }

        DbClients dbClients = new DbClients(ShowDataClient.this);
        clients = dbClients.showClient(id);

        if(clients != null){
            name.setText(clients.getName());
            lastName.setText(clients.getLastName());
            addressHome.setText(clients.getAddressHome());
            addressJob.setText(clients.getAddressJob());
            phoneNumber.setText(clients.getPhoneNumber());
            name.setInputType(InputType.TYPE_NULL);
            lastName.setInputType(InputType.TYPE_NULL);
            addressHome.setInputType(InputType.TYPE_NULL);
            addressJob.setInputType(InputType.TYPE_NULL);
            phoneNumber.setInputType(InputType.TYPE_NULL);
        }else {
            Toast.makeText(this, "CLIENTE NULL", Toast.LENGTH_SHORT).show();
        }

        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowDataClient.this, EditClient.class);
                intent.putExtra("ID",id);
                startActivity(intent);
                finish();
            }
        });

        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ShowDataClient.this);
                builder.setMessage("¿DESEA ELIMINAR ESTE CLIENTE?").setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(dbClients.deleteClient(clients.getId())){
                            intentShow();
                            finish();
                        }
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        });


        phoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+clients.getPhoneNumber()));
                if(ActivityCompat.checkSelfPermission(ShowDataClient.this, Manifest.permission.CALL_PHONE)!=
                        PackageManager.PERMISSION_GRANTED)
                    return;
                startActivity(intent);

            }
        });

    }

    private void intentShow(){
        Intent intent = new Intent(this, ShowClients.class);
        startActivity(intent);
    }
}