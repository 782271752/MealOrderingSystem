package com.mealordering.net.model;

import com.google.api.client.util.Key;

/**
 * Created by Anthonit on 2014/4/21.
 */
public class FoodTypesResult extends Results<FoodTypesResult.FoodType> {

    public static class FoodType {
        @Key("FoodTypeId")
        private String foodTypeId;
        @Key("FoodTypeName")
        private String foodTypeName;

        public FoodType() {
        }

        public FoodType(String foodTypeId, String foodTypeName) {
            this.foodTypeId = foodTypeId;
            this.foodTypeName = foodTypeName;
        }

        @Override
        public String toString() {
            return "FoodType{" +
                    "foodTypeId=" + foodTypeId +
                    ", foodTypeName='" + foodTypeName + '\'' +
                    '}';
        }

        public String getFoodTypeId() {
            return foodTypeId;
        }

        public void setFoodTypeId(String foodTypeId) {
            this.foodTypeId = foodTypeId;
        }

        public String getFoodTypeName() {
            return foodTypeName;
        }

        public void setFoodTypeName(String foodTypeName) {
            this.foodTypeName = foodTypeName;
        }
    }
}
