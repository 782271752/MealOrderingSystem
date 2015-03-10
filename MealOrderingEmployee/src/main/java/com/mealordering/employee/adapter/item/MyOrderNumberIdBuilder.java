package com.mealordering.employee.adapter.item;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mealordering.employee.R;
import com.mealordering.employee.adapter.TemplateAdapter;
import com.mealordering.employee.net.model.OrderNumberId;
import com.mealordering.employee.utils.ViewUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Anthony on 14-2-14.
 */
public class MyOrderNumberIdBuilder implements ViewItemBuilder<OrderNumberId> {
    private TemplateAdapter<OrderNumberId> mAdapter;

    @Override
    public View createView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.list_item_order_number_id, null);
        return view;
    }

    @Override
    public void populateView(TemplateAdapter<OrderNumberId> adapter, View view, int position, OrderNumberId item) {
        mAdapter = adapter;
        ViewHolder holder = view.getTag() == null ? new ViewHolder(view) : (ViewHolder) view.getTag();
        ViewUtil.setText(holder.nameTv, item.getFoodName());
        ViewUtil.setText(holder.amountTv, String.valueOf(item.getOrderCount()));
        ViewUtil.setText(holder.priceTv, view.getResources().getString(R.string.money, item.getTotal()));
//        ViewUtil.setText(holder.priceTv, String.valueOf(item.getPrice()));
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
