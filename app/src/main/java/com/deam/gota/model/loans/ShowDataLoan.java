package com.deam.gota.model.loans;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.deam.gota.R;
import com.deam.gota.dataBases.DbClients;
import com.deam.gota.dataBases.DbLoans;
import com.deam.gota.dataBases.DbPayments;
import com.deam.gota.pojos.Clients;
import com.deam.gota.pojos.Loans;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class ShowDataLoan extends AppCompatActivity {
    private EditText name;
    private EditText lastName;
    private EditText addressHome;
    private EditText addressJob;
    private EditText phoneNumber;
    private EditText date;
    private EditText quotas;
    private EditText loan;
    private FloatingActionButton fabEdit, fabDelete, fabShowPayments;
    private EditText amountEditText, routeEditText, balanceFaulty, paymentCredits;
    private Button amountButton, routeButton;
    private DbPayments dbPayments;

    private Loans loans;
    private Clients client;
    private int id;
    private boolean correct = false;
    private int saldo;
    private boolean exist = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data_loan);
        saldo = 0;

        name            =   findViewById(R.id.showDataLoanNameText);
        lastName        =   findViewById(R.id.showDataLoanLastNameText);
        addressHome     =   findViewById(R.id.showDataLoanAddressHomeText);
        addressJob      =   findViewById(R.id.showDataLoanAddressJobText);
        phoneNumber     =   findViewById(R.id.showDataLoanPhoneNumberText);
        date            =   findViewById(R.id.showDataLoanDateText);
        quotas          =   findViewById(R.id.showDataLoanQuotasText);
        loan            =   findViewById(R.id.showDataLoanLoanText);
        amountEditText  =   findViewById(R.id.amountEditText);
        routeEditText   =   findViewById(R.id.routeEditText);
        balanceFaulty   =   findViewById(R.id.balanceFaulty);
        paymentCredits  =   findViewById(R.id.paymentCredits);

        amountButton    =   findViewById(R.id.amountButton);
        routeButton     =   findViewById(R.id.routeButton);

        fabEdit         =   findViewById(R.id.fabEditLoan);
        fabDelete       =   findViewById(R.id.fabDeleteLoan);
        fabShowPayments =   findViewById(R.id.fabShowPayments);

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

        DbLoans dbLoans = new DbLoans(ShowDataLoan.this);
        DbClients dbClients = new DbClients(ShowDataLoan.this);
        dbPayments = new DbPayments(ShowDataLoan.this);
        loans = dbLoans.showLoan(id);
        client = dbClients.showClient(loans.getIdClient());

        for(int i = 0; i < dbPayments.showPayments().size(); i++){
            if(loans.getId() == dbPayments.showPayments().get(i).getIdLoans()){
                saldo+=dbPayments.showPayments().get(i).getAmount();
            }
        }
        paymentCredits.setText(saldo+"");
        balanceFaulty.setText(Integer.parseInt(loans.getLoan())-saldo+"");


        if (loans != null) {
            name.setText(client.getName());
            lastName.setText(client.getLastName());
            addressHome.setText(client.getAddressHome());
            addressJob.setText(client.getAddressJob());
            phoneNumber.setText(client.getPhoneNumber());
            date.setText(loans.getDate());
            quotas.setText(loans.getQuotas());
            loan.setText(loans.getLoan());
            routeEditText.setHint(loans.getRoute()+"");
            balanceFaulty.setInputType(InputType.TYPE_NULL);
            name.setInputType(InputType.TYPE_NULL);
            lastName.setInputType(InputType.TYPE_NULL);
            addressHome.setInputType(InputType.TYPE_NULL);
            addressJob.setInputType(InputType.TYPE_NULL);
            phoneNumber.setInputType(InputType.TYPE_NULL);
            date.setInputType(InputType.TYPE_NULL);
            quotas.setInputType(InputType.TYPE_NULL);
            loan.setInputType(InputType.TYPE_NULL);
        } else {
            Toast.makeText(this, "CLIENTE NULL", Toast.LENGTH_SHORT).show();
        }

        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowDataLoan.this, EditLoan.class);
                intent.putExtra("ID", id);
                startActivity(intent);
                finish();
            }
        });

        for (int i = 0; i < dbPayments.showPayments().size(); i++){
            if(loans.getId() == dbPayments.showPayments().get(i).getIdLoans()){
                exist = true;
            }
        }

        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ShowDataLoan.this);
                if (!exist) {
                    builder.setMessage("¿DESEA ELIMINAR ESTE CLIENTE?").setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dbLoans.deleteClient(loans.getId());
                            finish();
                        }
                    }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
                }else {
                    Toast.makeText(ShowDataLoan.this, "PRESTAMO CON PAGOS REGISTRADOS", Toast.LENGTH_SHORT).show();
                }
            }
        });


        phoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + client.getPhoneNumber()));
                if (ActivityCompat.checkSelfPermission(ShowDataLoan.this, Manifest.permission.CALL_PHONE) !=
                        PackageManager.PERMISSION_GRANTED)
                    return;
                startActivity(intent);
            }

        });

        amountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPayment();
                exist = true;
                saldo = 0;
                for(int i = 0; i < dbPayments.showPayments().size(); i++){
                    if(loans.getId() == dbPayments.showPayments().get(i).getIdLoans()){
                        saldo+=dbPayments.showPayments().get(i).getAmount();
                    }
                }
                paymentCredits.setText(saldo+"");
                balanceFaulty.setText(Integer.parseInt(loans.getLoan())-saldo+"");
            }
        });

        fabShowPayments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPayments();
            }
        });

        routeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!routeEditText.getText().toString().equals("")){
                    correct = dbLoans.editLoan(
                            id,
                            dbLoans.showLoan(id).getIdClient(),
                            Integer.parseInt(routeEditText.getText().toString()),
                            date.getText().toString(),
                            quotas.getText().toString(),
                            loan.getText().toString()
                           );

                    if(correct){
                        Toast.makeText(ShowDataLoan.this, "REGISTRO MODIFICADO", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(ShowDataLoan.this, "ERROR AL MODIFICAR REGISTRO", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(ShowDataLoan.this, "DEBE LLENAR LOS CAMPOS", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void intentShow(){
        Intent intent = new Intent(this, ShowLoans.class);
        startActivity(intent);
    }

    public void showPayments(){
        Intent intent = new Intent(this, Payments.class);
        intent.putExtra("ID", loans.getId());
        startActivity(intent);
    }

    public void addPayment(){

        Calendar calendar = Calendar.getInstance();
        int anio = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String fecha = day + "/" + month + "/" + anio;
        int amount = Integer.parseInt(amountEditText.getText().toString())*1000;

        if(!amountEditText.getText().toString().isEmpty()){

            dbPayments = new DbPayments(this);
            long id = dbPayments.insertPayments(loans.getId(), fecha, amount);


            if (id > 0) {

                Toast.makeText(this, "REGISTRO GUARDADO", Toast.LENGTH_SHORT).show();
                amountEditText.setText("");

            } else {

                Toast.makeText(this, "ERROR AL GUARDAR REGISTRO ", Toast.LENGTH_SHORT).show();

            }
        } else {

            Toast.makeText(this, "LLENE TODOS LOS ESPACIOS", Toast.LENGTH_SHORT).show();

        }
    }

}