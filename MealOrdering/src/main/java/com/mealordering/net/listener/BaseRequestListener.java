package com.mealordering.net.listener;

import com.mealordering.utils.L;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.listener.RequestProgress;
import com.octo.android.robospice.request.listener.RequestProgressListener;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;

public class BaseRequestListener<RESULT> implements RequestListener<RESULT>,
        RequestProgressListener {

    @Override
    public void onRequestFailure(SpiceException arg0) {
        L.e("onRequestFailure ->%s ", arg0);
    }

    @Override
    public void onRequestSuccess(RESULT result) {
        L.i("onRequestSuccess ->%s ", result);
        if (result instanceof InputStream) {
            IOUtils.closeQuietly((InputStream) result);
        }
    }

    @Override
    public void onRequestProgressUpdate(RequestProgress progress) {
        switch (progress.getStatus()) {
            case PENDING:
                onRequestStart();
                break;
            case LOADING_FROM_NETWORK:
                onRequestNetwork();
                break;
            case COMPLETE:
                onRequestComplete();
                break;
        }
    }

    public void onRequestStart() {
        L.i("onRequestStart");
    }

    public void onRequestNetwork() {
    }

    public void onRequestComplete() {
        L.i("onRequestComplete");
    }

}