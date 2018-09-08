package com.primaseller.models;

import java.util.*;
import java.util.UUID;

public class Transaction {
    private String txnId;
    private String dateString;
    private Customer customer;
    private String paymentMethod;
    private Float amount;
    private List<Item> items;

    public Transaction(String dateString, String paymentMethod, Float amount, Customer customer, List<Item> items) {
        this.txnId = UUID.randomUUID().toString();
        this.dateString = dateString;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.customer = customer;
        this.items = items;
    }

    @Override
    public boolean equals(Object obj) {
        Transaction txn = (Transaction) obj;
        return this.txnId.equals(txn.txnId);
    }

    @Override
    public int hashCode() {
        return this.txnId.hashCode();
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner("|");
        sj.add(this.txnId);
        sj.add(String.valueOf(this.amount));
        sj.add(String.valueOf(this.customer));
        return sj.toString();
    }

    public Float getAmount() {
        return amount;
    }

    public String getTxnId() {
        return txnId;
    }

    public String getDateString() {
        return dateString;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public List<Item> getItems() {
        return items;
    }
}

/**
 * java Transact --books=/path/to/books.list --sales=/path/to/sales.list --top_selling_books=3 --top_customers=2 --sales_on_date=2018-08-04
 */