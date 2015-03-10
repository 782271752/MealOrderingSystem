package com.mealordering.employee.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.mealordering.employee.adapter.item.ViewItemBuilder;

import java.util.Arrays;
import java.util.List;

public class TemplateAdapter<T> extends BaseAdapter {
    private List<T> mList;
    private LayoutInflater mInflater;
    private ViewItemBuilder<T> mItemBuilder;

    public TemplateAdapter(ViewItemBuilder<T> builder) {
        this(builder, (List<T>) null);
    }

    public TemplateAdapter(ViewItemBuilder<T> builder, List<T> list) {
        mList = list;
        mItemBuilder = builder;
    }

    public TemplateAdapter(ViewItemBuilder<T> builder, T[] list) {
        mList = Arrays.asList(list);
        mItemBuilder = builder;
    }

    @Override

    public int getCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public T getItem(int position) {
        position = position < getCount() ? position : getCount() - 1;
        return mList != null ? mList.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (parent.getContext() == null) {
            return null;
        }
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        if (mItemBuilder == null) {
            return null;
        }
        if (convertView == null) {
            convertView = mItemBuilder.createView(mInflater);
        }
        mItemBuilder.populateView(this, convertView, position, getItem(position));
        return convertView;
    }

    public List<T> getList() {
        return mList;
    }

    public void setList(List<T> list) {
        mList = list;
        notifyDataSetChanged();
    }
}
