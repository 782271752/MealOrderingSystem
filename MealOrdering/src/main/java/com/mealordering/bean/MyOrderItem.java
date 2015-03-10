package com.mealordering.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Anthony on 14-2-21.
 */
public class MyOrderItem implements Serializable {
    public String id;
    public String time;
    public float totalCost;
    public String state;
    public int type;
    public List<String> foods;

    public MyOrderItem(int type) {
        id = "77789655463";
        time = "2014-02-21 11:11:23";
        totalCost = 18;
        state = "到店自取";
        this.type = type;
    }

    public enum OrderState {
        ALL(1, "全部"),
        WAIT(2, "等待配送"),
        DELIVERY(3, "配送中"),
        BYONESELF(4, "到店自取");
        private int state;
        private String stateMsg;

        private OrderState(int state, String stateMsg) {
            this.state = state;
            this.stateMsg = stateMsg;
        }

        public int getState() {
            return state;
        }

        public String getStateMsg() {
            return stateMsg;
        }
    }
}
