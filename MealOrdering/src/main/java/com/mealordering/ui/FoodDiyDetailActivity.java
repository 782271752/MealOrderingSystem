package com.mealordering.ui;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.google.common.collect.Lists;
import com.mealordering.Intents;
import com.mealordering.R;
import com.mealordering.net.RequestHelper;
import com.mealordering.net.listener.SimpleRequestListener;
import com.mealordering.net.model.EmptyResult;
import com.mealordering.net.model.FoodDiyDetailResult;
import com.mealordering.net.model.FoodsResult;
import com.mealordering.task.SaveTask;
import com.mealordering.utils.ToastUtil;
import com.mealordering.utils.ViewUtil;

import java.util.List;

import butterknife.ButterKnife;

public class FoodDiyDetailActivity extends TitleSubActivity implements View.OnClickListener {
    FoodDiyDetailResult.FoodDiyDetail mFoodDetail;
    FoodsResult.Food mFood;
    List<FoodDiyDetailResult.FoodDiyDetail.FoodDiyType.FoodDiyMaterial> mFoodDiyMaterials = Lists.newArrayList();
    private ViewGroup mDiyTypeRootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_diy_detail);
        ButterKnife.inject(this);
        ViewUtil.findViewAndClick(this, R.id.order_food_plus_btn, this);
        ViewUtil.findViewAndClick(this, R.id.order_food_reduce_btn, this);
        ViewUtil.findViewAndClick(this, R.id.food_detail_buy_btn, this);
        mDiyTypeRootLayout = ViewUtil.findView(this, R.id.food_diy_detail_type_root_layout);

        mFood = (FoodsResult.Food) getIntent().getSerializableExtra(Intents.ITEM);
        RequestHelper.exeFoodDiyDetailRequest(getSpiceManager(), new FoodDiyDetailRequestListener(), mFood.getFoodId());
    }

    private void populateViews(FoodDiyDetailResult.FoodDiyDetail foodDiyDetail) {
        ViewUtil.loadImage((ImageView) ViewUtil.findView(this, R.id.food_detail_image_iv), foodDiyDetail.getImg());
        ViewUtil.findTextViewAndSetText(this, R.id.food_diy_detail_name_tv,
                foodDiyDetail.getFoodName());
        ViewUtil.findTextViewAndSetText(this, R.id.order_food_amount_tv,
                String.valueOf(mFood.getAmount()));

        for (FoodDiyDetailResult.FoodDiyDetail.FoodDiyType foodDiyType : foodDiyDetail.getFoodDiyType()) {
            ViewGroup diyTypeRootLayout = (ViewGroup) getLayoutInflater().inflate(R.layout.layout_food_diy_type, null);
            ViewUtil.findTextViewAndSetText(diyTypeRootLayout, R.id.food_diy_detail_type_name_tv,
                    foodDiyType.getFoodDiyTypeName());

            ViewUtil.findTextViewAndSetText(diyTypeRootLayout, R.id.food_diy_detail_type_name_tip_tv,
                    getResources().getString(R.string.food_diy_detail_type_name_tip,
                            foodDiyType.getLess(), foodDiyType.getMore())
            );

            TableLayout diyTypeItemsLayout = ViewUtil.findView(diyTypeRootLayout, R.id.food_diy_detail_type_item_layout);
            TableRow row = null;
            for (int i = 0; i < foodDiyType.getFoodDiyMaterial().size(); i++) {
                if (i % 3 == 0) {
                    row = new TableRow(this);
                    diyTypeItemsLayout.addView(row);
                }

                CheckedTextView checkbox = new CheckedTextView(this);

                checkbox.setBackgroundResource(R.drawable.bg_diy_checked_item);
                checkbox.setGravity(Gravity.CENTER);
                checkbox.setOnClickListener(this);
                checkbox.setTag(foodDiyType);
                FoodDiyDetailResult.FoodDiyDetail.FoodDiyType.FoodDiyMaterial foodDiyMaterial = foodDiyType.getFoodDiyMaterial().get(i);
                checkbox.setText(foodDiyMaterial.getFoodDiyMaterialName());
                checkbox.setTag(checkbox.getId(), foodDiyMaterial);

                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                int defaultPX = getResources().getDimensionPixelSize(R.dimen.margin_default);
                layoutParams.setMargins(defaultPX, 5, defaultPX, 0);

                row.addView(checkbox, layoutParams);
            }
            mDiyTypeRootLayout.addView(diyTypeRootLayout);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.food_detail_buy_btn:
                boolean canOrder = true;
                for (FoodDiyDetailResult.FoodDiyDetail.FoodDiyType foodDiyType : mFoodDetail.getFoodDiyType()) {
                    if (foodDiyType.getHasCheckedCount() < foodDiyType.getLess()) {
                        ToastUtil.showShortTimeMsg(String.format("%s 最少选择%s项", foodDiyType.getFoodDiyTypeName(),
                                foodDiyType.getLess()));
                        canOrder = false;
                        break;
                    }
                }
                if (canOrder) {
                    RequestHelper.exeAddDIYRequest(getSpiceManager(), new AddFoodDiyRequestListener(),
                            mFoodDetail.getFoodDiyId(), mFoodDiyMaterials);
                }
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
            default:
                if (v instanceof CheckedTextView) {
                    CheckedTextView ctv = (CheckedTextView) v;
                    FoodDiyDetailResult.FoodDiyDetail.FoodDiyType foodDiyType =
                            (FoodDiyDetailResult.FoodDiyDetail.FoodDiyType) v.getTag();
                    int hasCheckedCount = foodDiyType.getHasCheckedCount();
                    if (ctv.isChecked()) {
                        ctv.setChecked(false);
                        foodDiyType.setHasCheckedCount(hasCheckedCount - 1);
                        mFoodDiyMaterials.remove(ctv.getTag(ctv.getId()));
                    } else {
                        if (foodDiyType.getHasCheckedCount() < foodDiyType.getMore()) {
                            ctv.setChecked(true);
                            foodDiyType.setHasCheckedCount(hasCheckedCount + 1);
                            mFoodDiyMaterials.add(
                                    (FoodDiyDetailResult.FoodDiyDetail.FoodDiyType.FoodDiyMaterial) ctv.getTag(ctv.getId()));
                        } else {
                            ToastUtil.showShortTimeMsg(String.format("%s 最多只能选择%s项",
                                    foodDiyType.getFoodDiyTypeName(), foodDiyType.getMore()));
                        }
                    }
                }
                break;
        }
    }

    private class FoodDiyDetailRequestListener extends SimpleRequestListener<FoodDiyDetailResult> {
        FoodDiyDetailRequestListener() {
            super(ViewUtil.findView(FoodDiyDetailActivity.this, R.id.loading_pb));
        }

        @Override
        public void onRequestSuccess(FoodDiyDetailResult result) {
            super.onRequestSuccess(result);
            ToastUtil.showShortTimeMsg(result.getMessage());
            if (result.isSuccess()) {
                FoodDiyDetailResult.FoodDiyDetail foodDetail = result.getData();
                mFoodDetail = foodDetail;
                populateViews(foodDetail);
            }
        }
    }

    private class AddFoodDiyRequestListener extends SimpleRequestListener<EmptyResult> {
        AddFoodDiyRequestListener() {
            super(ViewUtil.findView(FoodDiyDetailActivity.this, R.id.loading_pb));
        }

        @Override
        public void onRequestSuccess(EmptyResult result) {
            super.onRequestSuccess(result);
            ToastUtil.showShortTimeMsg(result.getMessage());
            if (result.isSuccess()) {
                mFood.setFoodId(result.getData().toString());
                float extraCost = 0f;
                for (int i = 0; i < mFoodDiyMaterials.size(); i++) {
                    extraCost += mFoodDiyMaterials.get(i).getPrice();
                }
                mFood.setPrice(mFoodDetail.getPrice() + extraCost);
                new SaveTask() {
                    @Override
                    protected void onPostExecute(Boolean success) {
                        super.onPostExecute(success);
                        if (success) {
                            finish();
                        }
                    }
                }.execute(mFood);
            }
        }
    }
}
