package com.mealordering.net;

import android.app.Application;

import com.octo.android.robospice.GoogleHttpClientSpiceService;
import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.persistence.googlehttpclient.json.JacksonObjectPersisterFactory;

public class RoboSpiceService extends GoogleHttpClientSpiceService {

    @Override
    public CacheManager createCacheManager(Application application)
            throws CacheCreationException {
        CacheManager cacheManager = new CacheManager();

        JacksonObjectPersisterFactory jacksonObjectPersisterFactory = new JacksonObjectPersisterFactory(
                application);
        cacheManager.addPersister(jacksonObjectPersisterFactory);
        return cacheManager;
    }

}