package com.deam.gota.model.expenses;

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
import com.deam.gota.dataBases.DbClients;
import com.deam.gota.dataBases.DbExpenses;

import java.util.Calendar;

public class Expenses extends AppCompatActivity {

    private EditText amount, date, comment;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        amount = findViewById(R.id.amountExpense);
        date = findViewById(R.id.dateExpense);
        date.setInputType(InputType.TYPE_NULL);
        comment = findViewById(R.id.commentExpense);

        add = findViewById(R.id.addExpense);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate();
            }
        });

    }

    public void add(){

            if (!amount.getText().toString().isEmpty() && !date.getText().toString().isEmpty() &&
                    !comment.getText().toString().isEmpty()) {

                DbExpenses dbExpenses = new DbExpenses(this);
                long id = dbExpenses.insertExpense(
                        Integer.parseInt(amount.getText().toString()),
                        date.getText().toString(),
                        comment.getText().toString());

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

        public void setDate(){
            Calendar calendar = Calendar.getInstance();
            int anio = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    int monthA = month+1;
                    String fecha = dayOfMonth + "/" + monthA + "/" + year;
                    date.setText(fecha);
                }
            }, anio, month, day);
            datePickerDialog.show();
        }

    }
