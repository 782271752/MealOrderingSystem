package com.mealordering.net.model;

import com.google.api.client.util.Key;

/**
 * Created by Anthonit on 2014/4/22.
 */
public class UserDetailResult extends Result<UserDetailResult.UserDetail> {
    @Override
    public String getMessage() {
        String message = null;
        switch (result) {
            case 1:
                message = "登录成功!!";
                break;
        }
        return message;
    }

    public static class UserDetail {
        @Key("UserId")
        private String userId;
        @Key("Pwd")
        private String pwd;
        @Key("RealName")
        private String realName;
        @Key("Sex")
        private int sex;
        @Key("Phone")
        private String phone;
        @Key("Total")
        private float total;
        @Key("Integral")
        private int integral;
        @Key("GradeCount")
        private String gradeCount;
        @Key("State")
        private int state;
        @Key("IsBlack")
        private boolean isBlack;
        @Key("Coordinates_x")
        private String coordinatesX;
        @Key("Coordinates_y")
        private String coordinatesY;

        @Override
        public String toString() {
            return "UserDetail{" +
                    "userId='" + userId + '\'' +
                    ", pwd='" + pwd + '\'' +
                    ", realName='" + realName + '\'' +
                    ", sex='" + sex + '\'' +
                    ", phone='" + phone + '\'' +
                    ", total='" + total + '\'' +
                    ", integral='" + integral + '\'' +
                    ", gradeCount='" + gradeCount + '\'' +
                    ", state='" + state + '\'' +
                    ", isBlack='" + isBlack + '\'' +
                    ", coordinatesX='" + coordinatesX + '\'' +
                    ", coordinatesY='" + coordinatesY + '\'' +
                    '}';
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public float getTotal() {
            return total;
        }

        public void setTotal(float total) {
            this.total = total;
        }

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }

        public String getGradeCount() {
            return gradeCount;
        }

        public void setGradeCount(String gradeCount) {
            this.gradeCount = gradeCount;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public boolean isBlack() {
            return isBlack;
        }

        public void setBlack(boolean isBlack) {
            this.isBlack = isBlack;
        }

        public String getCoordinatesX() {
            return coordinatesX;
        }

        public void setCoordinatesX(String coordinatesX) {
            this.coordinatesX = coordinatesX;
        }

        public String getCoordinatesY() {
            return coordinatesY;
        }

        public void setCoordinatesY(String coordinatesY) {
            this.coordinatesY = coordinatesY;
        }
    }
}
