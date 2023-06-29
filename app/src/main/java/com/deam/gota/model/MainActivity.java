package com.deam.gota.model;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.deam.gota.R;
import com.deam.gota.dataBases.DbPayments;
import com.deam.gota.model.loans.ShowLoansPayDay;
import com.deam.gota.model.loans.ShowPayments;
import com.deam.gota.adapters.ListLoanAdapter;
import com.deam.gota.dataBases.DbClients;
import com.deam.gota.dataBases.DbLoans;
import com.deam.gota.model.clients.ShowClients;
import com.deam.gota.model.expenses.ShowExpenses;
import com.deam.gota.model.loans.ShowClientToLoan;
import com.deam.gota.pojos.Loans;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private int REQUEST_CODE = 200;

    private FloatingActionButton fabAddLoan, fabShowClients, fabReset, fabExpenses, fabShowPaymentsDay, fabPayDay;
    private SearchView search;
    private RecyclerView loans;
    private ArrayList<Loans> listLoans;
    private SwipeRefreshLayout swipeRefreshLayout;
    ListLoanAdapter adapter;
    private DbLoans dbLoans;
    private DbClients dbClients;
    private DbPayments dbPayments;

    public MainActivity(){
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verificarPermisos();

        swipeRefreshLayout = findViewById(R.id.refresh);

        fabAddLoan = findViewById(R.id.fabAddLoan);
        fabShowClients = findViewById(R.id.fabShowClients);
        fabReset = findViewById(R.id.reset);
        fabExpenses = findViewById(R.id.fabExpenses);
        fabShowPaymentsDay = findViewById(R.id.fabShowPaymentsDay);
        fabPayDay = findViewById(R.id.fabPayDay);

        search  = findViewById(R.id.searchLoan);
        loans = findViewById(R.id.list);

        fabShowPaymentsDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowPayments.class);
                startActivity(intent);
            }
        });

        loans.setLayoutManager(new LinearLayoutManager(this));

        dbLoans = new DbLoans(this);
        dbClients = new DbClients(this);
        dbPayments = new DbPayments(this);

        listLoans = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String fecha = day + "/" + month + "/" + year;

        Toast.makeText(MainActivity.this, fecha, Toast.LENGTH_SHORT).show();

        for (int i = 0; i < dbLoans.showLoans().size(); i++) {
            boolean hasPaymentOnDate = false;

            for (int j = 0; j < dbPayments.showPayments().size(); j++) {
                if (dbLoans.showLoans().get(i).getId() == dbPayments.showPayments().get(j).getIdLoans()) {
                    if (dbPayments.showPayments().get(j).getDate().equals(fecha)) {
                        hasPaymentOnDate = true;
                        break;
                    }
                }
            }

            if (!hasPaymentOnDate) {
                listLoans.add(dbLoans.showLoans().get(i));
            }
        }

        adapter = new ListLoanAdapter(listLoans, dbClients.showClients());

        loans.setAdapter(adapter);


        fabAddLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLoanMenu();
            }
        });

        fabShowClients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showClientMenu();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);

                listLoans = new ArrayList<>();

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                String fecha = day + "/" + month + "/" + year;

                Toast.makeText(MainActivity.this, fecha, Toast.LENGTH_SHORT).show();

                for (int i = 0; i < dbLoans.showLoans().size(); i++) {
                    boolean hasPaymentOnDate = false;

                    for (int j = 0; j < dbPayments.showPayments().size(); j++) {
                        if (dbLoans.showLoans().get(i).getId() == dbPayments.showPayments().get(j).getIdLoans()) {
                            if (dbPayments.showPayments().get(j).getDate().equals(fecha)) {
                                hasPaymentOnDate = true;
                                break;
                            }
                        }
                    }

                    if (!hasPaymentOnDate) {
                        listLoans.add(dbLoans.showLoans().get(i));
                    }
                }

                Toast.makeText(MainActivity.this, listLoans.size()+"", Toast.LENGTH_SHORT).show();

                ListLoanAdapter adapter = new ListLoanAdapter(listLoans, dbClients.showClients());

                loans.setAdapter(adapter);
            }
        });

        search.setOnQueryTextListener(this);

        fabReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean correct = false;
                correct = dbLoans.resetRoute();
                if(correct){
                    Toast.makeText(MainActivity.this, "RUTA REINICIADA", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "ERROR AL REINICIAR LA RUTA", Toast.LENGTH_SHORT).show();
                }
            }
        });

        fabExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowExpenses.class);
                startActivity(intent);
            }
        });

        fabPayDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowLoansPayDay.class);
                startActivity(intent);
            }
        });

    }

    public void verificarPermisos(){
        int permiso = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.CALL_PHONE);

        if(permiso != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_CODE);
        }
    }

    public void addLoanMenu(){
        Intent intent = new Intent(this, ShowClientToLoan.class);
        startActivity(intent);
    }

    public void showClientMenu(){
        Intent intent = new Intent(this, ShowClients.class);
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filter(newText);
        return false;
    }
}