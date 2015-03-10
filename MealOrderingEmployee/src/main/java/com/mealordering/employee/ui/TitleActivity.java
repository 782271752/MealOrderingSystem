package com.mealordering.employee.ui;


import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.mealordering.employee.R;
import com.mealordering.employee.utils.ViewUtil;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public abstract class TitleActivity extends BaseActivity {

    protected abstract void onTitleBtnClick(View view);

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
        if (ViewUtil.findView(view, R.id.title_btn) != null) {
            super.setContentView(view);
        } else {
            LinearLayout container = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_title, null);
            container.addView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
            super.setContentView(container);
        }
        ViewUtil.findViewAndClick(TitleActivity.this, R.id.title_btn, new ButtonClickListener());
        setTitle(getTitle());
    }

    class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            onTitleBtnClick(v);
        }
    }

    public void setTitle(int resId) {
        setTitle(getString(resId));
    }

    public void setTitle(String title) {
        ViewUtil.findTextViewAndSetText(this, R.id.title_tv, title);
    }

    public void setButtonTitle(String title) {
        ViewUtil.findTextViewAndSetText(this, R.id.title_btn, title);
        ViewUtil.setGone(ViewUtil.findView(this, R.id.title_btn), false);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        setTitle(title.toString());
    }

}
