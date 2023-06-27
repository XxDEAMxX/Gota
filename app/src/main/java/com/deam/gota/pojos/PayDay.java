package com.deam.gota.pojos;

public class PayDay {

    private int amount;
    private String name;
    private String phoneNumber;

    public PayDay(int amount, String name, String phoneNumber) {
        this.amount = amount;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
