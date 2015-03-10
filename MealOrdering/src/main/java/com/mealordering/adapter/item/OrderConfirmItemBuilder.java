package com.mealordering.adapter.item;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mealordering.R;
import com.mealordering.adapter.TemplateAdapter;
import com.mealordering.net.model.FoodsResult;
import com.mealordering.utils.ViewUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Anthony on 14-2-14.
 */
public class OrderConfirmItemBuilder implements ViewItemBuilder<FoodsResult.Food> {
    private TemplateAdapter<FoodsResult.Food> mAdapter;

    @Override
    public View createView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.list_item_order_confirm, null);
        return view;
    }

    @Override
    public void populateView(TemplateAdapter<FoodsResult.Food> adapter, View view, int position, FoodsResult.Food item) {
        mAdapter = adapter;
        ViewHolder holder = view.getTag() == null ? new ViewHolder(view) : (ViewHolder) view.getTag();
        ViewUtil.setText(holder.mNameTv, item.getFoodName());
        ViewUtil.setText(holder.mAmountTv, String.valueOf(item.getAmount()));
        ViewUtil.setText(holder.mPriceTv, String.valueOf(item.getPrice()));
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
