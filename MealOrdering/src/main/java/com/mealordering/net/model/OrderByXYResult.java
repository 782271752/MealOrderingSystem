package com.mealordering.net.model;

import com.google.api.client.util.Key;

/**
 * Created by Anthonit on 2014/4/26.
 */
public class OrderByXYResult extends Result<OrderByXYResult.OrderByXY> {
    public static class OrderByXY {
        @Key("EmployeeId")
        private String employeeId;
        @Key("Coordinates_x")
        private String coordinates_x;
        @Key("Coordinates_y")
        private String coordinates_y;

        @Override
        public String toString() {
            return "OrderByXY{" +
                    "employeeId='" + employeeId + '\'' +
                    ", coordinates_x='" + coordinates_x + '\'' +
                    ", coordinates_y='" + coordinates_y + '\'' +
                    '}';
        }

        public String getEmployeeId() {
            return employeeId;
        }

        public float getCoordinatesX() {
            return Float.parseFloat(coordinates_x);
        }

        public float getCoordinatesY() {
            return Float.parseFloat(coordinates_y);
        }

    }
}
