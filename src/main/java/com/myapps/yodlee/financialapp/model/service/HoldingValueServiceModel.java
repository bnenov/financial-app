package com.myapps.yodlee.financialapp.model.service;

public class HoldingValueServiceModel {

    private double amount;
    private String currency;

    public HoldingValueServiceModel() {
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
