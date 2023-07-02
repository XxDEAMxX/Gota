package com.deam.gota.model.loans;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.deam.gota.R;
import com.deam.gota.adapters.ListLoanAdapter;
import com.deam.gota.adapters.ListLoanDoneAdapter;
import com.deam.gota.dataBases.DbClients;
import com.deam.gota.dataBases.DbLoans;
import com.deam.gota.dataBases.DbPayments;
import com.deam.gota.pojos.Loans;
import com.deam.gota.pojos.Payments;

import java.util.ArrayList;
import java.util.Calendar;

public class LoansDone extends AppCompatActivity {

    private RecyclerView loans;
    private ArrayList<Loans> listLoans;
    private DbClients dbClients;
    private DbLoans dbLoans;
    private DbPayments dbPayments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loans_done);

        init();
        setListAdapter();

    }

    public void init(){
        loans = findViewById(R.id.listLoansDone);
        loans.setLayoutManager(new LinearLayoutManager(this));

        dbClients = new DbClients(this);
        dbLoans = new DbLoans(this);
        dbPayments = new DbPayments(this);
    }

    public void setListAdapter(){
        listLoans = new ArrayList<>();
        ArrayList<Loans> allLoans = dbLoans.showLoans();
        ArrayList<com.deam.gota.pojos.Payments> allPayments = dbPayments.showPayments();

        for (Loans loan : allLoans) {
            double saldo = 0.0;
            boolean finish = false;

            for (com.deam.gota.pojos.Payments payment : allPayments) {
                if (loan.getId() == payment.getIdLoans()) {
                    saldo += payment.getAmount();
                }
            }
            if (saldo >= Double.parseDouble(loan.getLoan())) {
                finish = true;
            }

            if (finish) {
                listLoans.add(loan);
            }
        }

        ListLoanDoneAdapter adapter = new ListLoanDoneAdapter(listLoans, dbClients.showClients());
        loans.setAdapter(adapter);
    }

}