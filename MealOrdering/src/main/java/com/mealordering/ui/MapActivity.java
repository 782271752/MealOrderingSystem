package com.mealordering.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.mealordering.Intents;
import com.mealordering.R;
import com.mealordering.net.RequestHelper;
import com.mealordering.net.listener.SimpleRequestListener;
import com.mealordering.net.model.OrderByXYResult;
import com.mealordering.utils.ToastUtil;
import com.mealordering.utils.ViewUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MapActivity extends TitleSubActivity implements AMap.OnInfoWindowClickListener, AMap.InfoWindowAdapter {
    private static final int DELAY_MS = 10 * 1000;
    @InjectView(R.id.map)
    MapView mMapView;
    String mOrderId;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            RequestHelper.exeOrderByXYRequest(getSpiceManager(), new OrderByXYRequestListener(), mOrderId);
        }
    };
    private AMap mAMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.inject(this);
        mMapView.onCreate(savedInstanceState);
        mAMap = mMapView.getMap();
        mOrderId = getIntent().getStringExtra(Intents.ID);
        mAMap.setOnInfoWindowClickListener(this);// 设置点击infoWindow事件监听器
        mAMap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
        mHandler.sendEmptyMessage(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
        mHandler.removeMessages(0);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    private class OrderByXYRequestListener extends SimpleRequestListener<OrderByXYResult> {
        public OrderByXYRequestListener() {
            super(ViewUtil.findView(MapActivity.this, R.id.loading_pb));
        }

        @Override
        public void onRequestSuccess(OrderByXYResult result) {
            super.onRequestSuccess(result);
            if (result.isSuccess()) {
                OrderByXYResult.OrderByXY orderByXY = result.getData();

                LatLng latLng = new LatLng(orderByXY.getCoordinatesX(), orderByXY.getCoordinatesY());
                mAMap.clear();
                mAMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("送餐人员位置")
                        .perspective(true)
                        .icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_RED))).showInfoWindow();
                mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
            } else {
                ToastUtil.showShortTimeMsg(result.getMessage());
            }
        }

        @Override
        public void onRequestComplete() {
            super.onRequestComplete();
            mHandler.sendEmptyMessageDelayed(0, DELAY_MS);
        }
    }
}
