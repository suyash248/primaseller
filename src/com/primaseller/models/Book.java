package com.primaseller.models;

import java.util.StringJoiner;

/**
 * Represents an item of type `book`.
 */
public class Book extends Item {
    private String author;

    public Book(String... params) {
        this.id = params[0];
        this.title = params[1];
        this.author = params[2];
        this.price = Float.valueOf(params[3]);
        this.sellCount = 0;
    }

    public Book(String id, String title, String author, Float price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner("|");
        sj.add(this.id);
        sj.add(this.title);
        sj.add(String.valueOf(this.price));
        sj.add(String.valueOf(this.sellCount));
        return sj.toString();
    }
}
