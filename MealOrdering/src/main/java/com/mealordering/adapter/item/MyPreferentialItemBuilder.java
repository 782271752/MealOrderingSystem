package com.mealordering.adapter.item;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.mealordering.R;
import com.mealordering.adapter.TemplateAdapter;
import com.mealordering.net.model.MyPreferentialResult;
import com.mealordering.utils.ViewUtil;

/**
 * Created by Anthony on 14-2-17.
 */
public class MyPreferentialItemBuilder implements ViewItemBuilder<MyPreferentialResult.MyPreferential> {

    @Override
    public View createView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.list_item_my_preferential, null);
    }

    @Override
    public void populateView(TemplateAdapter<MyPreferentialResult.MyPreferential> adapter, View view,
                             int position, MyPreferentialResult.MyPreferential item) {
//        ViewUtil.findTextViewAndSetText(view, R.id.my_preferential_title_tv, item.title);
        ImageView iconView = ViewUtil.findView(view, R.id.my_preferential_icon_iv);
        ViewUtil.loadImage(iconView, item.getImg());
        ViewUtil.findTextViewAndSetText(view, R.id.my_preferential_amount_tv, String.valueOf(item.getpNumber()));

    }
}
