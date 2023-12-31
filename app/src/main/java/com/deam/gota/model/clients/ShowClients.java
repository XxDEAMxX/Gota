package com.deam.gota.model.clients;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.deam.gota.R;
import com.deam.gota.adapters.ListClientAdapter;
import com.deam.gota.dataBases.DbClients;
import com.deam.gota.pojos.Clients;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ShowClients extends AppCompatActivity {

    private EditText searchEditText;
    private RecyclerView clientsRecyclerView;
    private FloatingActionButton fabAddClient;
    private ArrayList<Clients> listClients;
    private SwipeRefreshLayout swipeRefreshLayout;
    private DbClients dbClients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_clients);

        searchEditText = findViewById(R.id.searchClientToEdit);
        clientsRecyclerView = findViewById(R.id.list);
        fabAddClient = findViewById(R.id.fabAddClient);
        clientsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        swipeRefreshLayout = findViewById(R.id.refresh);
        dbClients = new DbClients(ShowClients.this);

        updateList();

        fabAddClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowClients.this, AddClient.class);
                startActivity(intent);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                updateList();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList();
    }

    public void updateList() {
        listClients = dbClients.showClients();
        ListClientAdapter adapter = new ListClientAdapter(listClients);
        clientsRecyclerView.setAdapter(adapter);
    }
}
