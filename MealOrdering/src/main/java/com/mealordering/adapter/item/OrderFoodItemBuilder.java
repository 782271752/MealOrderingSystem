package com.mealordering.adapter.item;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.mealordering.R;
import com.mealordering.adapter.TemplateAdapter;
import com.mealordering.db.DBHelper;
import com.mealordering.net.model.FoodsResult;
import com.mealordering.utils.ViewUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Anthony on 14-2-14.
 */
public class OrderFoodItemBuilder implements ViewItemBuilder<FoodsResult.Food>, View.OnClickListener {
    private TemplateAdapter<FoodsResult.Food> mAdapter;

    @Override
    public View createView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.list_item_order_food, null);
        ViewUtil.findViewAndClick(view, R.id.order_food_reduce_btn, this);
        ViewUtil.findViewAndClick(view, R.id.order_food_plus_btn, this);
        ViewUtil.findViewAndClick(view, R.id.order_food_delete_iv, this);
        return view;
    }

    @Override
    public void populateView(TemplateAdapter<FoodsResult.Food> adapter, View view, int position, FoodsResult.Food item) {
        mAdapter = adapter;
        ViewHolder holder = view.getTag() == null ? new ViewHolder(view) : (ViewHolder) view.getTag();
        ViewUtil.setText(holder.mNameTv, item.getFoodName());
        String amount = view.getResources().getString(R.string.buy_amount, item.getAmount());
        String price = view.getResources().getString(R.string.money, item.getPrice());
        ViewUtil.setText(holder.mAmountTv, amount);
        ViewUtil.setText(holder.mPriceTv, price);


        holder.mReduceBtn.setTag(item);
        holder.mPlusBtn.setTag(item);
        holder.mDeleteIv.setTag(item);
    }

    @Override
    public void onClick(View v) {
        RuntimeExceptionDao<FoodsResult.Food, Integer> dao = DBHelper.getInstance().getFoodDao();
        FoodsResult.Food item = (FoodsResult.Food) v.getTag();
        switch (v.getId()) {
            case R.id.order_food_reduce_btn:
                if (item.getAmount() > 1) {
                    item.setAmount(item.getAmount() - 1);
                    dao.update(item);
                }
                break;
            case R.id.order_food_plus_btn:
                item.setAmount(item.getAmount() + 1);
                dao.update(item);
                break;
            case R.id.order_food_delete_iv:
                mAdapter.getList().remove(item);
                dao.delete(item);
                break;
        }
        mAdapter.notifyDataSetChanged();
    }

    static class ViewHolder {
        @InjectView(R.id.order_food_delete_iv)
        ImageView mDeleteIv;
        @InjectView(R.id.order_food_name_tv)
        TextView mNameTv;
        @InjectView(R.id.order_food_reduce_btn)
        ImageButton mReduceBtn;
        @InjectView(R.id.order_food_amount_tv)
        TextView mAmountTv;
        @InjectView(R.id.order_food_plus_btn)
        ImageButton mPlusBtn;
        @InjectView(R.id.order_food_price_tv)
        TextView mPriceTv;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
