package com.deam.gota.model.clients;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.deam.gota.R;
import com.deam.gota.dataBases.DbClients;
import com.deam.gota.pojos.Clients;

public class EditClient extends AppCompatActivity {

    private EditText nameEditText;
    private EditText lastNameEditText;
    private EditText addressHomeEditText;
    private EditText addressJobEditText;
    private EditText phoneNumberEditText;
    private Button editButton;

    private Clients clients;
    private int clientId = 0;
    private boolean isCorrect = false;
    private DbClients dbClients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_client);

        nameEditText = findViewById(R.id.showDataNameText);
        lastNameEditText = findViewById(R.id.showDataLastNameText);
        addressHomeEditText = findViewById(R.id.showDataAddressHomeText);
        addressJobEditText = findViewById(R.id.showDataAddressJobText);
        phoneNumberEditText = findViewById(R.id.showDataPhoneNumberText);
        editButton = findViewById(R.id.edit);

        clientId = getIntent().getIntExtra("ID", 0);
        dbClients = new DbClients(EditClient.this);
        clients = dbClients.showClient(clientId);

        if (clients != null) {
            nameEditText.setText(clients.getName());
            lastNameEditText.setText(clients.getLastName());
            addressHomeEditText.setText(clients.getAddressHome());
            addressJobEditText.setText(clients.getAddressJob());
            phoneNumberEditText.setText(clients.getPhoneNumber());
        }

        editButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();
            String lastName = lastNameEditText.getText().toString().trim();
            String addressHome = addressHomeEditText.getText().toString().trim();
            String addressJob = addressJobEditText.getText().toString().trim();
            String phoneNumber = phoneNumberEditText.getText().toString().trim();

            if (!name.isEmpty() && !lastName.isEmpty() && !addressHome.isEmpty()
                    && !addressJob.isEmpty() && !phoneNumber.isEmpty()) {

                isCorrect = dbClients.editClient(clientId, name, lastName, addressHome, addressJob, phoneNumber);

                if (isCorrect) {
                    Toast.makeText(EditClient.this, "REGISTRO MODIFICADO", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditClient.this, ShowClients.class);
                    intent.putExtra("ID", clients.getId());
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(EditClient.this, "ERROR AL MODIFICAR REGISTRO", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(EditClient.this, "DEBE LLENAR TODOS LOS CAMPOS", Toast.LENGTH_SHORT).show();
            }
        });
    }
}