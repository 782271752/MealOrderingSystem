package com.mealordering.bean;

/**
 * Created by Anthony on 14-2-21.
 */
public class FoodItem {
    public String image;
    public String name;
    public int type;
    public int likeCount;
    public int price;

    public FoodItem() {
        name = "极品云吞";
        likeCount = 8888;
        price = 18;
    }

    @Override
    public String toString() {
        return "FoodItem{" +
                "image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", likeCount=" + likeCount +
                ", price=" + price +
                '}';
    }
}
