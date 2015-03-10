package com.mealordering.employee.adapter.item;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.mealordering.employee.Intents;
import com.mealordering.employee.R;
import com.mealordering.employee.adapter.TemplateAdapter;
import com.mealordering.employee.net.RequestHelper;
import com.mealordering.employee.net.listener.SimpleRequestListener;
import com.mealordering.employee.net.model.ConfirmDispathOrderResult;
import com.mealordering.employee.net.model.OrderNumberId;
import com.mealordering.employee.net.model.OrderResults;
import com.mealordering.employee.ui.BaseActivity;
import com.mealordering.employee.utils.ToastUtil;
import com.mealordering.employee.utils.ViewUtil;

/**
 * Created by Anthony on 14-2-14.
 */
public class MyOrderItemBuilder implements ViewItemBuilder<OrderResults.Order>, View.OnClickListener {
    private boolean isFinish;
    private TemplateAdapter<OrderResults.Order> adapter;
    private BaseActivity activity;

    public MyOrderItemBuilder(BaseActivity activity) {
        this.activity = activity;
    }

    @Override
    public View createView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.list_item_order_delivering, null);
        ViewUtil.findViewAndClick(view, R.id.order_delivery_map_btn, this);
        ViewUtil.findViewAndClick(view, R.id.order_delivery_finish_btn, this);
        return view;
    }

    @Override
    public void populateView(TemplateAdapter<OrderResults.Order> adapter, View view, int position, OrderResults.Order item) {
        this.adapter = adapter;
        String username_phone = item.getRealName() +
                (TextUtils.isEmpty(item.getPhone()) ? "" : String.format(" (%s)", item.getPhone()));
        ViewUtil.findTextViewAndSetText(view, R.id.order_username_phone_tv, username_phone);
        ViewUtil.findTextViewAndSetText(view, R.id.order_address_tv, item.getDeliveryAddress());
        ViewUtil.findTextViewAndSetText(view, R.id.order_amount_tv, view.getResources().getString(R.string.money, item.getAmount()));
        StringBuilder foods = new StringBuilder();
        for (OrderNumberId orderNumberId : item.getOrderNumberId()) {
            foods.append(orderNumberId.getFoodName());
            foods.append("*");
            foods.append(orderNumberId.getOrderCount());
            foods.append(",");
        }
        if (foods.length() > 0)
            foods.deleteCharAt(foods.length() - 1);
        ViewUtil.findTextViewAndSetText(view, R.id.order_foods_tv, foods.toString());

        Button mapBtn = ViewUtil.findView(view, R.id.order_delivery_map_btn);
        Button finishBtn = ViewUtil.findView(view, R.id.order_delivery_finish_btn);
        ViewUtil.setGone(mapBtn, isFinish);
        ViewUtil.setGone(finishBtn, isFinish);
        mapBtn.setTag(item);
        finishBtn.setTag(item);
    }

    @Override
    public void onClick(View v) {
        OrderResults.Order item = (OrderResults.Order) v.getTag();
        switch (v.getId()) {
            case R.id.order_delivery_map_btn:
//                try {
//                    Uri uri = Uri.parse(String.format("geo:%s,%s", item.getCoordinatesX(), item.getCoordinatesY()));
//                    Intent it = new Intent(Intent.ACTION_VIEW, uri);
//                    activity.startActivity(it);
//                } catch (ActivityNotFoundException exception) {
//                    ToastUtil.showLongTimeMsg("无可用的地图,请下载安装一个!");
//                }
                Intents.launchMap(activity, item);
                break;
            case R.id.order_delivery_finish_btn:
                RequestHelper.exeConfirmDispatchOrderRequest(activity, activity.getSpiceManager(),
                        new ConfirmDispatchOrderRequestListener(item), item.getOrderId());
                break;
        }
    }

    public void setFinish(boolean isFinish) {
        this.isFinish = isFinish;
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
                adapter.getList().remove(order);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
