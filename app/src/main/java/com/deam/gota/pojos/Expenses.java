package com.deam.gota.pojos;

public class Expenses {

    private int expense;
    private String date;
    private String coment;

    public Expenses(int expense, String date, String coment) {
        this.expense = expense;
        this.date = date;
        this.coment = coment;
    }

    public int getExpense() {
        return expense;
    }

    public void setExpense(int expense) {
        this.expense = expense;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComent() {
        return coment;
    }

    public void setComent(String coment) {
        this.coment = coment;
    }
}
