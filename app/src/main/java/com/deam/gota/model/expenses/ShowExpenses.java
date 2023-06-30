package com.deam.gota.model.expenses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.deam.gota.R;
import com.deam.gota.adapters.ListExpensesAdapter;
import com.deam.gota.dataBases.DbExpenses;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
        dbExpenses = new DbExpenses(this);

        ListExpensesAdapter adapter = new ListExpensesAdapter(dbExpenses.showExpenses());
        recyclerView.setAdapter(adapter);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExpense();
            }
        });

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setRefresh();
            }
        });
    }

    private void addExpense() {
        Intent intent = new Intent(this, Expenses.class);
        startActivity(intent);
    }

    private void setRefresh() {
        ListExpensesAdapter adapter = new ListExpensesAdapter(dbExpenses.showExpenses());
        recyclerView.setAdapter(adapter);
        refresh.setRefreshing(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setRefresh();
    }
}
