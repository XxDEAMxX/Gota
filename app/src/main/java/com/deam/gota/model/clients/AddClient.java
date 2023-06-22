package com.deam.gota.model.clients;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.deam.gota.R;
import com.deam.gota.dataBases.DbClients;
import com.deam.gota.model.clients.ShowClients;
import com.deam.gota.model.loans.ShowClientToLoan;

public class AddClient extends AppCompatActivity {


    private EditText name;
    private EditText lastName;
    private EditText addressHome;
    private EditText addressJob;
    private EditText phoneNumber;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        name = (EditText)findViewById(R.id.showDataClientNameText);
        lastName = (EditText)findViewById(R.id.showDataLoanLastNameText);
        addressHome = (EditText)findViewById(R.id.showDataLoanAddressHomeText);
        addressJob = (EditText)findViewById(R.id.showDataLoanAddressJobText);
        phoneNumber = (EditText)findViewById(R.id.showDataLoanPhoneNumberText);
        //name.setInputType(InputType.TYPE_CLASS_TEXT);
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



    public void addClient(View view) {
        String nameS = name.getText().toString();
        String lastNameS = lastName.getText().toString();
        String addressHomeS = addressHome.getText().toString();
        String addressJobS = addressJob.getText().toString();
        String phoneNumberS = phoneNumber.getText().toString();
        if(phoneNumberS.length()==10) {

            if (!nameS.isEmpty() && !lastNameS.isEmpty() && !addressHomeS.isEmpty() &&
                    !addressJobS.isEmpty() && !phoneNumberS.isEmpty()) {

                DbClients dbClients = new DbClients(this);
                long id = dbClients.insertClient(nameS, lastNameS, addressHomeS, addressJobS, phoneNumberS);

                if (id > 0) {
                    Toast.makeText(this, "REGISTRO GUARDADO", Toast.LENGTH_SHORT).show();
                    name.setText("");
                    lastName.setText("");
                    addressHome.setText("");
                    addressJob.setText("");
                    phoneNumber.setText("");
                } else {
                    Toast.makeText(this, "ERROR AL GUARDAR REGISTRO ", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "LLENE TODOS LOS ESPACIOS", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "NUMERO DE TELOFONO NO VALIDO", Toast.LENGTH_SHORT).show();
        }
    }

}