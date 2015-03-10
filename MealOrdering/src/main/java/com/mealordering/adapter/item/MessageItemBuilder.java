package com.mealordering.adapter.item;

import android.view.LayoutInflater;
import android.view.View;

import com.mealordering.R;
import com.mealordering.adapter.TemplateAdapter;
import com.mealordering.net.model.MyMessageResults;
import com.mealordering.utils.ViewUtil;

import java.text.SimpleDateFormat;

/**
 * Created by Anthony on 14-2-21.
 */
public class MessageItemBuilder implements ViewItemBuilder<MyMessageResults.MyMessage> {
    private static SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public View createView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.list_item_my_message, null);
    }

    @Override
    public void populateView(TemplateAdapter<MyMessageResults.MyMessage> adapter, View view, int position, MyMessageResults.MyMessage item) {
        ViewUtil.findTextViewAndSetText(view, R.id.my_message_time_tv,
                item.getReleaseTime());
        ViewUtil.findTextViewAndSetText(view, R.id.my_message_content_tv, item.getMessageContent());
    }
}
