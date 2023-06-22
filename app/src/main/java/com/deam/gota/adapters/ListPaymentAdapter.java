package com.deam.gota.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deam.gota.R;
import com.deam.gota.model.loans.Payments;
import com.deam.gota.model.loans.ShowDataLoan;
import com.deam.gota.pojos.Clients;
import com.deam.gota.pojos.Loans;

import java.util.ArrayList;

public class ListPaymentAdapter extends RecyclerView.Adapter<ListPaymentAdapter.ClientViewHolder> {

    int id;
    int size = 0;
    ArrayList<com.deam.gota.pojos.Payments> listPayments;

    public ListPaymentAdapter(int id, ArrayList<com.deam.gota.pojos.Payments> listPayments){
        this.id = id;
        this.listPayments = listPayments;
        for(int i = 0; i < listPayments.size(); i++){
            if(id == listPayments.get(i).getIdLoans()){

                size++;
            }
        }
    }

    @NonNull
    @Override
    public ListPaymentAdapter.ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_element_payments, null, false);
        return new ListPaymentAdapter.ClientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListPaymentAdapter.ClientViewHolder holder, int position) {
            holder.date.setText(listPayments.get(position).getDate());
            holder.amount.setText(listPayments.get(position).getAmount()+"");
    }

    @Override
    public int getItemCount() {
        return listPayments.size();
    }

    public class ClientViewHolder extends RecyclerView.ViewHolder {

        TextView date, amount;
        public ClientViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.dateText);
            amount = itemView.findViewById(R.id.amountEditText);

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ShowDataLoan.class);
                    //intent.putExtra("ID", listLoans.get(getAdapterPosition()).getId());
                    context.startActivity(intent);

                }
            });*/
        }

    }
}