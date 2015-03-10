package com.mealordering.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mealordering.Intents;
import com.mealordering.R;
import com.mealordering.net.RequestHelper;
import com.mealordering.net.listener.SimpleRequestListener;
import com.mealordering.net.model.FoodDetailResult;
import com.mealordering.net.model.FoodsResult;
import com.mealordering.task.SaveTask;
import com.mealordering.utils.ToastUtil;
import com.mealordering.utils.ViewUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FoodDetailActivity extends TitleSubActivity implements View.OnClickListener {

    @InjectView(R.id.food_detail_image_iv)
    ImageView mImageIv;
    @InjectView(R.id.food_detail_name_tv)
    TextView mNameTv;
    @InjectView(R.id.food_detail_buy_btn)
    Button mBuyBtn;
    @InjectView(R.id.food_detail_price_tv)
    TextView mPriceTv;
    @InjectView(R.id.food_detail_like_tv)
    TextView mLikeTv;
    @InjectView(R.id.food_detail_introduction_tv)
    TextView mIntroductionTv;
    private FoodDetailResult.FoodDetail mFoodDetail;
    private FoodsResult.Food mFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        ButterKnife.inject(this);
        ViewUtil.setOnClick(mBuyBtn, this);
        ViewUtil.findViewAndClick(this, R.id.order_food_plus_btn, this);
        ViewUtil.findViewAndClick(this, R.id.order_food_reduce_btn, this);
        mFood = (FoodsResult.Food) getIntent().getSerializableExtra(Intents.ITEM);
        RequestHelper.exeFoodDetailRequest(getSpiceManager(),
                new FoodDetailRequestListener(), mFood.getFoodId());
        ViewUtil.findTextViewAndSetText(this, R.id.order_food_amount_tv, String.valueOf(mFood.getAmount()));
    }

    private void populateViews(FoodDetailResult.FoodDetail foodDetail) {
        ViewUtil.loadImage(mImageIv, foodDetail.getImg());
        ViewUtil.setText(mNameTv, foodDetail.getFoodName());
        ViewUtil.setText(mPriceTv, String.valueOf(foodDetail.getPrice()));
        ViewUtil.setText(mLikeTv, String.valueOf(foodDetail.getParameterOne()));
        ViewUtil.setText(mIntroductionTv, foodDetail.getRemark());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.food_detail_buy_btn:
                new SaveTask() {
                    @Override
                    protected void onPostExecute(Boolean success) {
                        super.onPostExecute(success);
                        if (success) {
                            finish();
                        }
                    }
                }.execute(mFood);
                break;
            case R.id.order_food_plus_btn:
                mFood.setAmount(mFood.getAmount() + 1);
                ViewUtil.findTextViewAndSetText(this, R.id.order_food_amount_tv,
                        String.valueOf(mFood.getAmount()));
                break;
            case R.id.order_food_reduce_btn:
                if (mFood.getAmount() > 1) {
                    mFood.setAmount(mFood.getAmount() - 1);
                    ViewUtil.findTextViewAndSetText(this, R.id.order_food_amount_tv,
                            String.valueOf(mFood.getAmount()));
                }
                break;
        }
    }

    private class FoodDetailRequestListener extends SimpleRequestListener<FoodDetailResult> {
        @Override
        public void onRequestSuccess(FoodDetailResult result) {
            super.onRequestSuccess(result);
            ToastUtil.showShortTimeMsg(result.getMessage());
            if (result.isSuccess()) {
                mFoodDetail = result.getData();
                populateViews(result.getData());
            }
        }
    }
}
