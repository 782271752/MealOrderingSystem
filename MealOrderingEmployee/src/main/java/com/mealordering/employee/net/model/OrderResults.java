package com.mealordering.employee.net.model;

import com.google.api.client.util.Key;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Anthonit on 2014/4/22.
 */
public class OrderResults extends Results<OrderResults.Order> {
    public static class Order implements Serializable {
        @Key("OrderId")
        private String orderId;
        @Key("RealName")
        private String realName;
        @Key("Phone")
        private String phone;
        @Key("City")
        private String city;
        @Key("DeliveryAddress")
        private String deliveryAddress;
        @Key("Amount")
        private float amount;
        @Key("OrderNumberId")
        private List<OrderNumberId> orderNumberId;
        @Key("OrderCount")
        private String orderCount;
        @Key("FoodName")
        private String foodName;
        @Key("Coordinates_x")
        private String coordinatesX;
        @Key("Coordinates_y")
        private String coordinatesY;

        public String getOrderId() {
            return orderId;
        }

        public String getRealName() {
            return realName;
        }

        public String getPhone() {
            return phone;
        }

        public String getDeliveryAddress() {
            return deliveryAddress;
        }

        public float getAmount() {
            return amount;
        }

        public List<OrderNumberId> getOrderNumberId() {
            return orderNumberId;
        }

        public String getOrderCount() {
            return orderCount;
        }

        public String getFoodName() {
            return foodName;
        }

        public String getCoordinatesX() {
            return coordinatesX;
        }

        public String getCoordinatesY() {
            return coordinatesY;
        }

        public String getCity() {
            return city;
        }

        @Override
        public String toString() {
            return "Order{" +
                    "orderId='" + orderId + '\'' +
                    ", realName='" + realName + '\'' +
                    ", phone='" + phone + '\'' +
                    ", city='" + city + '\'' +
                    ", deliveryAddress='" + deliveryAddress + '\'' +
                    ", amount=" + amount +
                    ", orderNumberId='" + orderNumberId + '\'' +
                    ", orderCount='" + orderCount + '\'' +
                    ", foodName='" + foodName + '\'' +
                    ", coordinatesX='" + coordinatesX + '\'' +
                    ", coordinatesY='" + coordinatesY + '\'' +
                    '}';
        }


    }
}
