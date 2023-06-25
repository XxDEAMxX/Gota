package com.deam.gota.model.expenses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.deam.gota.R;
import com.deam.gota.adapters.ListClientAdapter;
import com.deam.gota.adapters.ListExpensesAdapter;
import com.deam.gota.dataBases.DbClients;
import com.deam.gota.dataBases.DbExpenses;
import com.deam.gota.model.clients.AddClient;
import com.deam.gota.model.clients.ShowClients;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ShowExpenses extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd;
    private SwipeRefreshLayout refresh;
    private DbExpenses dbExpenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_expenses);

        recyclerView = findViewById(R.id.list);
        fabAdd = findViewById(R.id.fabAddExpense);
        refresh = findViewById(R.id.refresh);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        dbExpenses = new DbExpenses(ShowExpenses.this);
        Toast.makeText(this, dbExpenses.showExpenses().get(0).getExpense()+"", Toast.LENGTH_SHORT).show();

        ListExpensesAdapter adapter = new ListExpensesAdapter(dbExpenses.showExpenses());

        recyclerView.setAdapter(adapter);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setRefresh();

            }
        });

    }

    public void add(){
        Intent intent = new Intent(ShowExpenses.this, Expenses.class);
        startActivity(intent);
    }

    public void setRefresh(){
        refresh.setRefreshing(false);

        ListExpensesAdapter adapter = new ListExpensesAdapter(dbExpenses.showExpenses());

        recyclerView.setAdapter(adapter);
    }

}