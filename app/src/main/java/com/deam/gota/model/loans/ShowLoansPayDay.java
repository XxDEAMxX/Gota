package com.deam.gota.model.loans;

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
import com.deam.gota.model.MainActivity;
import com.deam.gota.model.loans.ShowPayments;
import com.deam.gota.adapters.ListLoanAdapter;
import com.deam.gota.dataBases.DbClients;
import com.deam.gota.dataBases.DbLoans;
import com.deam.gota.model.clients.ShowClients;
import com.deam.gota.model.expenses.ShowExpenses;
import com.deam.gota.model.loans.ShowClientToLoan;
import com.deam.gota.pojos.Loans;
import com.deam.gota.pojos.Payments;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class ShowLoansPayDay extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private int REQUEST_CODE = 200;

    private SearchView search;
    private RecyclerView loans;
    private SwipeRefreshLayout swipeRefreshLayout;
    ListLoanAdapter adapter;
    private DbLoans dbLoans;
    private DbClients dbClients;
    private DbPayments dbPayments;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_loans_pay_day);
        verificarPermisos();

        swipeRefreshLayout = findViewById(R.id.refresh);

        search  = findViewById(R.id.searchLoanPayDay);
        loans = findViewById(R.id.list);


        loans.setLayoutManager(new LinearLayoutManager(this));

        dbLoans = new DbLoans(this);
        dbClients = new DbClients(this);
        dbPayments = new DbPayments(this);

        getAdapterList();


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                getAdapterList();
            }
        });

        search.setOnQueryTextListener(this);

    }

    public void getAdapterList(){
        ArrayList<Loans> loansWithPaymentOnCurrentDate = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String currentDate = day + "/" + month + "/" + year;

        ArrayList<Payments> paymentsOnCurrentDate = new ArrayList<>();
        for (Payments payment : dbPayments.showPayments()) {
            if (payment.getDate().equals(currentDate)) {
                paymentsOnCurrentDate.add(payment);
            }
        }

        for (Loans loan : dbLoans.showLoans()) {
            for (Payments payment : paymentsOnCurrentDate) {
                if (payment.getIdLoans() == loan.getId()) {
                    loansWithPaymentOnCurrentDate.add(loan);
                    break;
                }
            }
        }

        ListLoanAdapter adapter = new ListLoanAdapter(loansWithPaymentOnCurrentDate, dbClients.showClients());
        loans.setAdapter(adapter);
    }

    public void verificarPermisos(){
        int permiso = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.CALL_PHONE);

        if(permiso != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ShowLoansPayDay.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_CODE);
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