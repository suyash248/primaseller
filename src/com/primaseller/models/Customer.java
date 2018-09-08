package com.primaseller.models;

import java.util.StringJoiner;

/**
 * Represents customer entity.
 */
public class Customer {
    private String email;
    // TODO - BigDecimal is better option for currency.
    private Float totalPurchaseAmount;

    public Customer(String email, Float totalPurchaseAmount) {
        this.email = email;
        this.totalPurchaseAmount = totalPurchaseAmount;
    }

    public Float getTotalPurchaseAmount() {
        return totalPurchaseAmount;
    }

    public String getEmail() {
        return email;
    }

    public void incrTotalPurchaseAmountBy(float incrBy) {
        this.totalPurchaseAmount = this.totalPurchaseAmount + incrBy;
    }

    @Override
    public boolean equals(Object obj) {
        Customer c = (Customer) obj;
        return c.email.equals(this.email);
    }

    @Override
    public int hashCode() {
        return this.email.hashCode();
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner("|");
        sj.add(this.email);
        sj.add(String.valueOf(this.totalPurchaseAmount));
        return sj.toString();
    }
}
