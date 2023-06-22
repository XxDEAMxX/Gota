package com.deam.gota.pojos;

public class Payments {

    private int id;
    private int idLoans;
    String date;
    int amount;

    public Payments(int id, int idLoans, String date, int amount) {
        this.id = id;
        this.idLoans = idLoans;
        this.date = date;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdLoans() {
        return idLoans;
    }

    public void setIdLoans(int idLoans) {
        this.idLoans = idLoans;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
