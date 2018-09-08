package com.primaseller.services;

import com.primaseller.models.Book;
import com.primaseller.models.Customer;
import com.primaseller.models.Item;
import com.primaseller.models.Transaction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class AnalyticsService {
    private Map<String, Book> booksById = new TreeMap<>();
    private Set<Transaction> transactions = new HashSet<>();
    private Map<String, Customer> customersByEmail = new HashMap<>();

    public List<Book> getTopSellingBooks(int count) {
        List<Book> topSellingBooks = this.booksById.values().stream().sorted(Comparator.comparing(Book::getSellCount))
                .collect(Collectors.toList());
        if (count <= topSellingBooks.size()) {
            return topSellingBooks.stream().skip(topSellingBooks.size()-count).collect(Collectors.toList());
        }
        return topSellingBooks;
    }

    public List<Customer> getTopCustomers(int count) {
        List<Customer> topCustomers = this.customersByEmail.values().stream().sorted(Comparator.
                comparing(Customer::getTotalPurchaseAmount)).collect(Collectors.toList());
        if (count <= topCustomers.size()) {
            return topCustomers.stream().skip(topCustomers.size()-count).collect(Collectors.toList());
        }
        return topCustomers;
    }

    public double getSaleAmountOnDate(String dateString) {
        double saleAmtOnDate = this.transactions.stream().filter(txn -> txn.getDateString().equals(dateString))
                .mapToDouble(txn -> txn.getAmount()).sum();
        return saleAmtOnDate;
    }

    public void processBooksCsv(String booksListFp){
        List<String> booksCSV = new ArrayList<>();
        try {
            booksCSV = Files.readAllLines(Paths.get(booksListFp));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.booksById = booksCSV.stream().map(line -> new Book(line.trim().split(","))).collect(
                Collectors.toMap(book-> book.getId(), book-> book));
    }

    public void processSalesCsv(String salesListFp){
        List<String> salesCSV = new ArrayList<>();
        try {
            salesCSV = Files.readAllLines(Paths.get(salesListFp));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String saleLine: salesCSV) {
            int idx = 0;
            saleLine = saleLine.trim();
            String saleLineArr[] = saleLine.split(",");
            String dateString = saleLineArr[idx++];
            String userEmail = saleLineArr[idx++];
            String paymentMethod = saleLineArr[idx++];
            Integer itemCount = Integer.valueOf(saleLineArr[idx++]);
            List<Item> txnItems = new ArrayList<>();
            Customer customer = customersByEmail.getOrDefault(userEmail, new Customer(userEmail, 0.0f));
            customersByEmail.putIfAbsent(userEmail, customer);
            float txnAmount = 0.0f;

            while(itemCount > 0) {
                String bookIdSellCount[] = saleLineArr[idx++].split(";");
                String bookId = bookIdSellCount[0];
                Integer quantity = Integer.valueOf(bookIdSellCount[1]);

                Item book = this.booksById.get(bookId);
                book.incrSellCountBy(quantity);
                txnItems.add(book);

                txnAmount = txnAmount + (quantity * book.getPrice());
                customer.incrTotalPurchaseAmountBy(quantity * book.getPrice());
                itemCount--;
            }

            Transaction txn = new Transaction(dateString, paymentMethod, txnAmount, customer, txnItems);
            this.transactions.add(txn);
        }
    }
}
