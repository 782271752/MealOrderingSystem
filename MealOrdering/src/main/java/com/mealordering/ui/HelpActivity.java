package com.mealordering.ui;

import android.os.Bundle;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.mealordering.R;
import com.mealordering.adapter.TemplateAdapter;
import com.mealordering.adapter.item.HelpItemBuilder;
import com.mealordering.net.RequestHelper;
import com.mealordering.net.listener.SimpleRequestListener;
import com.mealordering.net.model.HelpResult;
import com.mealordering.utils.ToastUtil;
import com.mealordering.utils.ViewUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

public class HelpActivity extends TitleSubActivity implements OnRefreshListener {

    @InjectView(R.id.list_lv)
    PullToRefreshListView mHelpLv;
    private List<HelpResult.Help> mHelps;
    private TemplateAdapter<HelpResult.Help> mAdapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ButterKnife.inject(this);
        mAdapter = new TemplateAdapter<HelpResult.Help>(new HelpItemBuilder());
        mHelpLv.setAdapter(mAdapter);
        mHelpLv.setOnRefreshListener(this);
        RequestHelper.exeHelpRequest(getSpiceManager(), new HelpRequestListener(), 0);
    }

    @Override
    public void onRefresh(PullToRefreshBase pullToRefreshBase) {
        RequestHelper.exeHelpRequest(getSpiceManager(), new HelpRequestListener(), 0);
    }

    private class HelpRequestListener extends SimpleRequestListener<HelpResult> {
        public HelpRequestListener() {
            super(ViewUtil.findView(HelpActivity.this, R.id.loading_pb));
        }

        @Override
        public void onRequestSuccess(HelpResult result) {
            super.onRequestSuccess(result);
            ToastUtil.showShortTimeMsg(result.getMessage());
            if (result.isSuccess()) {
                mHelps = result.getData();
                mAdapter.setList(mHelps);
            }
        }

        @Override
        public void onRequestStart() {
            super.onRequestStart();
            mHelpLv.setRefreshing();
        }

        @Override
        public void onRequestComplete() {
            super.onRequestComplete();
            mHelpLv.onRefreshComplete();
        }
    }
}
