package com.mealordering.adapter.item;

import android.view.LayoutInflater;
import android.view.View;

import com.mealordering.R;
import com.mealordering.adapter.TemplateAdapter;
import com.mealordering.bean.TextItem;
import com.mealordering.utils.ViewUtil;

/**
 * Created by Anthony on 14-2-14.
 */
public class TextItemBuilder implements ViewItemBuilder<TextItem> {
    @Override
    public View createView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.list_item_more, null);
    }

    @Override
    public void populateView(TemplateAdapter<TextItem> adapter, View view, int position, TextItem item) {
        ViewUtil.findTextViewAndSetText(view, R.id.more_item, item.name);
    }
}
