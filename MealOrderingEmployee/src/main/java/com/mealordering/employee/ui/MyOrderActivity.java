package com.mealordering.employee.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckedTextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.mealordering.employee.AppContext;
import com.mealordering.employee.Intents;
import com.mealordering.employee.R;
import com.mealordering.employee.adapter.TemplateAdapter;
import com.mealordering.employee.adapter.item.MyOrderItemBuilder;
import com.mealordering.employee.net.RequestHelper;
import com.mealordering.employee.net.listener.SimpleRequestListener;
import com.mealordering.employee.net.model.OrderResults;
import com.mealordering.employee.utils.DClickExit;
import com.mealordering.employee.utils.ToastUtil;
import com.mealordering.employee.utils.ViewUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MyOrderActivity extends TitleActivity implements View.OnClickListener, AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener {

    @InjectView(R.id.list_view)
    PullToRefreshListView mMyOrderLv;
    private CheckedTextView mLastCheckedCtv;
    private TemplateAdapter<OrderResults.Order> mOrderAdapter;
    private DClickExit mDClickExit;
    private MyOrderItemBuilder mMyOrderItemBuilder;

    @Override
    protected void onTitleBtnClick(View view) {
        Intents.launchGetOrder(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        setTitle("我的订单");
        setButtonTitle("派送领单");
        mDClickExit = new DClickExit(this);
        ButterKnife.inject(this);

        mMyOrderItemBuilder = new MyOrderItemBuilder(this);

        mOrderAdapter = new TemplateAdapter<OrderResults.Order>(mMyOrderItemBuilder);
        mMyOrderLv.setAdapter(mOrderAdapter);
        mMyOrderLv.setOnItemClickListener(this);
        mMyOrderLv.setOnRefreshListener(this);
        ViewUtil.findViewAndClick(this, R.id.my_order_state_going_ctv, this);
        ViewUtil.findViewAndClick(this, R.id.my_order_state_finished_ctv, this);

        CheckedTextView unusedCtv = ViewUtil.findView(this, R.id.my_order_state_going_ctv);
        unusedCtv.setChecked(true);
        mLastCheckedCtv = unusedCtv;
        onClick(mLastCheckedCtv);

        AppContext.getInstance().startUploadLocation();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (mLastCheckedCtv.getId() == R.id.my_order_state_going_ctv) {
            onClick(mLastCheckedCtv);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "注销");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intents.launchLogin(this);
        AppContext.getInstance().setEmployeeId(null);
        finish();
        return super.onOptionsItemSelected(item);
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
        String orderState = "";
        switch (v.getId()) {
            case R.id.my_order_state_going_ctv:
                orderState = "3";
                mMyOrderItemBuilder.setFinish(false);
                break;
            case R.id.my_order_state_finished_ctv:
                orderState = "5";
                mMyOrderItemBuilder.setFinish(true);
                break;

        }
        String orderId = "0";

        RequestHelper.exeOrdersRequest(this, getSpiceManager(), new MyOrderRequestListener(), orderState, orderId);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return mDClickExit.doubleClickExit(keyCode);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        OrderResults.Order item = (OrderResults.Order) parent.getItemAtPosition(position);
        Intents.launchOrderDetail(this, item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (mLastCheckedCtv.getId() == R.id.my_order_state_going_ctv) {
                onClick(mLastCheckedCtv);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppContext.getInstance().stopUploadLocation();
    }

    @Override
    public void onRefresh(PullToRefreshBase pullToRefreshBase) {
        onClick(mLastCheckedCtv);
    }

    private class MyOrderRequestListener extends SimpleRequestListener<OrderResults> {
        MyOrderRequestListener() {
            super(ViewUtil.findView(MyOrderActivity.this, R.id.loading_pb));
        }

        @Override
        public void onRequestComplete() {
            super.onRequestComplete();
            mMyOrderLv.onRefreshComplete();
        }

        @Override
        public void onRequestSuccess(OrderResults result) {
            super.onRequestSuccess(result);

            if (result.isSuccess()) {
                mOrderAdapter.setList(result.getData());
            }
            if (result.getData().isEmpty()) {
                ToastUtil.showLongTimeMsg("无订单!");
                mOrderAdapter.setList(null);
            }
        }
    }
}