package com.mealordering.employee.net.model;

/**
 * Created by Anthonit on 2014/4/26.
 */
public class ConfirmDispathOrderResult extends Result<Object> {
    @Override
    public String getMessage() {
        String message = null;
        switch (result) {
            case 0:
                message = "确认失败 !";
                break;
            case 1:
                message = "确认成功!";
                break;
        }
        return message;
    }
}
