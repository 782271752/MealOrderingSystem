package com.mealordering.net.model;

import com.google.api.client.util.Key;

/**
 * Created by Anthonit on 2014/4/21.
 */
public class FoodDetailResult extends Result<FoodDetailResult.FoodDetail> {

    public static class FoodDetail {
        @Key("FoodId")
        private int foodId;
        @Key("FoodName")
        private String foodName;
        @Key("Img")
        private String img;
        @Key("Price")
        private float price;
        @Key("Remark")
        private String remark;
        @Key("ParameterOne")
        private int parameterOne;

        @Override
        public String toString() {
            return "FoodDetail{" +
                    "foodId=" + foodId +
                    ", foodName=" + foodName +
                    ", img='" + img + '\'' +
                    ", price=" + price +
                    ", remark='" + remark + '\'' +
                    ", parameterOne=" + parameterOne +
                    '}';
        }

        public int getFoodId() {
            return foodId;
        }

        public void setFoodId(int foodId) {
            this.foodId = foodId;
        }

        public String getFoodName() {
            return foodName;
        }

        public void setFoodName(String foodName) {
            this.foodName = foodName;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getParameterOne() {
            return parameterOne;
        }

        public void setParameterOne(int parameterOne) {
            this.parameterOne = parameterOne;
        }
    }
}
