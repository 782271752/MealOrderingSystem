package com.mealordering;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.joboevan.push.tool.PushManager;
import com.mealordering.net.model.UserDetailResult;
import com.mealordering.utils.L;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.File;

import roboguice.util.temp.Ln;

public class AppContext extends Application {
    private static final String USER_DETAIL = "user_detail";
    private static AppContext instance;
    private SharedPreferences preferences;
    private UserDetailResult.UserDetail userDetail;
    private Gson gson;

    public static AppContext getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        gson = new Gson();
        Ln.getConfig().setLoggingLevel(Log.ERROR);
        preferences = getSharedPreferences("setting", Context.MODE_PRIVATE);
        initImageLoader();
        String userDetailJson = preferences.getString(USER_DETAIL, "");
        userDetail = gson.fromJson(userDetailJson, UserDetailResult.UserDetail.class);
        startPushService();
    }

    public UserDetailResult.UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetailResult.UserDetail userDetail) {
        this.userDetail = userDetail;
        preferences.edit().putString(USER_DETAIL, gson.toJson(userDetail)).commit();
        startPushService();
    }

    private void startPushService() {
        PushManager.getInstance().setLogDisplay(false);
        if (isLogin()) {
            PushManager.getInstance().connect(this, true);
            PushManager.getInstance().isStopMessage(this, false);
            L.i("设置别名->%s", userDetail.getPhone());
            PushManager.getInstance().bindAlias(this, userDetail.getPhone());
        } else {
            PushManager.getInstance().isStopMessage(this, true);
            PushManager.getInstance().clearAlias(this);
        }
    }

    public boolean isLogin() {
        return userDetail != null;
    }

    private void initImageLoader() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.ic_launcher)
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
}
