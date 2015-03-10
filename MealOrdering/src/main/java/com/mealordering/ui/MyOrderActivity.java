package com.mealordering.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckedTextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.mealordering.Intents;
import com.mealordering.R;
import com.mealordering.adapter.TemplateAdapter;
import com.mealordering.adapter.item.MyOrderItemBuilder;
import com.mealordering.net.RequestHelper;
import com.mealordering.net.listener.SimpleRequestListener;
import com.mealordering.net.model.MyOrderResult;
import com.mealordering.utils.ToastUtil;
import com.mealordering.utils.ViewUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MyOrderActivity extends TitleSubActivity implements View.OnClickListener, AdapterView.OnItemClickListener
        , PullToRefreshBase.OnRefreshListener {

    @InjectView(R.id.list_lv)
    PullToRefreshListView mMyOrderLv;
    private CheckedTextView mLastCheckedCtv;
    private List<MyOrderResult.MyOrder> mMyOrdersGoing;
    private List<MyOrderResult.MyOrder> mMyOrdersFinished;
    private TemplateAdapter<MyOrderResult.MyOrder> mMyOrderAdapter;

    /**
     * 判断焦点是否在 完成 页面
     */
    private boolean focusFinish=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        ButterKnife.inject(this);

        mMyOrderAdapter = new TemplateAdapter<MyOrderResult.MyOrder>(new MyOrderItemBuilder(), mMyOrdersGoing);
        mMyOrderLv.setAdapter(mMyOrderAdapter);
        mMyOrderLv.setOnItemClickListener(this);
        mMyOrderLv.setOnRefreshListener(this);
        ViewUtil.findViewAndClick(this, R.id.my_order_state_going_ctv, this);
        ViewUtil.findViewAndClick(this, R.id.my_order_state_finished_ctv, this);

        CheckedTextView unusedCtv = ViewUtil.findView(this, R.id.my_order_state_going_ctv);
        unusedCtv.setChecked(true);
        mLastCheckedCtv = unusedCtv;

//        Collections2.filter(mMyOrdersGoing, new Predicate<MyOrderItem>() {
//            @Override
//            public boolean apply(@Nullable MyOrderItem input) {
//                return false;
//            }
//        });

        onClick(mLastCheckedCtv);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(focusFinish){
            RequestHelper.exeMyOrderRequest(getSpiceManager(), new MyOrderRequestListener(), "5", "0");
        }

    }

    @Override
    public void onClick(View v) {
        if (v instanceof CheckedTextView) {
            if (mLastCheckedCtv != null) {
                mLastCheckedCtv.setChecked(false);
                mLastCheckedCtv.setTextColor(getResources().getColor(R.color.light_grey));
            }
            CheckedTextView curCheckedTextView = (CheckedTextView) v;
            curCheckedTextView.setChecked(true);
            curCheckedTextView.setTextColor(getResources().getColor(R.color.red));
            mLastCheckedCtv = curCheckedTextView;
        }
        MyOrderResult.MyOrder myOrder = mMyOrderAdapter.getItem(mMyOrderAdapter.getCount() - 1);
//        String orderId = myOrder == null ? "0" : myOrder.getOrderId();
        String orderId = "0";
        switch (v.getId()) {
            case R.id.my_order_state_going_ctv:
                RequestHelper.exeMyOrderRequest(getSpiceManager(), new MyOrderRequestListener(), "0,1,2,3,4", orderId);
                focusFinish=false;
                break;
            case R.id.my_order_state_finished_ctv:
                RequestHelper.exeMyOrderRequest(getSpiceManager(), new MyOrderRequestListener(), "5", orderId);
                focusFinish=true;

                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MyOrderResult.MyOrder item = (MyOrderResult.MyOrder) parent.getItemAtPosition(position);
//        if (item.type == 1) {
//            Intents.launchOrderComment(this);
//        } else if (item.type == 2) {
//
//        } else {
        Intents.launchOrderDetail(this, item);
//        }
    }

    @Override
    public void onRefresh(PullToRefreshBase pullToRefreshBase) {
        onClick(mLastCheckedCtv);
    }

    private class MyOrderRequestListener extends SimpleRequestListener<MyOrderResult> {
        public MyOrderRequestListener() {
            super(ViewUtil.findView(MyOrderActivity.this, R.id.loading_pb));
        }

        @Override
        public void onRequestSuccess(MyOrderResult result) {
            super.onRequestSuccess(result);
            ToastUtil.showShortTimeMsg(result.getMessage());
            if (result.isSuccess()) {
                mMyOrderAdapter.setList(result.getData());
            }
        }

        @Override
        public void onRequestStart() {
            super.onRequestStart();
            mMyOrderAdapter.setList(null);
            mMyOrderLv.setRefreshing();
        }

        @Override
        public void onRequestComplete() {
            super.onRequestComplete();
            mMyOrderLv.onRefreshComplete();
        }
    }
}
