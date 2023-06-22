package com.deam.gota.model.clients;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;

import com.deam.gota.R;
import com.deam.gota.adapters.ListClientAdapter;
import com.deam.gota.dataBases.DbClients;
import com.deam.gota.pojos.Clients;

import java.util.ArrayList;

public class ShowClients extends AppCompatActivity {

    private EditText search;
    private RecyclerView clients;
    private ArrayList<Clients> listClients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_clients);

        search  = findViewById(R.id.searchClientToEdit);
        clients = findViewById(R.id.list);


        clients.setLayoutManager(new LinearLayoutManager(this));

        DbClients dbClients = new DbClients(ShowClients.this);

        listClients = new ArrayList<>();
        ListClientAdapter adapter = new ListClientAdapter(dbClients.showClients());
        adapter.showClientsClose(this);

        clients.setAdapter(adapter);


    }
}