package com.estimote.examples.demos;

public class FoodItem {

    private String name;

    private String sku;

    private int price;

    public FoodItem(String name, String sku, int price) {
        this.name = name;
        this.sku = sku;
        this.price = price;
    }

    public String toString() {
        return String.format("%s (%.2f €)", name, (float)price / 100);
    }

    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
