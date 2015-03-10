package com.mealordering.ui;

import android.os.Bundle;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.mealordering.R;
import com.mealordering.net.RequestHelper;
import com.mealordering.net.listener.SimpleRequestListener;
import com.mealordering.net.model.ShopLocationResults;
import com.mealordering.utils.ToastUtil;
import com.mealordering.utils.ViewUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Anthonit on 2014/6/2 0002.
 */
public class ShopLocationActivity extends TitleSubActivity implements AMap.OnMarkerClickListener,
        AMap.OnInfoWindowClickListener, AMap.OnMarkerDragListener, AMap.OnMapLoadedListener,
        AMap.InfoWindowAdapter {

    @InjectView(R.id.map)
    MapView mMapView;
    private AMap mAMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.inject(this);
        mMapView.onCreate(savedInstanceState);
        mAMap = mMapView.getMap();


        mAMap.setOnMarkerDragListener(this);// 设置marker可拖拽事件监听器
        mAMap.setOnMapLoadedListener(this);// 设置amap加载成功事件监听器
        mAMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
        mAMap.setOnInfoWindowClickListener(this);// 设置点击infoWindow事件监听器
        mAMap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式

        RequestHelper.exeShopLocationRequest(getSpiceManager(), new ShopLocationRequestListener());
    }

    /**
     * 在地图上添加marker
     */
    private void updateMarkersToMap(List<ShopLocationResults.ShopLocation> shopLocations) {
        ArrayList<BitmapDescriptor> defaultIcon = new ArrayList<BitmapDescriptor>();
        defaultIcon.add(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mAMap.clear();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (ShopLocationResults.ShopLocation shopLocation : shopLocations) {
            MarkerOptions markerOption = new MarkerOptions();
            LatLng latLng = new LatLng(shopLocation.getCoordinatesX(), shopLocation.getCoordinatesY());
            markerOption.position(latLng);
            markerOption.title(shopLocation.getShopName()).snippet(shopLocation.getShopAddress());
            markerOption.perspective(true);
            markerOption.draggable(true);
            markerOption.icons(defaultIcon);
            mAMap.addMarker(markerOption);

            builder.include(latLng);
        }
        if (shopLocations.get(0) != null) {
            ShopLocationResults.ShopLocation shopLocation = shopLocations.get(0);
            LatLng latLng = new LatLng(shopLocation.getCoordinatesX(), shopLocation.getCoordinatesY());
            mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        }
//        mAMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 17));
    }

    /**
     * 对marker标注点点击响应事件
     */
    @Override
    public boolean onMarkerClick(final Marker marker) {
//        if (marker.equals(marker2)) {
//            if (aMap != null) {
//                jumpPoint(marker);
//            }
//        }
//        markerText.setText("你点击的是" + marker.getTitle());
        return false;
    }

    /**
     * 监听点击infowindow窗口事件回调
     */
    @Override
    public void onInfoWindowClick(Marker marker) {
//        ToastUtil.show(this, "你点击了infoWindow窗口" + marker.getTitle());
//        ToastUtil.show(MarkerActivity.this, "当前地图可视区域内Marker数量:"
//                + aMap.getMapScreenMarkers().size());
    }

    /**
     * 监听拖动marker时事件回调
     */
    @Override
    public void onMarkerDrag(Marker marker) {
//        String curDes = marker.getTitle() + "拖动时当前位置:(lat,lng)\n("
//                + marker.getPosition().latitude + ","
//                + marker.getPosition().longitude + ")";
//        markerText.setText(curDes);
    }

    /**
     * 监听拖动marker结束事件回调
     */
    @Override
    public void onMarkerDragEnd(Marker marker) {
//        markerText.setText(marker.getTitle() + "停止拖动");
    }

    /**
     * 监听开始拖动marker事件回调
     */
    @Override
    public void onMarkerDragStart(Marker marker) {
//        markerText.setText(marker.getTitle() + "开始拖动");
    }

    /**
     * 监听amap地图加载成功事件回调
     */
    @Override
    public void onMapLoaded() {
        // 设置所有maker显示在当前可视区域地图中
    }

    /**
     * 监听自定义infowindow窗口的infocontents事件回调
     */
    @Override
    public View getInfoContents(Marker marker) {
//        if (radioOption.getCheckedRadioButtonId() != R.id.custom_info_contents) {
//            return null;
//        }
//        View infoContent = getLayoutInflater().inflate(
//                R.layout.custom_info_contents, null);
//        render(marker, infoContent);
//        return infoContent;
        return null;
    }

    /**
     * 监听自定义infowindow窗口的infowindow事件回调
     */
    @Override
    public View getInfoWindow(Marker marker) {
//        if (radioOption.getCheckedRadioButtonId() != R.id.custom_info_window) {
//            return null;
//        }
//        View infoWindow = getLayoutInflater().inflate(
//                R.layout.custom_info_window, null);
//
//        render(marker, infoWindow);
//        return infoWindow;
        return null;
    }


    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
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
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    private class ShopLocationRequestListener extends SimpleRequestListener<ShopLocationResults> {
        public ShopLocationRequestListener() {
            super(ViewUtil.findView(ShopLocationActivity.this, R.id.loading_pb));
        }

        @Override
        public void onRequestSuccess(ShopLocationResults result) {
            super.onRequestSuccess(result);
            if (result.isSuccess()) {
                updateMarkersToMap(result.getData());
            } else {
                ToastUtil.showShortTimeMsg(result.getMessage());
            }
        }
    }
}
