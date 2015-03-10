package com.mealordering.net.listener;

import android.view.View;

import com.mealordering.utils.ToastUtil;
import com.mealordering.utils.ViewUtil;
import com.octo.android.robospice.persistence.exception.SpiceException;

public class SimpleRequestListener<RESULT> extends BaseRequestListener<RESULT> {
    private View mProgressView;

    public SimpleRequestListener() {

    }

    public SimpleRequestListener(View progressView) {
        mProgressView = progressView;
    }

    @Override
    public void onRequestStart() {
        super.onRequestStart();
        ViewUtil.setInvisible(mProgressView, false);
    }

    @Override
    public void onRequestComplete() {
        super.onRequestComplete();
        ViewUtil.setInvisible(mProgressView, true);
    }

    @Override
    public void onRequestFailure(SpiceException exception) {
        super.onRequestFailure(exception);
        ToastUtil.showLongTimeMsg("获取数据失败 , 请稍后重试 !");
    }

    @Override
    public void onRequestSuccess(RESULT result) {
        super.onRequestSuccess(result);
    }

}
