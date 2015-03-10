package com.mealordering.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.mealordering.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends Activity {

    @InjectView(R.id.welcome_iv)
    ImageView mWelcomeIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }


}
