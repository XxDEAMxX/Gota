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
import com.deam.gota.model.loans.ShowDataLoan;
import com.deam.gota.pojos.Clients;
import com.deam.gota.pojos.Loans;
import com.deam.gota.pojos.SearchLoan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ListLoanDoneAdapter extends RecyclerView.Adapter<ListLoanDoneAdapter.ClientViewHolder> {

    ArrayList<SearchLoan> listSearchLoan;
    ArrayList<SearchLoan> listOrigin;


    public ListLoanDoneAdapter(ArrayList<Loans> listLoans, ArrayList<Clients> listClients){
        listSearchLoan = new ArrayList<>();
        listOrigin = new ArrayList<>();
        for(int i = 0; i < listLoans.size(); i++){
            for (int j = 0; j < listClients.size(); j++) {
                if(listLoans.get(i).getIdClient()==listClients.get(j).getId()){
                    listSearchLoan.add(new SearchLoan(listLoans.get(i),listClients.get(j)));
                }
            }
        }
        listOrigin.addAll(listSearchLoan);

        Collections.sort(listSearchLoan, new Comparator<SearchLoan>() {
            @Override
            public int compare(SearchLoan o1, SearchLoan o2) {
                    return new Integer(o1.getLoans().getRoute()).compareTo(new Integer(o2.getLoans().getRoute()));
            }
        });

    }

    @NonNull
    @Override
    public ListLoanDoneAdapter.ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_element_loans, null, false);
        return new ListLoanDoneAdapter.ClientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListLoanDoneAdapter.ClientViewHolder holder, int position) {

        holder.searchNameToLoan.setText(listSearchLoan.get(position).getClients().getName());
        holder.viewPhoneNumber.setText(listSearchLoan.get(position).getClients().getPhoneNumber());
        holder.viewAddressHome.setText(listSearchLoan.get(position).getClients().getAddressHome());
        
    }

    public void filter(String search){
        int lenght = search.length();
        if(lenght==0){
            listSearchLoan.clear();
            listSearchLoan.addAll(listOrigin);
        }else {
            List<SearchLoan> colletion = listSearchLoan.stream()
                    .filter(i -> i.getClients().getName().toLowerCase().contains(search.toLowerCase()))
                    .collect(Collectors.toList());
            listSearchLoan.clear();
            listSearchLoan.addAll(colletion);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listSearchLoan.size();
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
                    if(listSearchLoan.get(getAdapterPosition()).getLoans()!=null) {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, ShowDataLoan.class);
                        intent.putExtra("ID", listSearchLoan.get(getAdapterPosition()).getLoans().getId());
                        context.startActivity(intent);
                    }else {
                        Toast.makeText(v.getContext(), "EL REGISTRO NO EXISTE", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}