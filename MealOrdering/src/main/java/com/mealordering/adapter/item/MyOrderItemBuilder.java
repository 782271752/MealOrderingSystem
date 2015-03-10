package com.mealordering.adapter.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.mealordering.Intents;
import com.mealordering.R;
import com.mealordering.adapter.TemplateAdapter;
import com.mealordering.net.model.MyOrderResult;
import com.mealordering.utils.ViewUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Anthony on 14-2-14.
 */
public class MyOrderItemBuilder implements ViewItemBuilder<MyOrderResult.MyOrder>, View.OnClickListener {
    private Context context;

    @Override
    public View createView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.list_item_my_order, null);
        ViewUtil.findViewAndClick(view, R.id.my_order_location_tv, this);
        ViewUtil.findViewAndClick(view, R.id.my_order_comment_tv, this);
        return view;
    }

    @Override
    public void populateView(TemplateAdapter<MyOrderResult.MyOrder> adapter, View view, int position,
                             MyOrderResult.MyOrder item) {
        context = view.getContext();
        ViewHolder holder = view.getTag() == null ? new ViewHolder(view) : (ViewHolder) view.getTag();
        ViewUtil.setText(holder.idTv, item.getOrderNo());
        ViewUtil.setText(holder.timeTv, item.getOrderTime());

        ViewUtil.setText(holder.totalCostTv, view.getResources().getString(R.string.money, item.getAmount()));

        ViewUtil.setText(holder.stateTv, item.getOrderStateText());

        ViewUtil.setGone(holder.typeVf, !item.canLocation());
        //已完成未评价
        if (item.getOrderState() == 5) {
            if (item.isEvaluation()) {
                holder.typeVf.setDisplayedChild(2);
            } else {
                holder.typeVf.setDisplayedChild(1);
                View comment = holder.typeVf.getChildAt(1);
                comment.setTag(item);
            }
        }
        //处理中
        else {
            holder.typeVf.setDisplayedChild(0);
            View location = holder.typeVf.getChildAt(0);
            location.setTag(item);
        }
        StringBuilder foods = new StringBuilder();
        for (MyOrderResult.MyOrder.OrderNumberId orderNumberId : item.getOrderNumberId()) {
            foods.append(orderNumberId.getFoodName());
            foods.append("*");
            foods.append(orderNumberId.getOrderCount());
            foods.append(",");
        }
        if (foods.length() > 0)
            foods.deleteCharAt(foods.length() - 1);
        ViewUtil.setText(holder.foodsTv, foods.toString());
//        if (item.foods != null && item.foods.size() > 0) {
//            StringBuilder foods = new StringBuilder();
//            for (String food : item.foods) {
//                foods.append(food);
//                foods.append("，");
//            }
//            foods.deleteCharAt(foods.length() - 1);
//            ViewUtil.setText(holder.foodsTv, foods.toString());
//        }
    }

    @Override
    public void onClick(View v) {
        MyOrderResult.MyOrder item = (MyOrderResult.MyOrder) v.getTag();
        switch (v.getId()) {
            case R.id.my_order_location_tv:
                Intents.launchMap(context, item.getOrderId());
                break;
            case R.id.my_order_comment_tv:
                Intents.launchOrderComment(context, item);
                break;
        }
    }


    static class ViewHolder {
        @InjectView(R.id.my_order_id_tv)
        TextView idTv;
        @InjectView(R.id.my_address_type_vf)
        ViewFlipper typeVf;
        @InjectView(R.id.my_order_time_tv)
        TextView timeTv;
        @InjectView(R.id.my_order_total_cost_tv)
        TextView totalCostTv;
        @InjectView(R.id.my_order_state_tv)
        TextView stateTv;
        @InjectView(R.id.my_order_foods_tv)
        TextView foodsTv;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
