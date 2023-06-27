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

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private int REQUEST_CODE = 200;

    private FloatingActionButton fabAddLoan, fabShowClients, fabReset, fabExpenses, fabShowPaymentsDay;
    private SearchView search;
    private RecyclerView loans;
    private ArrayList<Loans> listLoans;
    private SwipeRefreshLayout swipeRefreshLayout;
    ListLoanAdapter adapter;
    private DbLoans dbLoans;

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

        dbLoans = new DbLoans(MainActivity.this);
        DbClients dbClients = new DbClients(MainActivity.this);

        listLoans = new ArrayList<>();
        dbLoans.showLoans().size();
        adapter = new ListLoanAdapter(dbLoans.showLoans(), dbClients.showClients());

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
                dbLoans.showLoans().size();
                ListLoanAdapter adapter = new ListLoanAdapter(dbLoans.showLoans(), dbClients.showClients());

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