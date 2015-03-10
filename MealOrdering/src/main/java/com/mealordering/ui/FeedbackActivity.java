package com.mealordering.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.mealordering.R;
import com.mealordering.net.RequestHelper;
import com.mealordering.net.listener.SimpleRequestListener;
import com.mealordering.net.model.EmptyResult;
import com.mealordering.utils.ToastUtil;
import com.mealordering.utils.ViewUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FeedbackActivity extends TitleSubActivity implements View.OnClickListener {

    @InjectView(R.id.feedback_content_et)
    EditText mContentEt;
    @InjectView(R.id.feedback_email_et)
    EditText mEmailEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.inject(this);
        ViewUtil.findViewAndClick(this, R.id.feedback_submit_btn, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.feedback_submit_btn:
                if (ViewUtil.checkEmpty(mContentEt, "请输入内容!") ||
                        ViewUtil.checkEmpty(mEmailEt, "请输入你的Email!"))
                    return;
                String content = mContentEt.getText().toString();
                String email = mEmailEt.getText().toString();
                RequestHelper.exeFeedbackRequest(getSpiceManager(),
                        new FeedbackRequestListener(), content, email);
                break;
        }
    }

    private class FeedbackRequestListener extends SimpleRequestListener<EmptyResult> {
        public FeedbackRequestListener() {
            super(ViewUtil.findView(FeedbackActivity.this, R.id.loading_pb));
        }

        @Override
        public void onRequestSuccess(EmptyResult result) {
            super.onRequestSuccess(result);
            ToastUtil.showShortTimeMsg(result.getMessage());
            if (result.isSuccess()) {
                ToastUtil.showLongTimeMsg("意见,提交成功!");
                finish();
            }
        }
    }
}
