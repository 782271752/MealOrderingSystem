package com.mealordering.employee.bean;

import java.io.Serializable;

/**
 * Created by Anthony on 2014/3/24.
 */
public class DeliveringItem implements Serializable {
    public int type;

    public DeliveringItem(int type) {
        this.type = type;
    }
}
