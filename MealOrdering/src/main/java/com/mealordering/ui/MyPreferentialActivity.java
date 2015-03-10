package com.mealordering.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckedTextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.mealordering.R;
import com.mealordering.adapter.TemplateAdapter;
import com.mealordering.adapter.item.MyPreferentialItemBuilder;
import com.mealordering.net.RequestHelper;
import com.mealordering.net.listener.SimpleRequestListener;
import com.mealordering.net.model.MyPreferentialResult;
import com.mealordering.utils.ToastUtil;
import com.mealordering.utils.ViewUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MyPreferentialActivity extends TitleSubActivity implements View.OnClickListener
        , PullToRefreshBase.OnRefreshListener {

    @InjectView(R.id.list_lv)
    PullToRefreshListView mPreferentialLv;
    private CheckedTextView mLastCheckedCtv;

    private List<MyPreferentialResult.MyPreferential> mPreferentias;
    private TemplateAdapter<MyPreferentialResult.MyPreferential> mPreferentiaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_preferential);
        ButterKnife.inject(this);
        mPreferentiaAdapter = new TemplateAdapter<MyPreferentialResult.MyPreferential>(new MyPreferentialItemBuilder());
        mPreferentialLv.setAdapter(mPreferentiaAdapter);
        mPreferentialLv.setOnRefreshListener(this);

        ViewUtil.findViewAndClick(this, R.id.my_preferential_unused_ctv, this);
        ViewUtil.findViewAndClick(this, R.id.my_preferential_used_ctv, this);
        ViewUtil.findViewAndClick(this, R.id.my_preferential_overdue_ctv, this);

        CheckedTextView unusedCtv = ViewUtil.findView(this, R.id.my_preferential_unused_ctv);
        unusedCtv.setChecked(true);
        mLastCheckedCtv = unusedCtv;

        onClick(mLastCheckedCtv);
    }

    private void loadMyPreferential(int type, String pid) {
        RequestHelper.exeMyPreferentialRequest(getSpiceManager(), new MyPreferentialRequestListener(), type, pid);
    }

    @Override
    public void onClick(View v) {
        if (v instanceof CheckedTextView) {
            if (mLastCheckedCtv != null) {
                mLastCheckedCtv.setChecked(false);
                mLastCheckedCtv.setTextColor(getResources().getColor(R.color.light_grey));
            }
            CheckedTextView curCheckedTextView = (CheckedTextView) v;
            curCheckedTextView.setChecked(true);
            curCheckedTextView.setTextColor(getResources().getColor(R.color.red));
            mLastCheckedCtv = curCheckedTextView;
        }

        switch (v.getId()) {
            case R.id.my_preferential_unused_ctv:
                loadMyPreferential(0, "0");
                break;
            case R.id.my_preferential_used_ctv:
                loadMyPreferential(1, "0");
                break;
            case R.id.my_preferential_overdue_ctv:
                loadMyPreferential(2, "0");
                break;
        }
    }

    @Override
    public void onRefresh(PullToRefreshBase pullToRefreshBase) {
        onClick(mLastCheckedCtv);
    }

    private class MyPreferentialRequestListener extends SimpleRequestListener<MyPreferentialResult> {
        public MyPreferentialRequestListener() {
            super(ViewUtil.findView(MyPreferentialActivity.this, R.id.loading_pb));
        }

        @Override
        public void onRequestSuccess(MyPreferentialResult result) {
            super.onRequestSuccess(result);
            ToastUtil.showShortTimeMsg(result.getMessage());
            mPreferentiaAdapter.setList(null);
            if (result.isSuccess()) {
                mPreferentias = result.getData();
                mPreferentiaAdapter.setList(mPreferentias);
            }
        }

        @Override
        public void onRequestStart() {
            super.onRequestStart();
            mPreferentialLv.setRefreshing();
        }

        @Override
        public void onRequestComplete() {
            super.onRequestComplete();
            mPreferentialLv.onRefreshComplete();
        }
    }
}
