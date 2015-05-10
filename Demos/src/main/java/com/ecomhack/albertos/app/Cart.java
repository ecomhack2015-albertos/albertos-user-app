package com.ecomhack.albertos.app;

import java.util.ArrayList;

public class Cart {

    private ArrayList<FoodItem> items;

    private int total = 0;

    public Cart() {
        items = new ArrayList<FoodItem>();
    }

    public void addItem(FoodItem item) {
        items.add(item);
        total += item.getPrice();
    }

    public void removeItem(FoodItem item) {
        total -= item.getPrice();
        items.remove(item);
    }

    public ArrayList<FoodItem> getItems() {
        return items;
    }

    public int getItemCount() {
        return items.size();
    }

    public int getTotal() {
        return total;
    }

    public void clear(){
        items = new ArrayList<FoodItem>();
    }
}
