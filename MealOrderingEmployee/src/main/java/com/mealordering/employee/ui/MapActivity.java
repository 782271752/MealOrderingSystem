package com.mealordering.employee.ui;

import android.app.ProgressDialog;
import android.location.Location;
import android.os.Bundle;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.overlay.WalkRouteOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiItemDetail;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.mealordering.employee.Intents;
import com.mealordering.employee.R;
import com.mealordering.employee.net.model.OrderResults;
import com.mealordering.employee.utils.ToastUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MapActivity extends TitleActivity implements
        AMap.OnMapClickListener,
        PoiSearch.OnPoiSearchListener, RouteSearch.OnRouteSearchListener, GeocodeSearch.OnGeocodeSearchListener
        , AMapLocationListener {
    @InjectView(R.id.map)
    MapView mMapView;
    LatLonPoint mStartPoint;
    LatLonPoint mEndPoint;
    OrderResults.Order mOrder;
    private AMap mAMap;
    /**
     * 搜索时进度条
     */
    private ProgressDialog mProgressDialog;
    /**
     * 路线查询
     */
    private RouteSearch mRouteSearch;
    private GeocodeSearch mGeocoderSearch;
    /**
     * 定位
     */
    private LocationManagerProxy mAMapLocManager;
    private AMapLocation mAMapLocation;

    @Override
    protected void onTitleBtnClick(View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.inject(this);
        setButtonTitle("返回");
        mMapView.onCreate(savedInstanceState);
        mAMap = mMapView.getMap();
        mAMap.setOnMapClickListener(this);
        mRouteSearch = new RouteSearch(this);
        mRouteSearch.setRouteSearchListener(this);

        mGeocoderSearch = new GeocodeSearch(this);
        mGeocoderSearch.setOnGeocodeSearchListener(this);

        mAMapLocManager = LocationManagerProxy.getInstance(this);
        showProgressDialog("正在定位,请稍等...");
        mAMapLocManager.requestLocationUpdates(
                LocationProviderProxy.AMapNetwork, 2000, 10, this);

        mOrder = (OrderResults.Order) getIntent().getSerializableExtra(Intents.ITEM);

    }

    /**
     * 开始搜索路径规划方案
     */
    public void searchRouteResult(LatLonPoint startPoint, LatLonPoint endPoint) {
        showProgressDialog("正在搜索");
        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                startPoint, endPoint);
        RouteSearch.WalkRouteQuery query = new RouteSearch.WalkRouteQuery(fromAndTo, RouteSearch.WalkDefault);
        // 异步路径规划步行模式查询
        mRouteSearch.calculateWalkRouteAsyn(query);
    }


    /**
     * 显示进度框
     */
    private void showProgressDialog(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    /**
     * 隐藏进度框
     */
    private void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * *******************路线规划****************************
     */
    @Override
    public void onBusRouteSearched(BusRouteResult arg0, int arg1) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult arg0, int arg1) {

    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult result, int rCode) {
        dismissProgressDialog();

        if (result != null && result.getPaths() != null
                && result.getPaths().size() > 0) {

            WalkPath walkPath = result.getPaths().get(0);
            // 清理地图上的所有覆盖物
            mAMap.clear();
            WalkRouteOverlay walkRouteOverlay = new WalkRouteOverlay(this,
                    mAMap, walkPath, result.getStartPos(),
                    result.getTargetPos());
            walkRouteOverlay.removeFromMap();
            walkRouteOverlay.addToMap();
            walkRouteOverlay.zoomToSpan();
        } else {
            ToastUtil.showLongTimeMsg("获取步行路线失败!");
        }
    }

    /**
     * *******************搜索poi****************************
     */
    @Override
    public void onPoiItemDetailSearched(PoiItemDetail arg0, int arg1) {

    }

    @Override
    public void onPoiSearched(PoiResult arg0, int arg1) {

    }

    /**
     * *********************点击地图**************************
     */
    @Override
    public void onMapClick(LatLng arg0) {

    }

    /**
     * ***********************************************
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

    }

    @Override
    public void onGeocodeSearched(GeocodeResult result, int rCode) {
        switch (rCode) {
            case 0:
                dismissProgressDialog();
                if (result != null && result.getGeocodeAddressList() != null
                        && result.getGeocodeAddressList().size() > 0) {
                    GeocodeAddress geocodeAddress = result.getGeocodeAddressList().get(0);
//                    LatLonPoint latLonPoint = geocodeAddress.getLatLonPoint();
//                    LatLng latLng = new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());
//                    mAMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
//                            latLng, 15));

                    mEndPoint = geocodeAddress.getLatLonPoint();
                    ;
//                    String addressName = "经纬度值:" + geocodeAddress.getLatLonPoint() + "\n位置描述:"
//                            + geocodeAddress.getFormatAddress();
//                    ToastUtil.showShortTimeMsg(addressName);

                    searchRouteResult(mStartPoint, mEndPoint);
                } else {
                    ToastUtil.showShortTimeMsg(R.string.no_result);
                }
                break;
            case 27:
                ToastUtil.showShortTimeMsg(R.string.error_network);
                break;
            case 32:
                ToastUtil.showShortTimeMsg(R.string.error_key);
            default:
                ToastUtil.showShortTimeMsg(
                        getString(R.string.error_other) + rCode);
                break;
        }

    }

    /**
     * ********************定位结果***************************
     */
    @Override
    public void onLocationChanged(AMapLocation location) {

        if (location == null) {
            ToastUtil.showLongTimeMsg("定位失败,请重试!");
            return;
        }

        mAMapLocation = location;// 判断超时机制
//        Double geoLat = location.getLatitude();
//        Double geoLng = location.getLongitude();
        mStartPoint = new LatLonPoint(location.getLatitude(), location.getLongitude());

        // 第一个参数表示地址，第二个参数表示查询城市，中文或者中文全拼，citycode、adcode，

        GeocodeQuery query = new GeocodeQuery(mOrder.getDeliveryAddress(), mOrder.getCity());
        // 设置同步地理编码请求
        mGeocoderSearch.getFromLocationNameAsyn(query);
        mAMapLocManager.removeUpdates(this);
//        String cityCode = "";
//        String desc = "";
//        Bundle locBundle = location.getExtras();
//        if (locBundle != null) {
//            cityCode = locBundle.getString("citycode");
//            desc = locBundle.getString("desc");
//        }
//        String str = ("定位成功:(" + geoLng + "," + geoLat + ")"
//                + "\n精    度    :" + location.getAccuracy() + "米"
//                + "\n定位方式:" + location.getProvider() + "\n定位时间:"
//                + AMapUtil.convertToTime(location.getTime()) + "\n城市编码:"
//                + cityCode + "\n位置描述:" + desc + "\n省:"
//                + location.getProvince() + "\n市:" + location.getCity()
//                + "\n区(县):" + location.getDistrict() + "\n区域编码:" + location
//                .getAdCode());
    }


    /**
     * *********************定位废弃的方法,无用**************************
     */
    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    /**
     * ***********************************************
     */


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
}
