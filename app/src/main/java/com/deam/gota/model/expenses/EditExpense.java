package com.deam.gota.model.expenses;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.deam.gota.R;
import com.deam.gota.dataBases.DbExpenses;
import com.deam.gota.pojos.Expenses;

import java.util.Calendar;

public class EditExpense extends AppCompatActivity {

    private EditText amount, date, comment;
    private Button edit;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);

        amount = findViewById(R.id.amountEditExpense);
        date = findViewById(R.id.dateEditExpense);
        comment = findViewById(R.id.commentEditExpense);

        edit = findViewById(R.id.editButtonExpense);


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

        DbExpenses dbExpenses = new DbExpenses(this);
        Expenses expenses = dbExpenses.showExpense(id);


        amount.setText(expenses.getExpense()+"");
        date.setText(expenses.getDate());
        comment.setText(expenses.getComent());

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int anio = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH)+1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EditExpense.this, new DatePickerDialog.OnDateSetListener() {
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
                if(!amount.getText().toString().isEmpty() && !date.getText().toString().isEmpty()
                        && !comment.getText().toString().isEmpty()){
                    boolean correct = dbExpenses.editExpense(id, Integer.parseInt(amount.getText().toString()),
                            date.getText().toString(), comment.getText().toString());

                    if(correct){
                        Toast.makeText(EditExpense.this, "REGISTRO EDITADO", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(EditExpense.this, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(EditExpense.this, "LLENE TODO LOS ESPACIOS", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}