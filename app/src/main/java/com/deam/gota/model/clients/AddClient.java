package com.deam.gota.model.clients;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.deam.gota.R;
import com.deam.gota.dataBases.DbClients;

public class AddClient extends AppCompatActivity {

    private EditText nameEditText;
    private EditText lastNameEditText;
    private EditText addressHomeEditText;
    private EditText addressJobEditText;
    private EditText phoneNumberEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);
        initializeViews();
    }

    private void initializeViews() {
        nameEditText = findViewById(R.id.showDataClientNameText);
        lastNameEditText = findViewById(R.id.showDataLoanLastNameText);
        addressHomeEditText = findViewById(R.id.showDataLoanAddressHomeText);
        addressJobEditText = findViewById(R.id.showDataLoanAddressJobText);
        phoneNumberEditText = findViewById(R.id.showDataLoanPhoneNumberText);
    }

    public void addClient(View view) {
        String name = nameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String addressHome = addressHomeEditText.getText().toString().trim();
        String addressJob = addressJobEditText.getText().toString().trim();
        String phoneNumber = phoneNumberEditText.getText().toString().trim();

        if (isValidPhoneNumber(phoneNumber)) {
            if (areAllFieldsFilled(name, lastName, addressHome, addressJob, phoneNumber)) {
                DbClients dbClients = new DbClients(this);
                long id = dbClients.insertClient(name, lastName, addressHome, addressJob, phoneNumber);

                if (id > 0) {
                    showToast("REGISTRO GUARDADO");
                    clearFields();
                    finish();
                } else {
                    showToast("ERROR AL GUARDAR REGISTRO");
                }
            } else {
                showToast("LLENE TODOS LOS ESPACIOS");
            }
        } else {
            showToast("NUMERO DE TELEFONO NO VALIDO");
        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.length() == 10;
    }

    private boolean areAllFieldsFilled(String name, String lastName, String addressHome, String addressJob, String phoneNumber) {
        return !name.isEmpty() && !lastName.isEmpty() && !addressHome.isEmpty() && !addressJob.isEmpty() && !phoneNumber.isEmpty();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void clearFields() {
        nameEditText.setText("");
        lastNameEditText.setText("");
        addressHomeEditText.setText("");
        addressJobEditText.setText("");
        phoneNumberEditText.setText("");
    }
}