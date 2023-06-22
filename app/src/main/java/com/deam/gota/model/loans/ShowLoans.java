package com.deam.gota.model.loans;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;

import com.deam.gota.R;
import com.deam.gota.adapters.ListLoanAdapter;
import com.deam.gota.dataBases.DbClients;
import com.deam.gota.dataBases.DbLoans;
import com.deam.gota.pojos.Loans;

import java.util.ArrayList;

public class ShowLoans extends AppCompatActivity {

    private EditText search;
    private RecyclerView loans;
    private ArrayList<Loans> listLoans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_loans);

        search  = findViewById(R.id.searchClientToEdit);
        loans = findViewById(R.id.list);


        loans.setLayoutManager(new LinearLayoutManager(this));

        DbLoans dbLoans = new DbLoans(ShowLoans.this);
        DbClients dbClients = new DbClients(ShowLoans.this);

        listLoans = new ArrayList<>();
        dbLoans.showLoans().size();
        ListLoanAdapter adapter = new ListLoanAdapter(dbLoans.showLoans(), dbClients.showClients());

        loans.setAdapter(adapter);
    }
}