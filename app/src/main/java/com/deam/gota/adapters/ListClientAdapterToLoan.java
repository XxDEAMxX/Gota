package com.deam.gota.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deam.gota.model.loans.AddLoan;
import com.deam.gota.R;
import com.deam.gota.pojos.Clients;

import java.util.ArrayList;

public class ListClientAdapterToLoan extends RecyclerView.Adapter<ListClientAdapterToLoan.ClientViewHolder> {

    ArrayList<Clients> listClients;

    public ListClientAdapterToLoan(ArrayList<Clients> listClients){
        this.listClients = listClients;
    }

    @NonNull
    @Override
    public ListClientAdapterToLoan.ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_element, null, false);
        return new ListClientAdapterToLoan.ClientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListClientAdapterToLoan.ClientViewHolder holder, int position) {
        holder.searchNameToLoan.setText(listClients.get(position).getName());
        holder.viewPhoneNumber.setText(listClients.get(position).getPhoneNumber());
        holder.viewAddressHome.setText(listClients.get(position).getAddressHome());
    }

    @Override
    public int getItemCount() {
        return listClients.size();
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
                    Intent intent = new Intent(context, AddLoan.class);
                    intent.putExtra("ID", listClients.get(getAdapterPosition()).getId());
                    context.startActivity(intent);

                }
            });
        }

    }
}