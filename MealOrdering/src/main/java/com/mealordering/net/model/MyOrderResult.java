package com.mealordering.net.model;

import com.google.api.client.util.Key;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Anthonit on 2014/4/22.
 */
public class MyOrderResult extends Results<MyOrderResult.MyOrder> {
    public static class MyOrder implements Serializable {
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
        private float amount;
        @Key("OrderNumberId")
        private List<OrderNumberId> orderNumberId;
        @Key("Coordinates_x")
        private String coordinatesX;
        @Key("Coordinates_y")
        private String coordinatesY;

        @Override
        public String toString() {
            return "MyOrder{" +
                    "orderId='" + orderId + '\'' +
                    ", orderNo='" + orderNo + '\'' +
                    ", orderTime='" + orderTime + '\'' +
                    ", orderState='" + orderState + '\'' +
                    ", isEvaluation='" + isEvaluation + '\'' +
                    ", amount='" + amount + '\'' +
                    ", orderNumberId='" + orderNumberId + '\'' +
                    ", coordinatesX=" + coordinatesX +
                    ", coordinatesY=" + coordinatesY +
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
            return orderState == 3 || orderState == 5;
        }

        public boolean isEvaluation() {
            return isEvaluation == 1;
        }

        public float getAmount() {
            return amount;
        }

        public List<OrderNumberId> getOrderNumberId() {
            return orderNumberId;
        }

        public String getCoordinatesX() {
            return coordinatesX;
        }

        public String getCoordinatesY() {
            return coordinatesY;
        }

        public static class OrderNumberId implements Serializable {
            @Key("OrderCount")
            private int orderCount;
            @Key("FoodName")
            private String foodName;
            @Key("Total")
            private String total;

            public OrderNumberId() {
            }

            public OrderNumberId(int orderCount, String foodName, String total) {
                this.orderCount = orderCount;
                this.foodName = foodName;
                this.total = total;
            }

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

            public String getTotal() {
                return total;
            }
        }
    }
}
