package com.mealordering.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.mealordering.Intents;
import com.mealordering.R;
import com.mealordering.adapter.TemplateAdapter;
import com.mealordering.adapter.item.FindRoadItemBuilder;
import com.mealordering.net.RequestHelper;
import com.mealordering.net.listener.SimpleRequestListener;
import com.mealordering.net.model.ShopRoadResults;
import com.mealordering.utils.ToastUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FindRoadActivity extends TitleSubActivity implements AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener {

    @InjectView(R.id.list_lv)
    PullToRefreshListView mStreetLv;
    private String mCity;
    private String mArea;
    private String mFind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_street);
        ButterKnife.inject(this);
        mCity = getIntent().getStringExtra(Intents.CITY);
        mArea = getIntent().getStringExtra(Intents.AREA);
        mFind = getIntent().getStringExtra(Intents.FIND_ROAD);
        mStreetLv.setOnItemClickListener(this);
        mStreetLv.setOnRefreshListener(this);
        mStreetLv.getRefreshableView().setBackgroundColor(getResources().getColor(android.R.color.transparent));
        RequestHelper.exeShopRoadRequest(getSpiceManager(), new FindRoadRequestListener(), mCity, mArea, mFind);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ShopRoadResults.ShopRoad item = (ShopRoadResults.ShopRoad) parent.getItemAtPosition(position);
        Intent intent = new Intent();
        intent.putExtra(Intents.FIND_ROAD, item.getRoad());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onRefresh(PullToRefreshBase pullToRefreshBase) {
        RequestHelper.exeShopRoadRequest(getSpiceManager(), new FindRoadRequestListener(), mCity, mArea, mFind);
    }

    private class FindRoadRequestListener extends SimpleRequestListener<ShopRoadResults> {
        @Override
        public void onRequestStart() {
            super.onRequestStart();
            mStreetLv.setRefreshing();
        }

        @Override
        public void onRequestComplete() {
            super.onRequestComplete();
            mStreetLv.onRefreshComplete();
        }

        @Override
        public void onRequestSuccess(ShopRoadResults result) {
            super.onRequestSuccess(result);
            if (result.isSuccess()) {
                if (result.getData().isEmpty()) {
                    ToastUtil.showLongTimeMsg("查找不到该路段，请检查后重试！");
                } else {
                    mStreetLv.setAdapter(new TemplateAdapter<ShopRoadResults.ShopRoad>
                            (new FindRoadItemBuilder(), result.getData()));
                }
            }
        }
    }
}
