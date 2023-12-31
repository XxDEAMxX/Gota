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
import com.deam.gota.model.clients.ShowClients;
import com.deam.gota.model.clients.ShowDataClient;
import com.deam.gota.model.expenses.EditExpense;
import com.deam.gota.pojos.Clients;
import com.deam.gota.pojos.Expenses;

import java.util.ArrayList;

public class ListExpensesAdapter extends RecyclerView.Adapter<ListExpensesAdapter.ClientViewHolder> {

    ArrayList<Expenses> listExpenses;

    public ListExpensesAdapter(ArrayList<Expenses> listExpenses){
        this.listExpenses = listExpenses;
    }


    @NonNull
    @Override
    public ListExpensesAdapter.ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_element, null, false);
        return new ClientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListExpensesAdapter.ClientViewHolder holder, int position) {
        holder.searchNameToLoan.setText(listExpenses.get(position).getExpense()+"");
        holder.viewPhoneNumber.setText(listExpenses.get(position).getComent());
        holder.viewAddressHome.setText(listExpenses.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return listExpenses.size();
    }

    public class ClientViewHolder extends RecyclerView.ViewHolder {

        TextView searchNameToLoan, viewPhoneNumber, viewAddressHome;
        public ClientViewHolder(@NonNull View itemView) {
            super(itemView);
            searchNameToLoan = itemView.findViewById(R.id.nameTextView);
            viewAddressHome = itemView.findViewById(R.id.addressHomeTexView);
            viewPhoneNumber = itemView.findViewById(R.id.phoneNumberTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION && position < listExpenses.size()) {
                        Expenses expense = listExpenses.get(position);
                        if (expense != null) {
                            Intent intent = new Intent(context, EditExpense.class);
                            intent.putExtra("ID", expense.getId());
                            context.startActivity(intent);
                        }
                    }
                }
            });
        }

    }
}
