package com.mealordering.net.model;

import com.google.api.client.util.Key;

import java.util.List;

/**
 * Created by Anthonit on 2014/5/14.
 */
public class CityResults extends Results<CityResults.City> {

    public static class City {
        @Key("City")
        private String city;
        @Key("Area")
        private List<Area> areas;

        @Override
        public String toString() {
            return "City{" +
                    "city='" + city + '\'' +
                    ", areas=" + areas +
                    '}';
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public List<Area> getAreas() {
            return areas;
        }

        public void setAreas(List<Area> areas) {
            this.areas = areas;
        }

        public static class Area {
            @Key("Area")
            private String area;
            @Key("Road")
            private List<Road> roads;

            @Override
            public String toString() {
                return "Area{" +
                        "area='" + area + '\'' +
                        ", roads=" + roads +
                        '}';
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public List<Road> getRoads() {
                return roads;
            }

            public void setRoads(List<Road> roads) {
                this.roads = roads;
            }

            public static class Road {
                @Key("Road")
                private String road;
                @Key("Address")
                private String address;
                @Key("ShopId")
                private String shopId;

                @Override
                public String toString() {
                    return "Road{" +
                            "road='" + road + '\'' +
                            ", address='" + address + '\'' +
                            ", shopId='" + shopId + '\'' +
                            '}';
                }

                public String getRoad() {
                    return road;
                }

                public void setRoad(String road) {
                    this.road = road;
                }

                public String getAddress() {
                    return address;
                }

                public void setAddress(String address) {
                    this.address = address;
                }

                public String getShopId() {
                    return shopId;
                }

                public void setShopId(String shopId) {
                    this.shopId = shopId;
                }
            }

        }
    }
}
