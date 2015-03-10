package com.mealordering.adapter.item;

import android.view.LayoutInflater;
import android.view.View;

import com.mealordering.R;
import com.mealordering.adapter.TemplateAdapter;
import com.mealordering.net.model.FoodTypesResult;
import com.mealordering.utils.ViewUtil;

/**
 * Created by Anthony on 14-2-14.
 */
public class FoodClassifyItemBuilder implements ViewItemBuilder<FoodTypesResult.FoodType> {
    @Override
    public View createView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.list_item_food_classify, null);
    }

    @Override
    public void populateView(TemplateAdapter<FoodTypesResult.FoodType> adapter, View view, int position, FoodTypesResult.FoodType item) {
        ViewUtil.findTextViewAndSetText(view, R.id.food_classify_name_tv, item.getFoodTypeName());
    }
}
