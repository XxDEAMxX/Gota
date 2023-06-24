package com.deam.gota.pojos;

public class SearchLoan {

    private Clients clients;
    private Loans loans;

    public SearchLoan(Loans loans, Clients clients ) {
        this.clients = clients;
        this.loans = loans;
    }

    public Clients getClients() {
        return clients;
    }

    public void setClients(Clients clients) {
        this.clients = clients;
    }

    public Loans getLoans() {
        return loans;
    }

    public void setLoans(Loans loans) {
        this.loans = loans;
    }
}
