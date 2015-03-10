package com.mealordering.net.model;

import com.google.api.client.util.Key;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Anthonit on 2014/4/22.
 */
public class MyOrderDetailResult extends Results<MyOrderDetailResult.MyOrderDetail> {
    public static class MyOrderDetail implements Serializable {
        @Key("OrderId")
        private String orderId;
        @Key("OrderNo")
        private String orderNo;
        @Key("OrderTime")
        private String orderTime;
        @Key("OrderState")
        private int orderState;
        @Key("IsEvaluation")
        private int isEvaluation;
        @Key("Amount")
        private String amount;
        @Key("OrderNumberId")
        private List<MyOrderResult.MyOrder.OrderNumberId> orderNumberId;
        @Key("OrderCount")
        private String orderCount;
        @Key("FoodName")
        private String foodName;
        @Key("Total")
        private float total;
        @Key("Title")
        private String title;
        @Key("OrderMoney")
        private float orderMoney;
        @Key("Coordinates_x")
        private int coordinatesX;
        @Key("Coordinates_y")
        private int coordinatesY;

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

        public String getOrderStateText() {
            String orderStateText = null;
            switch (getOrderState()) {
                case 0:
                    orderStateText = "等待制作";
                    break;
                case 1:
                    orderStateText = "制作中";
                    break;
                case 2:
                    orderStateText = "等待配送";
                    break;
                case 3:
                    orderStateText = "配送中";
                    break;
                case 4:
                    orderStateText = "等待结算";
                    break;
                case 5:
                    orderStateText = isEvaluation == 1 ? "已评价" : "未评价";
                    break;
            }
            return orderStateText;
        }

        public boolean canLocation() {
            return orderState == 3 ;
        }

        public boolean isEvaluation() {
            return isEvaluation == 1;
        }

        public String getAmount() {
            return amount;
        }

        public List<MyOrderResult.MyOrder.OrderNumberId> getOrderNumberId() {
            return orderNumberId;
        }

        public String getOrderCount() {
            return orderCount;
        }

        public String getFoodName() {
            return foodName;
        }

        public float getTotal() {
            return total;
        }

        public String getTitle() {
            return title;
        }

        public float getOrderMoney() {
            return orderMoney;
        }

        public int getCoordinatesX() {
            return coordinatesX;
        }

        public int getCoordinatesY() {
            return coordinatesY;
        }

        @Override
        public String toString() {
            return "MyOrderDetail{" +
                    "orderId='" + orderId + '\'' +
                    ", orderNo='" + orderNo + '\'' +
                    ", orderTime='" + orderTime + '\'' +
                    ", orderState='" + orderState + '\'' +
                    ", isEvaluation='" + isEvaluation + '\'' +
                    ", amount='" + amount + '\'' +
                    ", orderNumberId=" + orderNumberId +
                    ", orderCount='" + orderCount + '\'' +
                    ", foodName='" + foodName + '\'' +
                    ", total=" + total +
                    ", title='" + title + '\'' +
                    ", orderMoney=" + orderMoney +
                    ", coordinatesX=" + coordinatesX +
                    ", coordinatesY=" + coordinatesY +
                    '}';
        }
    }
}