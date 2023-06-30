package com.deam.gota.model.expenses;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.deam.gota.R;
import com.deam.gota.dataBases.DbExpenses;
import com.deam.gota.pojos.Expenses;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditExpense extends AppCompatActivity {

    private EditText amount, date, comment;
    private FloatingActionButton edit, delete;
    private int id;
    private DbExpenses dbExpenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);

        amount = findViewById(R.id.amountEditExpense);
        date = findViewById(R.id.dateEditExpense);
        date.setInputType(InputType.TYPE_NULL);
        comment = findViewById(R.id.commentEditExpense);

        edit = findViewById(R.id.editButtonExpense);
        delete = findViewById(R.id.deleteButtonExpense);

        dbExpenses = new DbExpenses(this);

        id = getIntent().getIntExtra("ID", -1);
        if (id == -1) {
            Toast.makeText(this, "Error: No expense ID found.", Toast.LENGTH_SHORT).show();
            finish();
        }

        Expenses expenses = dbExpenses.showExpense(id);

        amount.setText(String.valueOf(expenses.getExpense()));
        date.setText(expenses.getDate());
        comment.setText(expenses.getComent());

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editExpense();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog();
            }
        });
    }

    private void showDatePickerDialog() {
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }

    private void editExpense() {
        String amountValue = amount.getText().toString().trim();
        String dateValue = date.getText().toString().trim();
        String commentValue = comment.getText().toString().trim();

        if (!amountValue.isEmpty() && !dateValue.isEmpty() && !commentValue.isEmpty()) {
            int amountInt = Integer.parseInt(amountValue);
            boolean correct = dbExpenses.editExpense(id, amountInt, dateValue, commentValue);

            if (correct) {
                Toast.makeText(EditExpense.this, "REGISTRO EDITADO", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(EditExpense.this, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(EditExpense.this, "LLENE TODO LOS ESPACIOS", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿DESEA ELIMINAR ESTE GASTO?")
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteExpense();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing, just dismiss the dialog
                    }
                })
                .show();
    }

    private void deleteExpense() {
        if (dbExpenses.deleteExpense(id)) {
            Toast.makeText(EditExpense.this, "GASTO ELIMINADO", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(EditExpense.this, "ERROR AL ELIMINAR EL GASTO", Toast.LENGTH_SHORT).show();
        }
    }
}

