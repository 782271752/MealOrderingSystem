package com.mealordering.adapter.item;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mealordering.Intents;
import com.mealordering.R;
import com.mealordering.adapter.TemplateAdapter;
import com.mealordering.net.model.FoodsResult;
import com.mealordering.task.SaveTask;
import com.mealordering.ui.fragment.FoodFragment;
import com.mealordering.utils.ViewUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Anthony on 14-2-14.
 */
public class FoodItemBuilder implements ViewItemBuilder<FoodsResult.Food>, View.OnClickListener {
    Context context;
    private TemplateAdapter<FoodsResult.Food> mAdapter;

    public FoodItemBuilder(Context context) {
        this.context = context;
    }

    @Override
    public View createView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.list_item_food_foods, null);
    }

    @Override
    public void populateView(TemplateAdapter<FoodsResult.Food> adapter, View view, int position, FoodsResult.Food item) {
        mAdapter = adapter;
        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null) {
            holder = new ViewHolder(view);
            ViewUtil.setOnClick(holder.orderBtn, this);
            ViewUtil.setOnClick(holder.plusBtn, this);
            ViewUtil.setOnClick(holder.reduceBtn, this);
            view.setTag(holder);
        }

        ViewUtil.loadImage(holder.imageIv, item.getImg());
        if (item.getA() == 1) {
            holder.nameTv.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

        } else {
            holder.nameTv.setCompoundDrawablesWithIntrinsicBounds(null, null,
                    view.getResources().getDrawable(R.drawable.icon_good), null);
        }
        ViewUtil.setInvisible(holder.controlLayout, item.getA() == 1);
        ViewUtil.setInvisible(holder.likeTv, item.getA() == 1);

        ViewUtil.loadImage(holder.imageIv, item.getImg());
        ViewUtil.setText(holder.nameTv, item.getFoodName());
        if (adapter instanceof FoodFragment.FoodsAdapter) {
            Drawable right = ((FoodFragment.FoodsAdapter) adapter).isRecommend()
                    ? view.getResources().getDrawable(R.drawable.icon_good) : null;
            holder.nameTv.setCompoundDrawablesWithIntrinsicBounds(null, null, right, null);
        }
        ViewUtil.setText(holder.likeTv, String.valueOf(item.getParameterOne()));
        ViewUtil.setText(holder.amountTv, String.valueOf(item.getAmount()));
        ViewUtil.setText(holder.priceTv, view.getResources().getString(R.string.money, item.getPrice()));
        holder.orderBtn.setTag(item);
        holder.plusBtn.setTag(item);
        holder.reduceBtn.setTag(item);
    }

    @Override
    public void onClick(View v) {
//        RuntimeExceptionDao<FoodsResult.Food, Integer> dao = DBHelper.getInstance().getFoodDao();
        FoodsResult.Food food = (FoodsResult.Food) v.getTag();
        switch (v.getId()) {
            case R.id.food_foods_order_btn:
                if (food.getA() == 1) {
                    Intents.launchFoodDIYDetail(context, food);
                } else {
                    new SaveTask().execute(food);
                }
                break;
            case R.id.order_food_reduce_btn:
                if (food.getAmount() > 1) {
                    food.setAmount(food.getAmount() - 1);
//                    dao.update(food);
                }
                break;
            case R.id.order_food_plus_btn:
                food.setAmount(food.getAmount() + 1);
//                dao.update(food);
                break;
        }
        mAdapter.notifyDataSetChanged();
    }

    static class ViewHolder {
        @InjectView(R.id.food_foods_image_iv)
        ImageView imageIv;
        @InjectView(R.id.food_foods_control_layout)
        LinearLayout controlLayout;
        @InjectView(R.id.food_foods_name_tv)
        TextView nameTv;
        @InjectView(R.id.food_foods_like_tv)
        TextView likeTv;
        @InjectView(R.id.food_foods_price_tv)
        TextView priceTv;
        @InjectView(R.id.food_foods_order_btn)
        Button orderBtn;
        @InjectView(R.id.order_food_amount_tv)
        TextView amountTv;
        @InjectView(R.id.order_food_plus_btn)
        ImageButton plusBtn;
        @InjectView(R.id.order_food_reduce_btn)
        ImageButton reduceBtn;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
