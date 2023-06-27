package com.deam.gota.model.loans;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.deam.gota.R;
import com.deam.gota.dataBases.DbPayments;
import com.deam.gota.model.expenses.EditExpense;
import com.deam.gota.pojos.Payments;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class EditPayment extends AppCompatActivity {

    private EditText amount, date;
    private FloatingActionButton edit, delete;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_payment);

        amount = findViewById(R.id.amountEdit);
        date = findViewById(R.id.dateEdit);

        edit = findViewById(R.id.editButton);
        delete = findViewById(R.id.deleteButtonPayment);


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

        DbPayments dbPayments = new DbPayments(EditPayment.this);
        Payments payments = dbPayments.showPayment(id);

        amount.setText(payments.getAmount()+"");
        date.setText(payments.getDate());

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int anio = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH)+1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EditPayment.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String fecha = dayOfMonth + "/" + month + "/" + year;
                        date.setText(fecha);
                    }
                }, anio, month, day);
                datePickerDialog.show();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!date.getText().toString().isEmpty() && !amount.getText().toString().isEmpty()) {
                    boolean correct = dbPayments.editPayments(id, date.getText().toString(),
                            Integer.parseInt(amount.getText().toString()));
                    if (correct){
                        Toast.makeText(EditPayment.this, "REGISTRO EDITADO", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(EditPayment.this, "ERROR AL EDITAR EL REGISTRO", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(EditPayment.this, "LLENE TODOS LOS ESPACIOS", Toast.LENGTH_SHORT).show();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditPayment.this);
                builder.setMessage("¿DESEA ELIMINAR ESTE PAGO?").setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(dbPayments.deletePayment(id)){
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

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditPayment.this);
                builder.setMessage("¿DESEA ELIMINAR ESTE PAGO?").setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(dbPayments.deletePayment(id)){
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

    }
}