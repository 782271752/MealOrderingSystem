package com.mealordering.net.model;

import com.google.api.client.util.Key;

/**
 * Created by Anthonit on 2014/4/22.
 */
public class MyAddressDefaultResult extends Result<MyAddressDefaultResult.MyAddressDefault> {
    public String getMessage() {
        String message = null;
        switch (result) {
            case 0:
                message = "获取默认下单地址失败,请重试!!";
                break;
            case 1:
                message = "";
                break;
        }
        return message;
    }
    public static class MyAddressDefault {
        @Key("City")
        private String city;
        @Key("Area")
        private String area;
        @Key("Road")
        private String road;
        @Key("Address")
        private String address;
        @Key("UserName")
        private String userName;
        @Key("Usex")
        private int usex;
        @Key("UserPhone")
        private String userPhone;

        @Override
        public String toString() {
            return "MyAddressDefault{" +
                    "city='" + city + '\'' +
                    ", area='" + area + '\'' +
                    ", road='" + road + '\'' +
                    ", address='" + address + '\'' +
                    ", userName='" + userName + '\'' +
                    ", usex='" + usex + '\'' +
                    ", userPhone='" + userPhone + '\'' +
                    '}';
        }

        public String getCity() {
            return city;
        }

        public String getArea() {
            return area;
        }

        public String getRoad() {
            return road;
        }

        public String getAddress() {
            return address;
        }
        public String getFullAddress() {
            return String.format("%s%s%s%s", city, area, road, address);
        }
        public String getUserName() {
            return userName;
        }

        public int getUsex() {
            return usex;
        }

        public String getUserPhone() {
            return userPhone;
        }
    }
}
