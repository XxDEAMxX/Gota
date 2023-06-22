package com.deam.gota.pojos;


public class Clients {

    private int id;
    private String name;
    private String lastName;
    private String addressHome;
    private String addressJob;
    private String phoneNumber;

    public Clients(int id, String name, String lastName, String addressHome, String addressJob, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.addressHome = addressHome;
        this.addressJob = addressJob;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddressHome() {
        return addressHome;
    }

    public void setAddressHome(String addressHome) {
        this.addressHome = addressHome;
    }

    public String getAddressJob() {
        return addressJob;
    }

    public void setAddressJob(String addressJob) {
        this.addressJob = addressJob;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Clients{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", addressHome='" + addressHome + '\'' +
                ", addressJob='" + addressJob + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
