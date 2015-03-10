package com.mealordering.employee.net.model;

import com.google.api.client.util.Key;

import java.util.List;

/**
 * Created by Anthonit on 2014/4/22.
 */
public class OrderDetailResult extends Results<OrderDetailResult.OrderDetail> {
    public static class OrderDetail {
        @Key("OrderId")
        private String orderId;
        @Key("OrderNo")
        private String orderNo;
        @Key("OrderTime")
        private String orderTime;
        @Key("OrderState")
        private int orderState;
        @Key("Amount")
        private String amount;
        @Key("OrderNumberId")
        private List<OrderNumberId> orderNumberId;
        @Key("OrderMoney")
        private float orderMoney;


        public String getOrderStateText() {
            String orderStateText = null;
            switch (orderState) {
                case 3:
                    orderStateText = "派送中";
                    break;
                case 5:
                    orderStateText = "已完成";
                    break;
            }
            return orderStateText;
        }


        @Override
        public String toString() {
            return "OrderDetail{" +
                    "orderId='" + orderId + '\'' +
                    ", orderNo='" + orderNo + '\'' +
                    ", orderTime='" + orderTime + '\'' +
                    ", orderState=" + orderState +
                    ", amount='" + amount + '\'' +
                    ", orderNumberId=" + orderNumberId +
                    ", orderMoney=" + orderMoney +
                    '}';
        }

        public String getOrderId() {
            return orderId;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public int getOrderState() {
            return orderState;
        }

        public void setOrderState(int orderState) {
            this.orderState = orderState;
        }

        public String getAmount() {
            return amount;
        }

        public List<OrderNumberId> getOrderNumberId() {
            return orderNumberId;
        }

        public float getOrderMoney() {
            return orderMoney;
        }
    }
}
