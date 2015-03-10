package com.mealordering.employee;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.mealordering.employee.net.RequestHelper;
import com.mealordering.employee.net.listener.SimpleRequestListener;
import com.mealordering.employee.net.model.EmptyResults;
import com.mealordering.net.RoboSpiceService;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.octo.android.robospice.SpiceManager;

import java.io.File;

import roboguice.util.temp.Ln;

public class AppContext extends Application implements AMapLocationListener {
    private static final String EMPLOYEE_ID = "employee_id";

    private static AppContext instance;
    private SharedPreferences preferences;
    private String employeeId;
    private LocationManagerProxy mAMapLocManager;
    private SpiceManager mSpiceManager = new SpiceManager(
            RoboSpiceService.class);

    public static AppContext getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSpiceManager.start(this);
        instance = this;
        Ln.getConfig().setLoggingLevel(Log.ERROR);
        preferences = getSharedPreferences("setting", Context.MODE_PRIVATE);
        initImageLoader();
        employeeId = getEmployeeId();
        mAMapLocManager = LocationManagerProxy.getInstance(this);
    }

    public boolean isLogin() {
        return !TextUtils.isEmpty(employeeId);
    }

    private void initImageLoader() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher)
                .resetViewBeforeLoading(true).delayBeforeLoading(1)
                .cacheInMemory(true).cacheOnDisc(true).build();

        File cacheDir = ActivityCompat.getExternalCacheDirs(this)[0];
        if (cacheDir == null) {
            cacheDir = getCacheDir();
        }

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this)
                .threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY - 1)
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13)
                .discCache(
                        new UnlimitedDiscCache(cacheDir,
                                new Md5FileNameGenerator())
                )
                .discCacheSize(50 * 1024 * 1024).discCacheFileCount(150)
                .defaultDisplayImageOptions(options)
                /* .writeDebugLogs() */.build();

        ImageLoader.getInstance().init(config);
    }

    public SharedPreferences getSettingPreferences() {
        return preferences;
    }

    public String getEmployeeId() {
        return preferences.getString(EMPLOYEE_ID, "");
    }

    public void setEmployeeId(String employeeId) {
        preferences.edit().putString(EMPLOYEE_ID, employeeId).commit();
        this.employeeId = employeeId;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mSpiceManager.shouldStop();
    }

    public void startUploadLocation() {
        mAMapLocManager.requestLocationUpdates(
                LocationProviderProxy.AMapNetwork, 15 * 1000, 50, this);

    }

    public void stopUploadLocation() {
        mAMapLocManager.removeUpdates(this);
    }

    /**
     * ********************定位结果***************************
     */
    @Override
    public void onLocationChanged(AMapLocation location) {
        if (location != null) {
            RequestHelper.exeUpdateXYRequest(mSpiceManager,
                    new SimpleRequestListener<EmptyResults>(),
                    location.getLatitude(), location.getLongitude());
        }
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
}
