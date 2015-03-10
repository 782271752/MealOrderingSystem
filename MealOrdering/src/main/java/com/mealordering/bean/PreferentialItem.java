package com.mealordering.bean;

/**
 * Created by Anthony on 14-2-17.
 */
public class PreferentialItem {
    public int icon;
    public String title = "优惠活动";
    public int amount;

    public PreferentialItem(int icon, String title, int amount) {
        this.icon = icon;
        this.title = title;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "PreferentialItem{" +
                "icon='" + icon + '\'' +
                ", title='" + title + '\'' +
                ", amount=" + amount +
                '}';
    }
}
