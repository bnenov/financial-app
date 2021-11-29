package com.myapps.yodlee.financialapp.model.service;

import com.google.gson.annotations.Expose;

public class BalanceServiceModel {

    private String currency;
    private double amount;

    public BalanceServiceModel() {
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
