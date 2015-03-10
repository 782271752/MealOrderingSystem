package com.mealordering.net.model;

import com.google.api.client.util.Key;

/**
 * Created by Anthonit on 2014/4/26.
 */
public class ShopIdResult extends Result<ShopIdResult.ShopId> {
    public String getMessage() {
        String message = null;
        switch (result) {
            case 0:
                message = "获取下单的店铺ID失败,请重试!!";
                break;
            case 1:
                message = "";
                break;
        }
        return message;
    }

    public static class ShopId {
        @Key("ShopId")
        private int shopId;
        @Key("Datetimes")
        private int datetimes;

        public int getShopId() {
            return shopId;
        }

        public int getDatetimes() {
            return datetimes;
        }

        public String getDatetimesMsg() {
//            Datetimes=0你就提示分店拒收，不走流程了，返回购物车
//                    Datetimes=1你就提示30分钟送达，正常走流程
//                    Datetimes=其他数字，你就提示数字分钟送达，正常走流程
            String msg = null;
            switch (datetimes) {
                case 0:
                    msg = "对不起,分店拒收,无法下单!";
                    break;
                case 1:
                    msg = "下单成功!30分钟送达.";
                    break;
                default:
                    msg = String.format("下单成功! %s分钟送达.", datetimes);
                    break;
            }
            return msg;
        }

        @Override
        public String toString() {
            return "ShopId{" +
                    "shopId=" + shopId +
                    ", datetimes=" + datetimes +
                    '}';
        }
    }
}
