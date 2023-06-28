package com.deam.gota.model.loans;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.deam.gota.R;
import com.deam.gota.dataBases.DbLoans;
import com.deam.gota.pojos.Loans;

import java.util.Calendar;

public class EditLoan extends AppCompatActivity {

    private EditText date, quotas, loan;
    private Button edit;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_loan);

        date = findViewById(R.id.editTextDate);
        quotas = findViewById(R.id.editTextQuotas);
        loan = findViewById(R.id.editTextLoan);

        edit = findViewById(R.id.buttonEditLoan);

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

        DbLoans dbLoans = new DbLoans(this);

        Loans loans = dbLoans.showLoan(id);

        date.setText(loans.getDate());
        quotas.setText(loans.getQuotas()+"");
        loan.setText(loans.getLoan()+"");


        date.setInputType(InputType.TYPE_NULL);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int anio = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EditLoan.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        int monthCorrect = month+1;
                        String fecha = dayOfMonth + "/" + monthCorrect + "/" + year;
                        date.setText(fecha);
                    }
                }, anio, month, day);
                datePickerDialog.show();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean correct = dbLoans.editLoan(id, loans.getIdClient(), loans.getRoute(), date.getText().toString(),
                        quotas.getText().toString(),loan.getText().toString());
                if(correct){
                    Toast.makeText(EditLoan.this, "REGISTRO EDITADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(EditLoan.this, "ERROR AL EDITAR REGISTRO", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}