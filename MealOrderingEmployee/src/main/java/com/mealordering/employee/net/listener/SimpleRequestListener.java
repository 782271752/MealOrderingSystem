package com.mealordering.employee.net.listener;

import android.app.Dialog;
import android.view.View;

import com.mealordering.employee.utils.ToastUtil;
import com.mealordering.employee.utils.ViewUtil;
import com.octo.android.robospice.persistence.exception.SpiceException;

public class SimpleRequestListener<RESULT> extends BaseRequestListener<RESULT> {
    private View mProgressView;
    private Dialog dialog;

    public SimpleRequestListener() {

    }

    public SimpleRequestListener(View progressView) {
        mProgressView = progressView;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void onRequestStart() {
        super.onRequestStart();
        ViewUtil.setInvisible(mProgressView, false);
        if (dialog != null) {
            dialog.show();
        }
    }

    @Override
    public void onRequestComplete() {
        super.onRequestComplete();
        ViewUtil.setInvisible(mProgressView, true);
        if (dialog != null) {
            dialog.dismiss();
        }
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
