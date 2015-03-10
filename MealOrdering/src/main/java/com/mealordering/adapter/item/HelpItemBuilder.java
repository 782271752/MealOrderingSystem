package com.mealordering.adapter.item;

import android.view.LayoutInflater;
import android.view.View;

import com.mealordering.R;
import com.mealordering.adapter.TemplateAdapter;
import com.mealordering.net.model.HelpResult;
import com.mealordering.utils.ViewUtil;

/**
 * Created by Anthony on 14-2-21.
 */
public class HelpItemBuilder implements ViewItemBuilder<HelpResult.Help> {

    @Override
    public View createView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.list_item_help, null);
    }

    @Override
    public void populateView(TemplateAdapter<HelpResult.Help> adapter, View view, int position, HelpResult.Help item) {
        ViewUtil.findTextViewAndSetText(view, R.id.help_title_tv,
                item.getAnsweTitle());
        ViewUtil.findTextViewAndSetText(view, R.id.help_content_tv, item.getAnsweContent());
    }
}
