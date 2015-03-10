package com.mealordering.net.model;

import com.google.api.client.util.Key;

/**
 * Created by Anthonit on 2014/4/22.
 */
public class MyAddressResult extends Results<MyAddressResult.MyAddress> {
    public static class MyAddress {
        @Key("cityId")
        private String cityId;
        @Key("City")
        private String city;
        @Key("Area")
        private String area;
        @Key("Road")
        private String road;
        @Key("Detailed")
        private String detailed;
        @Key("UserName")
        private String userName;
        @Key("UserPhone")
        private String userPhone;
        @Key("USex")
        private String usex;


        @Override
        public String toString() {
            return "MyAddress{" +
                    "cityId='" + cityId + '\'' +
                    ", city='" + city + '\'' +
                    ", area='" + area + '\'' +
                    ", road='" + road + '\'' +
                    ", detailed='" + detailed + '\'' +
                    ",userName='"+userName+'\''+
                    ",userPhone='"+userPhone+'\''+
                    ",usex='"+usex+'\''+
                    '}';
        }

        public String getCityId() {
            return cityId;
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

        public String getDetailed() {
            return detailed;
        }

        public String getUserName(){return userName;}

        public String getUserPhone(){return userPhone;}

        public String getUSex(){return usex;}

        public String getFullAddress() {
            return String.format("%s%s%s%s", city, area, road, detailed);
        }
    }
}
