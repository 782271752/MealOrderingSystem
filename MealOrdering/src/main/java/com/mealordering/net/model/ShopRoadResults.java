package com.mealordering.net.model;

import com.google.api.client.util.Key;

/**
 * Created by Anthonit on 2014/4/26.
 */
public class ShopRoadResults extends Results<ShopRoadResults.ShopRoad> {
    public static class ShopRoad {
        @Key("Road")
        String Road;

        public String getRoad() {
            return Road;
        }

        @Override
        public String toString() {
            return "ShopRoad{" +
                    "Road='" + Road + '\'' +
                    '}';
        }
    }
}
