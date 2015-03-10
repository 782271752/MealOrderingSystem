package com.mealordering.ui;

import android.os.Bundle;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.mealordering.R;
import com.mealordering.adapter.TemplateAdapter;
import com.mealordering.adapter.item.MessageItemBuilder;
import com.mealordering.net.RequestHelper;
import com.mealordering.net.listener.SimpleRequestListener;
import com.mealordering.net.model.MyMessageResults;
import com.mealordering.utils.ToastUtil;
import com.mealordering.utils.ViewUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MyMessageActivity extends TitleSubActivity implements PullToRefreshBase.OnRefreshListener {

    @InjectView(R.id.list_lv)
    PullToRefreshListView mMessageLv;
    private List<MyMessageResults.MyMessage> mMyMessageList;
    private TemplateAdapter<MyMessageResults.MyMessage> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);
        ButterKnife.inject(this);
        mAdapter = new TemplateAdapter<MyMessageResults.MyMessage>(new MessageItemBuilder());
        mMessageLv.setAdapter(mAdapter);
        mMessageLv.setOnRefreshListener(this);
        RequestHelper.exeMyMessageRequest(getSpiceManager(), new MyMessageRequestListener());
    }

    @Override
    public void onRefresh(PullToRefreshBase pullToRefreshBase) {
        RequestHelper.exeMyMessageRequest(getSpiceManager(), new MyMessageRequestListener());
    }

    private class MyMessageRequestListener extends SimpleRequestListener<MyMessageResults> {
        public MyMessageRequestListener() {
            super(ViewUtil.findView(MyMessageActivity.this, R.id.loading_pb));
        }

        @Override
        public void onRequestSuccess(MyMessageResults result) {
            super.onRequestSuccess(result);
            if (result.isSuccess()) {
                mMyMessageList = result.getData();
                mAdapter.setList(result.getData());
            } else {
                ToastUtil.showShortTimeMsg(result.getMessage());
            }
        }

        @Override
        public void onRequestStart() {
            super.onRequestStart();
            mMessageLv.setRefreshing();
        }

        @Override
        public void onRequestComplete() {
            super.onRequestComplete();
            mMessageLv.onRefreshComplete();
        }
    }
}
