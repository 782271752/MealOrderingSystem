package com.mealordering.employee.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.mealordering.employee.Intents;
import com.mealordering.employee.R;
import com.mealordering.employee.adapter.TemplateAdapter;
import com.mealordering.employee.adapter.item.MyOrderNumberIdBuilder;
import com.mealordering.employee.net.RequestHelper;
import com.mealordering.employee.net.listener.SimpleRequestListener;
import com.mealordering.employee.net.model.ConfirmDispathOrderResult;
import com.mealordering.employee.net.model.OrderDetailResult;
import com.mealordering.employee.net.model.OrderNumberId;
import com.mealordering.employee.net.model.OrderResults;
import com.mealordering.employee.utils.ToastUtil;
import com.mealordering.employee.utils.ViewUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class OrderDetailActivity extends TitleActivity implements View.OnClickListener {
    @InjectView(R.id.list_view)
    protected ListView mListView;
    OrderDetailResult.OrderDetail mOrderDetail;
    private TemplateAdapter<OrderNumberId> mOrderNumberIdAdapter;
    private Button mDeliveryBtn;
    private OrderResults.Order mOrder;

    @Override
    protected void onTitleBtnClick(View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        setButtonTitle("返回");
        ButterKnife.inject(this);

        mDeliveryBtn = ViewUtil.findView(this, R.id.order_detail_delivery_btn);
        mDeliveryBtn.setOnClickListener(this);
        mOrderNumberIdAdapter = new TemplateAdapter<OrderNumberId>(new MyOrderNumberIdBuilder());
        mListView.setAdapter(mOrderNumberIdAdapter);

        mOrder = (OrderResults.Order) getIntent().getSerializableExtra(Intents.ITEM);
        RequestHelper.exeOrderDetailRequest(this, getSpiceManager(), new OrderDetailRequestListener(), mOrder.getOrderId());
    }

    private void populateView(OrderDetailResult.OrderDetail orderDetail) {
        mOrderDetail = orderDetail;
        ViewUtil.findTextViewAndSetText(this, R.id.order_detail_id_tv, orderDetail.getOrderId());
        ViewUtil.findTextViewAndSetText(this, R.id.order_detail_total_cost_tv,
                getString(R.string.money, orderDetail.getAmount()));
        ViewUtil.findTextViewAndSetText(this, R.id.order_detail_time_tv, orderDetail.getOrderTime());
        updateOrderState(orderDetail);
        mOrderNumberIdAdapter.setList(orderDetail.getOrderNumberId());

        ViewUtil.findTextViewAndSetText(this, R.id.order_detail_state_tv, orderDetail.getOrderStateText());
    }

    private void updateOrderState(OrderDetailResult.OrderDetail orderDetail) {
        String orderStateText = orderDetail.getOrderStateText();
        ViewUtil.findTextViewAndSetText(this, R.id.order_detail_state_tv, orderStateText);
        ViewUtil.setGone(mDeliveryBtn, orderDetail.getOrderState() == 5);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_detail_delivery_btn:
                RequestHelper.exeConfirmDispatchOrderRequest(this, getSpiceManager(),
                        new ConfirmDispatchOrderRequestListener(mOrder), mOrder.getOrderId());
                break;
        }
    }

    private class OrderDetailRequestListener extends SimpleRequestListener<OrderDetailResult> {
        @Override
        public void onRequestSuccess(OrderDetailResult result) {
            super.onRequestSuccess(result);
            if (result.isSuccess()) {
                populateView(result.getData().get(0));
            }
        }
    }

    private class ConfirmDispatchOrderRequestListener extends SimpleRequestListener<ConfirmDispathOrderResult> {
        OrderResults.Order order;

        private ConfirmDispatchOrderRequestListener(OrderResults.Order order) {
            this.order = order;
        }

        @Override
        public void onRequestSuccess(ConfirmDispathOrderResult result) {
            super.onRequestSuccess(result);
            ToastUtil.showShortTimeMsg(result.getMessage());
            if (result.isSuccess()) {
                mOrderDetail.setOrderState(5);
                updateOrderState(mOrderDetail);
                setResult(RESULT_OK);
            }
        }
    }
}
