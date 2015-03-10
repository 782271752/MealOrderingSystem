package com.mealordering.net.model;

import com.google.api.client.util.Key;

import java.util.List;

/**
 * Created by Anthonit on 2014/4/21.
 */
public class FoodDiyDetailResult extends Result<FoodDiyDetailResult.FoodDiyDetail> {

    public static class FoodDiyDetail {
        @Key("FoodDiyId")
        private String foodDiyId;
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
        @Key("FoodDiyType")
        private List<FoodDiyType> foodDiyType;

        public static class FoodDiyType {
            @Key("FoodDiyTypeId")
            private String foodDiyTypeId;
            @Key("FoodDiyTypeName")
            private String foodDiyTypeName;
            @Key("Less")
            private int less;
            @Key("More")
            private int more;
            @Key("FoodDiyMaterial")
            private List<FoodDiyMaterial> foodDiyMaterial;
            private int hasCheckedCount;

            @Override
            public String toString() {
                return "FoodDiyType{" +
                        "foodDiyTypeId='" + foodDiyTypeId + '\'' +
                        ", foodDiyTypeName='" + foodDiyTypeName + '\'' +
                        ", less=" + less +
                        ", more=" + more +
                        ", foodDiyMaterial=" + foodDiyMaterial +
                        ", hasCheckedCount=" + hasCheckedCount +
                        '}';
            }

            public String getFoodDiyTypeId() {
                return foodDiyTypeId;
            }

            public void setFoodDiyTypeId(String foodDiyTypeId) {
                this.foodDiyTypeId = foodDiyTypeId;
            }

            public String getFoodDiyTypeName() {
                return foodDiyTypeName;
            }

            public void setFoodDiyTypeName(String foodDiyTypeName) {
                this.foodDiyTypeName = foodDiyTypeName;
            }

            public int getLess() {
                return less;
            }

            public void setLess(int less) {
                this.less = less;
            }

            public int getMore() {
                return more;
            }

            public void setMore(int more) {
                this.more = more;
            }

            public List<FoodDiyMaterial> getFoodDiyMaterial() {
                return foodDiyMaterial;
            }

            public void setFoodDiyMaterial(List<FoodDiyMaterial> foodDiyMaterial) {
                this.foodDiyMaterial = foodDiyMaterial;
            }

            public int getHasCheckedCount() {
                return hasCheckedCount;
            }

            public void setHasCheckedCount(int hasCheckedCount) {
                this.hasCheckedCount = hasCheckedCount;
            }

            public static class FoodDiyMaterial {
                @Key("FoodDiyMaterialId")
                private String foodDiyMaterialId;
                @Key("FoodDiyMaterialName")
                private String foodDiyMaterialName;
                @Key("Price")
                private float price;

                @Override
                public String toString() {
                    return "FoodDiyMaterial{" +
                            "foodDiyMaterialId='" + foodDiyMaterialId + '\'' +
                            ", foodDiyMaterialName='" + foodDiyMaterialName + '\'' +
                            ", price=" + price +
                            '}';
                }

                public String getFoodDiyMaterialId() {
                    return foodDiyMaterialId;
                }

                public String getFoodDiyMaterialName() {
                    return foodDiyMaterialName;
                }

                public float getPrice() {
                    return price;
                }
            }
        }

        @Override
        public String toString() {
            return "FoodDiyDetail{" +
                    "foodDiyId=" + foodDiyId +
                    ", foodName='" + foodName + '\'' +
                    ", img='" + img + '\'' +
                    ", price=" + price +
                    ", remark='" + remark + '\'' +
                    ", parameterOne=" + parameterOne +
                    ", foodDiyType=" + foodDiyType +
                    '}';
        }

        public String getFoodDiyId() {
            return foodDiyId;
        }

        public String getFoodName() {
            return foodName;
        }

        public String getImg() {
            return img;
        }

        public float getPrice() {
            return price;
        }

        public String getRemark() {
            return remark;
        }

        public int getParameterOne() {
            return parameterOne;
        }

        public List<FoodDiyType> getFoodDiyType() {
            return foodDiyType;
        }
    }
}
