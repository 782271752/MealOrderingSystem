package com.mealordering.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mealordering.Intents;
import com.mealordering.R;
import com.mealordering.net.RequestHelper;
import com.mealordering.net.listener.SimpleRequestListener;
import com.mealordering.net.listener.UserDetailRequestListener;
import com.mealordering.net.model.LoginResult;
import com.mealordering.utils.ToastUtil;
import com.mealordering.utils.ViewUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends TitleSubActivity implements View.OnClickListener {

    @InjectView(R.id.login_phone_et)
    EditText mPhoneEt;
    @InjectView(R.id.login_pwd_et)
    EditText mPwdEt;

    @InjectView(R.id.login_find_tv)
    TextView mFindTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        ViewUtil.findViewAndClick(this, R.id.register_btn, this);
        ViewUtil.findViewAndClick(this, R.id.login_btn, this);
        ViewUtil.findViewAndClick(this,R.id.login_find_tv,this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_btn:
                Intents.launchRegister(this);
                finish();
                break;
            case R.id.login_btn:

                if (ViewUtil.checkEmpty(mPhoneEt, getString(R.string.input_phone)))
                    break;

                if (ViewUtil.checkEmpty(mPwdEt, getString(R.string.input_pwd)))
                    break;

                String phone = mPhoneEt.getText().toString();
                String pwd = mPwdEt.getText().toString();
                RequestHelper.exeLoginRequest(getSpiceManager(), new LoginRequestListener(), phone, pwd);
                break;
            case R.id.login_find_tv:
                Intent intent=new Intent(LoginActivity.this,FindBackActivity.class);
                startActivity(intent);
                break;
        }
    }

    private class LoginRequestListener extends SimpleRequestListener<LoginResult> {
        @Override
        public void onRequestSuccess(LoginResult result) {
            super.onRequestSuccess(result);
            if (result.isSuccess()) {
                RequestHelper.exeUserDetailRequest(getSpiceManager(), new UserDetailRequestListener(LoginActivity.this),
                        result.getData().getUserId());
            } else {
                ToastUtil.showShortTimeMsg(result.getMessage());
            }
        }
    }
}
