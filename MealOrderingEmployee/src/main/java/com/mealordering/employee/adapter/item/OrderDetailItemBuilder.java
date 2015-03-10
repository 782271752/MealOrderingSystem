package com.mealordering.employee.adapter.item;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mealordering.employee.R;
import com.mealordering.employee.adapter.TemplateAdapter;
import com.mealordering.employee.net.model.OrderResults;
import com.mealordering.employee.utils.ViewUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Anthony on 14-2-14.
 */
public class OrderDetailItemBuilder implements ViewItemBuilder<OrderResults.Order> {
    private TemplateAdapter<OrderResults.Order> mAdapter;

    @Override
    public View createView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.list_item_order_detail, null);
        return view;
    }

    @Override
    public void populateView(TemplateAdapter<OrderResults.Order> adapter, View view, int position, OrderResults.Order item) {
        mAdapter = adapter;
        ViewHolder holder = view.getTag() == null ? new ViewHolder(view) : (ViewHolder) view.getTag();
        ViewUtil.setText(holder.mNameTv, item.getFoodName());
        ViewUtil.setText(holder.mAmountTv, String.valueOf(item.getOrderCount()));
        ViewUtil.setText(holder.mPriceTv, String.valueOf(item.getAmount()));
    }


    static class ViewHolder {
        @InjectView(R.id.order_food_name_tv)
        TextView mNameTv;
        @InjectView(R.id.order_food_amount_tv)
        TextView mAmountTv;
        @InjectView(R.id.order_food_price_tv)
        TextView mPriceTv;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
