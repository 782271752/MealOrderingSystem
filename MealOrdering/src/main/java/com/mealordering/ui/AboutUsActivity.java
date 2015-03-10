package com.mealordering.ui;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.mealordering.R;
import com.mealordering.utils.UpdateUtil;

public class AboutUsActivity extends TitleSubActivity {

    TextView versionTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
//        versionTv=(TextView)findViewById(R.id.version_code);
//        versionTv.setText("V  "+UpdateUtil.getLocalVersionCode(this)+"");
    }

}
