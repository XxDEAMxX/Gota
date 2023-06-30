package com.deam.gota.model.expenses;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.deam.gota.R;
import com.deam.gota.dataBases.DbExpenses;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Expenses extends AppCompatActivity {

    private EditText amount, date, comment;
    private Button add;
    private DbExpenses dbExpenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        amount = findViewById(R.id.amountExpense);
        date = findViewById(R.id.dateExpense);
        date.setInputType(InputType.TYPE_NULL);
        comment = findViewById(R.id.commentExpense);

        add = findViewById(R.id.addExpense);

        dbExpenses = new DbExpenses(this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExpense();
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

    }

    private void addExpense() {
        String amountText = amount.getText().toString().trim();
        String dateText = date.getText().toString().trim();
        String commentText = comment.getText().toString().trim();

        if (!TextUtils.isEmpty(amountText) && !TextUtils.isEmpty(dateText) && !TextUtils.isEmpty(commentText)) {
            int amountValue = Integer.parseInt(amountText);
            long id = dbExpenses.insertExpense(amountValue, dateText, commentText);

            if (id > 0) {
                Toast.makeText(this, "REGISTRO GUARDADO", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "LLENE TODOS LOS ESPACIOS", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String formattedDate = formatDate(year, month, dayOfMonth);
                date.setText(formattedDate);
            }
        }, year, month, dayOfMonth);
        datePickerDialog.show();
    }

    private String formatDate(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(calendar.getTime());
    }
}
