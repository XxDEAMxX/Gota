package com.deam.gota.model.clients;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.deam.gota.R;
import com.deam.gota.dataBases.DbClients;
import com.deam.gota.pojos.Clients;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditClient extends AppCompatActivity {

    private EditText name;
    private EditText lastName;
    private EditText addressHome;
    private EditText addressJob;
    private EditText phoneNumber;
    private Button edit;

    private Clients clients;
    private String nameS;
    private int id = 0;
    private boolean correct = false;
    private FloatingActionButton fabEdit, fabDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_client);

        name        =   findViewById(R.id.showDataNameText);
        lastName    =   findViewById(R.id.showDataLastNameText);
        addressHome =   findViewById(R.id.showDataAddressHomeText);
        addressJob  =   findViewById(R.id.showDataAddressJobText);
        phoneNumber =   findViewById(R.id.showDataPhoneNumberText);
        edit        =   findViewById(R.id.edit);
        //fabEdit     =   findViewById(R.id.fabEdit);
        //fabEdit.setVisibility(View.INVISIBLE);
        //fabDelete   =   findViewById(R.id.fabDelete);
        //fabDelete.setVisibility(View.INVISIBLE);


        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                id = Integer.parseInt(null);
            }else {
                id = extras.getInt("ID");
            }
        }else {
            id = (int) savedInstanceState.getSerializable("ID");
        }

        DbClients dbClients = new DbClients(EditClient.this);
        clients = dbClients.showClient(id);

        if(clients != null){
            name.setText(clients.getName());
            lastName.setText(clients.getLastName());
            addressHome.setText(clients.getAddressHome());
            addressJob.setText(clients.getAddressJob());
            phoneNumber.setText(clients.getPhoneNumber());

        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!name.getText().toString().equals("") &&
                        !lastName.getText().toString().equals("") &&
                        !addressHome.getText().toString().equals("") &&
                        !addressJob.getText().toString().equals("") &&
                        !phoneNumber.getText().toString().equals("")){
                    correct = dbClients.editClient(
                            id,
                            name.getText().toString(),
                            lastName.getText().toString(),
                            addressHome.getText().toString(),
                            addressJob.getText().toString(),
                            phoneNumber.getText().toString());

                    if(correct){
                        Toast.makeText(EditClient.this, "REGISTRO MODIFICADO", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(EditClient.this, ShowClients.class);
                        intent.putExtra("ID", clients.getId());
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(EditClient.this, "ERROR AL MODIFICAR REGISTRO", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(EditClient.this, "DEBE LLENAR LOS CAMPOS", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}