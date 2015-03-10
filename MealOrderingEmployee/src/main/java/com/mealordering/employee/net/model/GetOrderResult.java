package com.mealordering.employee.net.model;

/**
 * Created by Anthonit on 2014/4/26.
 */
public class GetOrderResult extends Result<Object> {
    @Override
    public String getMessage() {
        String message = null;
        switch (result) {
            case 0:
                message = "领取失败!";
                break;
            case 1:
                message = "领取成功!";
                break;
            case 2:
                message = "该张订单已被领取了，不能再领取!";
                break;
            case 3:
                message = "查询不到该订单号!";
                break;
        }
        return message;
    }
}
