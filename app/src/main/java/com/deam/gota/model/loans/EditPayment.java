package com.deam.gota.model.loans;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.deam.gota.R;
import com.deam.gota.dataBases.DbPayments;
import com.deam.gota.pojos.Payments;

import java.util.Calendar;

public class EditPayment extends AppCompatActivity {

    private EditText amount, date;
    private Button edit;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_payment);

        amount = findViewById(R.id.amountEdit);
        date = findViewById(R.id.dateEdit);

        edit = findViewById(R.id.editButton);


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

        amount.setText(payments.getAmount());
        date.setText(payments.getDate());

        Calendar calendar = Calendar.getInstance();
        int anio = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String fecha = dayOfMonth + "/" + month + "/" + year;
                date.setText(fecha);
            }
        }, anio, month, day);
        datePickerDialog.show();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!date.getText().toString().isEmpty() && !amount.getText().toString().isEmpty()) {
                    dbPayments.editPayments(id, date.getText().toString(), Integer.parseInt(amount.getText().toString()));
                }else {
                    Toast.makeText(EditPayment.this, "LLENE TODOS LOS ESPACIOS", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}