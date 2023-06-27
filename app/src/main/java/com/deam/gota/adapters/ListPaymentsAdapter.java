package com.deam.gota.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deam.gota.R;
import com.deam.gota.model.loans.EditPayment;

import java.util.ArrayList;

public class ListPaymentsAdapter extends RecyclerView.Adapter<ListPaymentsAdapter.ClientViewHolder> {
    ArrayList<com.deam.gota.pojos.Payments> listPayments;
    public ListPaymentsAdapter(ArrayList<com.deam.gota.pojos.Payments> listPayments){
        this.listPayments = listPayments;
    }
    @NonNull
    @Override
    public ListPaymentsAdapter.ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_element_payments, null, false);
        return new ListPaymentsAdapter.ClientViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ListPaymentsAdapter.ClientViewHolder holder, int position) {
            holder.date.setText(listPayments.get(position).getDate()+"");
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, EditPayment.class);
                    intent.putExtra("ID", listPayments.get(getAdapterPosition()).getDate());
                    context.startActivity(intent);

                }
            });
        }

    }
}