package com.deam.gota.model.loans;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.deam.gota.R;
import com.deam.gota.dataBases.DbClients;
import com.deam.gota.dataBases.DbLoans;
import com.deam.gota.model.MainActivity;
import com.deam.gota.pojos.Clients;

import java.util.Calendar;

public class AddLoan extends AppCompatActivity {

    private EditText date;
    private EditText quotas;
    private EditText loan;
    private TextView name;
    private TextView lastName;
    private TextView addressHome;
    private TextView addressJob;
    private TextView phoneNumber;
    private Button add;

    private DbClients dbClients;

    private Clients clients;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_loan);

        date = findViewById(R.id.editTextDate);
        date.setInputType(InputType.TYPE_NULL);
        quotas = findViewById(R.id.editTextQuotas);
        loan = findViewById(R.id.editTextLoan);

        name = findViewById(R.id.textViewNameToLoan);
        lastName = findViewById(R.id.textViewLastNameToLoan);
        addressHome = findViewById(R.id.textViewAddressHomeToLoan);
        addressJob = findViewById(R.id.textViewAddressJobToLoan);
        phoneNumber = findViewById(R.id.textViewPhoneNumberToLoan);

        add = findViewById(R.id.buttonAddLoan);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("ID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }

        dbClients = new DbClients(AddLoan.this);
        clients = dbClients.showClient(id);

        if (clients != null) {
            name.setText(clients.getName());
            lastName.setText(clients.getLastName());
            addressHome.setText(clients.getAddressHome());
            addressJob.setText(clients.getAddressJob());
            phoneNumber.setText(clients.getPhoneNumber());
        } else {
            Toast.makeText(this, "CLIENTE NULL", Toast.LENGTH_SHORT).show();
        }

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add(clients);
            }
        });

    }


    public void showDate() {

        Calendar calendar = Calendar.getInstance();
        int anio = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int monthCorrect = month+1;
                String fecha = dayOfMonth + "/" + monthCorrect + "/" + year;
                date.setText(fecha);
            }
        }, anio, month, day);
        datePickerDialog.show();
    }

    public void add(Clients clients){

        String dateS = date.getText().toString();
        String quotasS = quotas.getText().toString();
        Double loanT = (Integer.parseInt(loan.getText().toString())*0.2)+Integer.parseInt(loan.getText().toString());
        String loanS = loanT+"";

        if (!dateS.isEmpty() && !quotasS.isEmpty() && !loanS.isEmpty()) {

            DbLoans dbLoans = new DbLoans(this);
            long id = dbLoans.insertLoan(clients.getId(), 1000, dateS, quotasS,loanS);

            if (id > 0) {
                Toast.makeText(this, "REGISTRO GUARDADO", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "ERROR AL GUARDAR REGISTRO ", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "LLENE TODOS LOS ESPACIOS", Toast.LENGTH_SHORT).show();
        }
    }



}