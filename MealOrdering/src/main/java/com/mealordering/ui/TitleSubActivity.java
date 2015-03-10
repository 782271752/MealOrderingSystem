package com.mealordering.ui;


import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.mealordering.R;
import com.mealordering.utils.ViewUtil;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class TitleSubActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        setContentView(getLayoutInflater().inflate(layoutResID, null));
    }

    @Override
    public void setContentView(View view) {
        if (ViewUtil.findView(view, R.id.back_btn) != null) {
            super.setContentView(view);
        } else {
            LinearLayout container = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_title_sub, null);
            container.addView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
            super.setContentView(container);
        }
        ViewUtil.findViewAndClick(TitleSubActivity.this, R.id.back_btn, new BackClickListener());
        setTitle(getTitle());
    }

    class BackClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }

    public void setTitle(int resId) {
        setTitle(getString(resId));
    }

    public void setTitle(String title) {
        ViewUtil.findTextViewAndSetText(this, R.id.title_tv, title);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        setTitle(title.toString());
    }

}
