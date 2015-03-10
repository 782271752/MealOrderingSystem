package com.mealordering.adapter.item;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mealordering.R;
import com.mealordering.adapter.TemplateAdapter;
import com.mealordering.net.model.MyOrderResult;
import com.mealordering.utils.ViewUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Anthony on 14-2-14.
 */
public class MyOrderNumberIdBuilder implements ViewItemBuilder<MyOrderResult.MyOrder.OrderNumberId> {
    private TemplateAdapter<MyOrderResult.MyOrder.OrderNumberId> mAdapter;

    @Override
    public View createView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.list_item_order_confirm, null);
        return view;
    }

    @Override
    public void populateView(TemplateAdapter<MyOrderResult.MyOrder.OrderNumberId> adapter, View view, int position, MyOrderResult.MyOrder.OrderNumberId item) {
        mAdapter = adapter;
        Resources resources = view.getResources();
        ViewHolder holder = view.getTag() == null ? new ViewHolder(view) : (ViewHolder) view.getTag();
        ViewUtil.setText(holder.nameTv, item.getFoodName());
        ViewUtil.setText(holder.amountTv, item.getOrderCount() == 0 ? "" : resources.getString(R.string.buy_amount, item.getOrderCount()));
        ViewUtil.setText(holder.priceTv, item.getTotal().length() == 0 ? "" : resources.getString(R.string.money, item.getTotal()));
//        ViewUtil.setGone(holder.priceTv, TextUtils.isEmpty(item.getTotal()));
    }


    static class ViewHolder {
        @InjectView(R.id.order_food_name_tv)
        TextView nameTv;
        @InjectView(R.id.order_food_amount_tv)
        TextView amountTv;
        @InjectView(R.id.order_food_price_tv)
        TextView priceTv;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
