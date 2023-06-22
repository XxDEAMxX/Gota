package com.deam.gota.pojos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Loans {

    private int id;
    private int idClient;
    private int route;
    private String date;
    private String quotas;
    private String loan;

    public Loans(int id, int idClient, int route, String date, String quotas, String loan) {
        this.id = id;
        this.idClient = idClient;
        this.route = route;
        this.date = date;
        this.quotas = quotas;
        this.loan = loan;
    }

    public String getDate() {
        return date;
    }

    public int getIdClient() {
        return idClient;
    }

    public int getRoute() {
        return route;
    }

    public String getQuotas() {
        return quotas;
    }

    public String getLoan() {
        return loan;
    }

    public int getId() {
        return id;
    }
}
