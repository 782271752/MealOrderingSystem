package com.mealordering.adapter.item;

import android.view.LayoutInflater;
import android.view.View;

import com.mealordering.adapter.TemplateAdapter;

/**
 * Created by Anthony on 14-2-12.
 */
public interface ViewItemBuilder<T> {

    View createView(LayoutInflater inflater);

    void populateView(TemplateAdapter<T> adapter, View view, int position, T item);
}
