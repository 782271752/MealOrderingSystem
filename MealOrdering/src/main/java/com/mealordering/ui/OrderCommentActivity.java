package com.mealordering.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mealordering.Intents;
import com.mealordering.R;
import com.mealordering.net.RequestHelper;
import com.mealordering.net.listener.SimpleRequestListener;
import com.mealordering.net.model.EmptyResults;
import com.mealordering.net.model.MyOrderResult;
import com.mealordering.utils.ToastUtil;
import com.mealordering.utils.ViewUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class OrderCommentActivity extends TitleSubActivity implements View.OnClickListener {

    @InjectView(R.id.order_comment_order_id_tv)
    TextView mtOrderIdTv;
    @InjectView(R.id.order_comment_content_rb)
    EditText mtContentRb;
    @InjectView(R.id.order_comment_service_attitude_rg)
    RadioGroup mServiceAttitudeRg;
    @InjectView(R.id.order_comment_food_rg)
    RadioGroup mFoodRg;
    @InjectView(R.id.order_comment_delivery_speed_rg)
    RadioGroup mtDeliverySpeedRg;
    private MyOrderResult.MyOrder mMyOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_comment);
        ButterKnife.inject(this);
        ViewUtil.findViewAndClick(this, R.id.btn_ok, this);
        mMyOrder = (MyOrderResult.MyOrder) getIntent().getSerializableExtra(Intents.ITEM);
        mtOrderIdTv.setText(mMyOrder.getOrderNo());
    }

    @Override
    public void onClick(View v) {
        String orderId = mMyOrder.getOrderId();
        int attitude = mServiceAttitudeRg.indexOfChild(mServiceAttitudeRg.findViewById(mServiceAttitudeRg.getCheckedRadioButtonId()));
        int taste = mFoodRg.indexOfChild(mFoodRg.findViewById(mFoodRg.getCheckedRadioButtonId()));
        int speed = mtDeliverySpeedRg.indexOfChild(mtDeliverySpeedRg.findViewById(mtDeliverySpeedRg.getCheckedRadioButtonId()));
//        if (ViewUtil.checkEmpty(mtContentRb, "请输入" + mtContentRb.getHint())) {
//            return;
//        }
        String evaluation = mtContentRb.getText().toString()+"。";
        RequestHelper.exeOrderCommentRequest(getSpiceManager(), new OrderCommentRequestListener(),
                orderId, attitude, taste, speed, evaluation);
    }

    private class OrderCommentRequestListener extends SimpleRequestListener<EmptyResults> {
        @Override
        public void onRequestSuccess(EmptyResults result) {
            if (result.isSuccess()) {
                ToastUtil.showLongTimeMsg("评价成功!");
                finish();
            } else {
                ToastUtil.showLongTimeMsg("评价失败!");
            }
            super.onRequestSuccess(result);
        }
    }
}
