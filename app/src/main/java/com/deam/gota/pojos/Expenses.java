package com.deam.gota.pojos;

public class Expenses {

    private int id;
    private int expense;
    private String date;
    private String coment;

    public Expenses(int id, int expense, String date, String coment) {
        this.id = id;
        this.expense = expense;
        this.date = date;
        this.coment = coment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
