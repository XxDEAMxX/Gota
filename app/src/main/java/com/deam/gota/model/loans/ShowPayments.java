package com.deam.gota.model.loans;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.deam.gota.R;
import com.deam.gota.adapters.ListPaymentsAdapter;
import com.deam.gota.dataBases.DbPayments;
import com.deam.gota.pojos.Payments;

import java.util.ArrayList;

public class ShowPayments extends AppCompatActivity {

    private RecyclerView list;
    private ArrayList<Payments> listPayments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_payments);

        list = findViewById(R.id.listPayments);

        list.setLayoutManager(new LinearLayoutManager(this));

        listPayments = new ArrayList<>();

        DbPayments dbPayments = new DbPayments(ShowPayments.this);


        for (Payments payments: dbPayments.showPayments()) {
            String date = payments.getDate();
            int amount = payments.getAmount();

            Payments existingTotal = null;
            for (Payments total: listPayments) {
                if(total.getDate().equals(date)){
                    existingTotal = total;
                    break;
                }
            }

            if(existingTotal != null){
                existingTotal.setAmount(existingTotal.getAmount() + amount);
            }else {
                listPayments.add(new Payments(0, 0, date, amount));
            }

        }


        ListPaymentsAdapter adapter = new ListPaymentsAdapter(listPayments);

        list.setAdapter(adapter);

    }
}