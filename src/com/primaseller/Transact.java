package com.primaseller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import com.primaseller.models.*;
import com.primaseller.services.AnalyticsService;

public class Transact {
    // TODO - Inject the services using DI.
    private AnalyticsService analyticsService = new AnalyticsService();

    public static void main(String[] args) {
        Transact transact = new Transact();
        Map<String, String> argsPair = parseArgs(args);

        if(!argsPair.containsKey("books") || !argsPair.containsKey("sales")) {
            System.out.println("ERROR: --books & --sales are required!");
            return;
        }

        String booksListFp = argsPair.get("books");
        String salesListFp = argsPair.get("sales");

        transact.analyticsService.processBooksCsv(booksListFp);
        transact.analyticsService.processSalesCsv(salesListFp);

        if(argsPair.containsKey("top_selling_books")) {
            List<Book> topSellingBooks = transact.analyticsService.getTopSellingBooks(
                    Integer.valueOf(argsPair.get("top_selling_books")));
            topSellingBooks.forEach(b -> System.out.print(b.getId() + "\t"));
            System.out.println();
        }
        if(argsPair.containsKey("top_customers")) {
            List<Customer> topCustomers = transact.analyticsService.getTopCustomers(
                    Integer.valueOf(argsPair.get("top_selling_books")));
            topCustomers.forEach(c -> System.out.print(c.getEmail() + "\t"));
            System.out.println();
        }
        if(argsPair.containsKey("sales_on_date")) {
            String dateString = argsPair.get("sales_on_date");
            double saleOnDate = transact.analyticsService.getSaleAmountOnDate(dateString);
            System.out.print(dateString + "\t" + saleOnDate);
        }
        System.out.println();
    }

    private static Map<String, String> parseArgs(String... args){
        final Map<String, String> pairs = new HashMap<>();
        Arrays.stream(args).forEach(arg -> {
            String kv[] = arg.split("=");
            pairs.putIfAbsent(kv[0].trim().substring(2), kv[1].trim());
        });
        return pairs;
    }
}
