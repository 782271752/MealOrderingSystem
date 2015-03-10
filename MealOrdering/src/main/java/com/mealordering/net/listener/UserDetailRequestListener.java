package com.mealordering.net.listener;

import android.app.Activity;

import com.mealordering.AppContext;
import com.mealordering.net.model.UserDetailResult;
import com.mealordering.utils.ToastUtil;

public class UserDetailRequestListener extends SimpleRequestListener<UserDetailResult> {
    Activity activity;

    public UserDetailRequestListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onRequestSuccess(UserDetailResult result) {
        super.onRequestSuccess(result);
        ToastUtil.showShortTimeMsg(result.getMessage());
        if (result.isSuccess()) {
            AppContext.getInstance().setUserDetail(result.getData());
            activity.finish();
        }
    }
}