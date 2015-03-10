package com.mealordering.bean;

/**
 * Created by Anthony on 14-2-14.
 */
public class TextItem {
    public int id;
    public int drawable;
    public String name;

    public TextItem(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public TextItem(int id, int drawable, String name) {
        this.id = id;
        this.drawable = drawable;
        this.name = name;
    }

    @Override
    public String toString() {
        return "TextItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
