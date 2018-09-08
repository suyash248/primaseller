package com.primaseller.models;

/**
 * Root of all types of items.
 */
public class Item {
    protected String id;
    protected String title;

    // TODO - BigDecimal is better option for currency.
    protected Float price;
    protected Integer sellCount;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getSellCount() {
        return sellCount;
    }

    public void setSellCount(Integer sellCount) {
        this.sellCount = sellCount;
    }

    public void incrSellCountBy(Integer incrBy) {
        this.sellCount = this.sellCount + incrBy;
    }
}
