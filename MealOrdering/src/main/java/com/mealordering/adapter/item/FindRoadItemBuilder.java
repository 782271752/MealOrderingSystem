package com.mealordering.adapter.item;

import android.view.LayoutInflater;
import android.view.View;

import com.mealordering.R;
import com.mealordering.adapter.TemplateAdapter;
import com.mealordering.net.model.ShopRoadResults;
import com.mealordering.utils.ViewUtil;

/**
 * Created by Anthony on 14-2-21.
 */
public class FindRoadItemBuilder implements ViewItemBuilder<ShopRoadResults.ShopRoad> {

    @Override
    public View createView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.list_item_find_street, null);
    }

    @Override
    public void populateView(TemplateAdapter<ShopRoadResults.ShopRoad> adapter, View view, int position, ShopRoadResults.ShopRoad item) {
        ViewUtil.findTextViewAndSetText(view, R.id.find_road_title_tv,
                item.getRoad());
    }
}
