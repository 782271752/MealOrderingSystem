package com.mealordering.employee.net.model;

import com.google.api.client.util.Key;

import java.io.Serializable;

public class OrderNumberId implements Serializable {
    @Key("OrderCount")
    private int orderCount;
    @Key("FoodName")
    private String foodName;
    @Key("Total")
    private float total;

    @Override
    public String toString() {
        return "OrderNumberId{" +
                "orderCount=" + orderCount +
                ", foodName='" + foodName + '\'' +
                ", total='" + total + '\'' +
                '}';
    }

    public int getOrderCount() {
        return orderCount;
    }

    public String getFoodName() {
        return foodName;
    }

    public float getTotal() {
        return total;
    }
}