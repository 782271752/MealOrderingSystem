package com.mealordering.net.model;

import com.google.api.client.util.Key;

/**
 * Created by Anthonit on 2014/4/22.
 */
public class DemoResult extends Result<DemoResult.Demo> {
    public static class Demo {
        @Key("ShopName")
        private String ShopName;
    }
}
