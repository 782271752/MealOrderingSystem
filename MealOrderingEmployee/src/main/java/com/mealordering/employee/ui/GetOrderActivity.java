package com.mealordering.employee.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.mealordering.employee.R;
import com.mealordering.employee.net.RequestHelper;
import com.mealordering.employee.net.listener.SimpleRequestListener;
import com.mealordering.employee.net.model.GetOrderResult;
import com.mealordering.employee.utils.ToastUtil;
import com.mealordering.employee.utils.ViewUtil;

public class GetOrderActivity extends TitleActivity implements View.OnClickListener {

    @Override
    protected void onTitleBtnClick(View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_order);
        setTitle("派送领单");
        setButtonTitle("返回");
        ViewUtil.findViewAndClick(this, R.id.get_order_btn, this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_order_btn:
                EditText editText = ViewUtil.findView(this, R.id.get_order_order_id_et);
                if (ViewUtil.checkEmpty(editText, "请输入订单号!")) return;
                String orderNo = editText.getText().toString();
                RequestHelper.exeGetDispatchOrderRequest(this, getSpiceManager(), new GetOrderRequestListener(), orderNo);
                break;
        }
    }

    private class GetOrderRequestListener extends SimpleRequestListener<GetOrderResult> {
        @Override
        public void onRequestSuccess(GetOrderResult result) {
            super.onRequestSuccess(result);
            ToastUtil.showShortTimeMsg(result.getMessage());
            if (result.isSuccess()) {
                setResult(RESULT_OK);
                finish();
            }
        }
    }
}
