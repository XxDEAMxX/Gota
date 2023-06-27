package com.deam.gota.model.loans;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.deam.gota.R;
import com.deam.gota.adapters.ListExpensesAdapter;
import com.deam.gota.adapters.ListLoanAdapter;
import com.deam.gota.adapters.ListPaymentAdapter;
import com.deam.gota.dataBases.DbClients;
import com.deam.gota.dataBases.DbLoans;
import com.deam.gota.dataBases.DbPayments;
import com.deam.gota.pojos.Loans;

import java.util.ArrayList;

public class Payments extends AppCompatActivity {

    private RecyclerView payments;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<com.deam.gota.pojos.Payments> listPayment;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("ID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }

        payments = findViewById(R.id.list);

        payments.setLayoutManager(new LinearLayoutManager(this));

        swipeRefreshLayout = findViewById(R.id.refreshPayments);
        
        DbPayments dbPayments = new DbPayments(Payments.this);

        listPayment=new ArrayList<>();

        for (int i = 0; i < dbPayments.showPayments().size(); i++){
            if(dbPayments.showPayments().get(i).getIdLoans() == id){
                listPayment.add(dbPayments.showPayments().get(i));
            }
        }

        ListPaymentAdapter adapter = new ListPaymentAdapter(listPayment);
        payments.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                listPayment = new ArrayList<>();

                for (int i = 0; i < dbPayments.showPayments().size(); i++){
                    if(dbPayments.showPayments().get(i).getIdLoans() == id){
                        listPayment.add(dbPayments.showPayments().get(i));
                    }
                }

                ListPaymentAdapter adapter = new ListPaymentAdapter(listPayment);

                payments.setAdapter(adapter);
            }
        });

    }
}