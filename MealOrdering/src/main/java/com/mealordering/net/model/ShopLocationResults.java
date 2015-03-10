package com.mealordering.net.model;

import com.google.api.client.util.Key;

import java.util.List;

/**
 * Created by Anthonit on 2014/4/22.
 */
public class ShopLocationResults extends Results<ShopLocationResults.ShopLocation> {
    public String getMessage() {
        String message = null;
        switch (result) {
            case 0:
                message = "读取商铺信息失败,请重试!";
                break;
            case 1:
                message = "";
                break;
        }
        return message;
    }
    public static class ShopLocation {
        @Key("ShopName")
        private String shopName;
        @Key("ShopAddress")
        private String shopAddress;
        @Key("Coordinates_x")
        private double coordinatesX;
        @Key("Coordinates_y")
        private double coordinatesY;
        @Key("ShopPhone")
        private List<ShopPhone> shopPhone;

        @Override
        public String toString() {
            return "ShopLocation{" +
                    "ShopName='" + shopName + '\'' +
                    ", ShopAddress='" + shopAddress + '\'' +
                    ", coordinatesX=" + coordinatesX +
                    ", coordinatesY=" + coordinatesY +
                    '}';
        }

        public String getShopName() {
            return shopName;
        }

        public String getShopAddress() {
            return shopAddress;
        }

        public double getCoordinatesX() {
            return coordinatesX;
        }

        public double getCoordinatesY() {
            return coordinatesY;
        }

        public List<ShopPhone> getShopPhone() {
            return shopPhone;
        }

        public static class ShopPhone {
            @Key("Phone")
            private String phone;

            @Override
            public String toString() {
                return "ShopPhone{" +
                        "phone=" + phone +
                        '}';
            }

            public String getPhone() {
                return phone;
            }
        }

    }
}
