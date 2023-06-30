package com.deam.gota.model.clients;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.deam.gota.R;
import com.deam.gota.dataBases.DbClients;
import com.deam.gota.dataBases.DbLoans;
import com.deam.gota.pojos.Clients;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ShowDataClient extends AppCompatActivity {

    private EditText nameEditText;
    private EditText lastNameEditText;
    private EditText addressHomeEditText;
    private EditText addressJobEditText;
    private EditText phoneNumberEditText;
    private FloatingActionButton fabEdit;
    private FloatingActionButton fabDelete;

    private Clients clients;
    private int id;
    private boolean exist = false;
    private DbLoans dbLoans;
    private DbClients dbClients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data_client);

        nameEditText = findViewById(R.id.showDataClientNameText);
        lastNameEditText = findViewById(R.id.showDataClientLastNameText);
        addressHomeEditText = findViewById(R.id.showDataClientAddressHomeText);
        addressJobEditText = findViewById(R.id.showDataClietnAddressJobText);
        phoneNumberEditText = findViewById(R.id.showDataClientPhoneNumberText);
        fabEdit = findViewById(R.id.fabEdit);
        fabDelete = findViewById(R.id.fabDelete);

        id = getIntent().getIntExtra("ID", -1);

        dbClients = new DbClients(ShowDataClient.this);
        clients = dbClients.showClient(id);

        if (clients != null) {
            nameEditText.setText(clients.getName());
            lastNameEditText.setText(clients.getLastName());
            addressHomeEditText.setText(clients.getAddressHome());
            addressJobEditText.setText(clients.getAddressJob());
            phoneNumberEditText.setText(clients.getPhoneNumber());
            setEditTextsInputType();
        } else {
            Toast.makeText(this, "CLIENTE NULL", Toast.LENGTH_SHORT).show();
        }

        fabEdit.setOnClickListener(v -> {
            Intent intent = new Intent(ShowDataClient.this, EditClient.class);
            intent.putExtra("ID", id);
            startActivity(intent);
            finish();
        });

        dbLoans = new DbLoans(this);

        for (int i = 0; i < dbLoans.showLoans().size(); i++) {
            if (clients.getId() == dbLoans.showLoans().get(i).getIdClient()) {
                exist = true;
                break;
            }
        }

        fabDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(ShowDataClient.this);
            if (!exist) {
                builder.setMessage("¿DESEA ELIMINAR ESTE CLIENTE?")
                        .setPositiveButton("SI", (dialog, which) -> {
                            if (dbClients.deleteClient(clients.getId())) {
                                intentShow();
                                finish();
                            }
                        })
                        .setNegativeButton("NO", (dialog, which) -> {
                            // Do nothing or handle cancel
                        })
                        .show();
            } else {
                Toast.makeText(ShowDataClient.this, "CLIENTE YA TIENE PRESTAMOS REGISTRADOS", Toast.LENGTH_SHORT).show();
            }
        });

        phoneNumberEditText.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + clients.getPhoneNumber()));
            if (ActivityCompat.checkSelfPermission(ShowDataClient.this, Manifest.permission.CALL_PHONE) !=
                    PackageManager.PERMISSION_GRANTED) {
                // Request CALL_PHONE permission if not granted
                ActivityCompat.requestPermissions(ShowDataClient.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return;
            }
            startActivity(intent);
        });
    }

    private void intentShow() {
        Intent intent = new Intent(this, ShowClients.class);
        startActivity(intent);
    }

    // Helper method to set the input type of EditText fields
    private void setEditTextsInputType() {
        int inputType = InputType.TYPE_NULL;
        nameEditText.setInputType(inputType);
        lastNameEditText.setInputType(inputType);
        addressHomeEditText.setInputType(inputType);
        addressJobEditText.setInputType(inputType);
        phoneNumberEditText.setInputType(inputType);
    }

    // Handle the permission request result (for CALL_PHONE)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, make the phone call
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + clients.getPhoneNumber()));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Permiso de llamada denegado", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Permiso de llamada denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
