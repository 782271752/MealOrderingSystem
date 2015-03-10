package com.mealordering.employee.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.mealordering.net.RoboSpiceService;
import com.octo.android.robospice.SpiceManager;

public class BaseActivity extends FragmentActivity {

    private SpiceManager mSpiceManager = new SpiceManager(
            RoboSpiceService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        mSpiceManager.start(this);
        super.onStart();
    }


    @Override
    protected void onStop() {
        mSpiceManager.shouldStop();
        super.onStop();
    }

    public SpiceManager getSpiceManager() {
        return mSpiceManager;
    }

}
