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
import com.deam.gota.model.loans.ShowDataLoan;
import com.deam.gota.pojos.Clients;
import com.deam.gota.pojos.Loans;

import java.util.ArrayList;

public class ListLoanAdapter extends RecyclerView.Adapter<ListLoanAdapter.ClientViewHolder> {

    ArrayList<Loans> listLoans;
    ArrayList<Clients> listClients;

    public ListLoanAdapter(ArrayList<Loans> listLoans, ArrayList<Clients> listClients){
        this.listLoans = listLoans;
        this.listClients = listClients;
    }

    @NonNull
    @Override
    public ListLoanAdapter.ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_element, null, false);
        return new ListLoanAdapter.ClientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListLoanAdapter.ClientViewHolder holder, int position) {

        holder.searchNameToLoan.setText(listClients.get(listLoans.get(position).getIdClient()).getName());
        holder.viewPhoneNumber.setText(listClients.get(listLoans.get(position).getIdClient()).getPhoneNumber());
        holder.viewAddressHome.setText(listClients.get(listLoans.get(position).getIdClient()).getAddressHome());
        
    }

    @Override
    public int getItemCount() {
        return listLoans.size();
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
                    Intent intent = new Intent(context, ShowDataLoan.class);
                    intent.putExtra("ID", listLoans.get(getAdapterPosition()).getId());
                    context.startActivity(intent);

                }
            });
        }

    }
}