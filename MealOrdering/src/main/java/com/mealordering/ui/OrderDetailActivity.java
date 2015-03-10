package com.mealordering.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.mealordering.Intents;
import com.mealordering.R;
import com.mealordering.adapter.TemplateAdapter;
import com.mealordering.adapter.item.MyOrderNumberIdBuilder;
import com.mealordering.net.RequestHelper;
import com.mealordering.net.listener.SimpleRequestListener;
import com.mealordering.net.model.MyOrderDetailResult;
import com.mealordering.net.model.MyOrderResult;
import com.mealordering.utils.ToastUtil;
import com.mealordering.utils.ViewUtil;

public class OrderDetailActivity extends TitleSubActivity implements View.OnClickListener {

    ListView mListView;
    MyOrderDetailResult.MyOrderDetail mOrderDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        MyOrderResult.MyOrder myOrder = (MyOrderResult.MyOrder) getIntent().getSerializableExtra(Intents.ITEM);

        RequestHelper.exeMyOrderDetailRequest(getSpiceManager(), new MyOrderDetailRequestListener(), myOrder.getOrderId());
    }

    private void populateView(MyOrderDetailResult.MyOrderDetail orderDetail) {
        ViewUtil.findTextViewAndSetText(this, R.id.order_detail_id_tv, orderDetail.getOrderNo());
        ViewUtil.findTextViewAndSetText(this, R.id.order_detail_total_cost_tv,
                getString(R.string.money, orderDetail.getAmount()));
        ViewUtil.findTextViewAndSetText(this, R.id.order_detail_time_tv, orderDetail.getOrderTime());
        String orderStateText = orderDetail.getOrderStateText();
        ViewUtil.findTextViewAndSetText(this, R.id.order_detail_state_tv, orderStateText);

        mListView = ViewUtil.findView(this, R.id.listView);
        mListView.setAdapter(new TemplateAdapter<MyOrderResult.MyOrder.OrderNumberId>(new MyOrderNumberIdBuilder(),
                orderDetail.getOrderNumberId()));
        TextView locationTv = ViewUtil.findView(this, R.id.order_detail_location_tv);
        ViewUtil.setInvisible(locationTv, !orderDetail.canLocation());
        locationTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_detail_location_tv:
                Intents.launchMap(this, mOrderDetail.getOrderId());
                break;
        }
    }

    private class MyOrderDetailRequestListener extends SimpleRequestListener<MyOrderDetailResult> {
        MyOrderDetailRequestListener() {
            super(ViewUtil.findView(OrderDetailActivity.this, R.id.loading_pb));
        }

        @Override
        public void onRequestSuccess(MyOrderDetailResult result) {
            super.onRequestSuccess(result);
            if (result.isSuccess()) {
                mOrderDetail = result.getData().get(0);
                if (!TextUtils.isEmpty(mOrderDetail.getTitle())) {
                    mOrderDetail.getOrderNumberId().add(new MyOrderResult.MyOrder.OrderNumberId(0, "优惠 : " + mOrderDetail.getTitle(), ""));
                }

                mOrderDetail.getOrderNumberId().add(new MyOrderResult.MyOrder.OrderNumberId(0, "送餐费用", String.valueOf(mOrderDetail.getOrderMoney())));
                populateView(result.getData().get(0));
            } else {
                ToastUtil.showShortTimeMsg(result.getMessage());
            }
        }
    }
}
