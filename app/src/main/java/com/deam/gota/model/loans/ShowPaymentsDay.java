package com.deam.gota.model.loans;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.deam.gota.R;
import com.deam.gota.adapters.ListPayDayAdapter;
import com.deam.gota.adapters.ListPaymentsAdapter;
import com.deam.gota.dataBases.DbClients;
import com.deam.gota.dataBases.DbLoans;
import com.deam.gota.dataBases.DbPayments;
import com.deam.gota.pojos.PayDay;
import com.deam.gota.pojos.Payments;

import java.util.ArrayList;

public class ShowPaymentsDay extends AppCompatActivity {

    private RecyclerView list;
    private ArrayList<PayDay> listPayments;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_payments_day);

        list = findViewById(R.id.listPaymentsDay);

        list.setLayoutManager(new LinearLayoutManager(this));

        listPayments = new ArrayList<>();

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                id = null;
            } else {
                id = extras.getString("ID");
            }
        } else {
            id = savedInstanceState.getString("ID");
        }

        DbPayments dbPayments = new DbPayments(this);
        DbLoans dbLoans = new DbLoans(this);
        DbClients dbClients = new DbClients(this);

        for (Payments pay: dbPayments.showPayments()) {
            if(pay.getDate().equals(id)){
                listPayments.add(new PayDay(pay.getAmount(),
                        dbClients.showClient(dbLoans.showLoan(pay.getIdLoans()).getIdClient()).getName(),
                        dbClients.showClient(dbLoans.showLoan(pay.getIdLoans()).getIdClient()).getPhoneNumber()));
            }
        }


        ListPayDayAdapter adapter = new ListPayDayAdapter(listPayments);

        list.setAdapter(adapter);

    }
}