package com.mealordering.bean;

import java.io.Serializable;

/**
 * Created by Anthony on 14-2-14.
 */
public class OrderFoodItem implements Serializable {
    public String icon;
    public String name;
    public int amount;
    public float price;

    public OrderFoodItem() {
        this.icon = "";
        this.name = "订购食物的名称";
        this.amount = 1;
        this.price = 13;
    }

    @Override
    public String toString() {
        return "OrderFoodItem{" +
                "icon='" + icon + '\'' +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", price=" + price +
                '}';
    }
}
