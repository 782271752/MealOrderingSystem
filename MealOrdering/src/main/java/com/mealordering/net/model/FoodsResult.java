package com.mealordering.net.model;

import com.google.api.client.util.Key;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Anthonit on 2014/4/21.
 */
public class FoodsResult extends Results<FoodsResult.Food> {

    @DatabaseTable
    public static class Food implements Serializable {
        @Key("FoodId")
        @DatabaseField(id = true, canBeNull = false, index = true)
        private String foodId;

        @Key("FoodName")
        @DatabaseField
        private String foodName;

        @Key("Price")
        @DatabaseField
        private float price;

        @Key("Img")
        @DatabaseField
        private String img;

        @Key("ParameterOne")
        @DatabaseField
        private int parameterOne;

        @Key("a")
        @DatabaseField
        private int a;

        @DatabaseField
        private int amount = 1;

        @Override
        public String toString() {
            return "FoodType{" +
                    "foodId=" + foodId +
                    ", foodName='" + foodName + '\'' +
                    ", price=" + price +
                    ", img='" + img + '\'' +
                    ", parameterOne=" + parameterOne +
                    ", a=" + a +
                    ", amount=" + amount +
                    '}';
        }

        public String getFoodId() {
            return foodId;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public void setFoodId(String foodId) {
            this.foodId = foodId;
        }

        public String getFoodName() {
            return foodName;
        }

        public float getPrice() {
            return price;
        }

        public String getImg() {
            return img;
        }

        public int getParameterOne() {
            return parameterOne;
        }

        public int getA() {
            return a;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }
    }
}
