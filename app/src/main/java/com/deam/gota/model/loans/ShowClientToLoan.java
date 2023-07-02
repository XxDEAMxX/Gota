package com.deam.gota.model.loans;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.EditText;
import android.widget.Toast;


import com.deam.gota.R;
import com.deam.gota.adapters.ListClientAdapterToLoan;
import com.deam.gota.dataBases.DbClients;
import com.deam.gota.pojos.Clients;

import java.util.ArrayList;

public class ShowClientToLoan extends AppCompatActivity {

    private EditText search;
    private RecyclerView clients;
    private ArrayList<Clients> listClients;

    ArrayList<String> names = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_client_to_loan);


        search = findViewById(R.id.searchClientToEdit);
        clients = findViewById(R.id.list);


        clients.setLayoutManager(new LinearLayoutManager(this));

        DbClients dbClients = new DbClients(ShowClientToLoan.this);

        listClients = new ArrayList<>();
        ListClientAdapterToLoan adapter = new ListClientAdapterToLoan(dbClients.showClients());

        clients.setAdapter(adapter);
    }
}