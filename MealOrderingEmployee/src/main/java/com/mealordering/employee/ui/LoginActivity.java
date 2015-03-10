package com.mealordering.employee.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.mealordering.employee.AppContext;
import com.mealordering.employee.Intents;
import com.mealordering.employee.R;
import com.mealordering.employee.net.RequestHelper;
import com.mealordering.employee.net.listener.SimpleRequestListener;
import com.mealordering.employee.net.model.LoginResult;
import com.mealordering.employee.utils.ToastUtil;
import com.mealordering.employee.utils.ViewUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.login_username_et)
    EditText mUsernameEt;
    @InjectView(R.id.login_password_et)
    EditText mPwdEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        ViewUtil.findViewAndClick(this, R.id.login_btn, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:

                if (ViewUtil.checkEmpty(mUsernameEt, "请输入" + mUsernameEt.getHint()))
                    break;

                if (ViewUtil.checkEmpty(mPwdEt, "请输入" + mPwdEt.getHint()))
                    break;

                String username = mUsernameEt.getText().toString();
                String pwd = mPwdEt.getText().toString();
//                Intents.launchMyOrder(LoginActivity.this);
                RequestHelper.exeLoginRequest(this, getSpiceManager(), new LoginRequestListener(), username, pwd);
                break;
        }
    }

    private class LoginRequestListener extends SimpleRequestListener<LoginResult> {
        @Override
        public void onRequestSuccess(LoginResult result) {
            super.onRequestSuccess(result);
            if (result.isSuccess()) {
                AppContext.getInstance().setEmployeeId(result.getData().getEmployeeId());
                Intents.launchMyOrder(LoginActivity.this);
                finish();
            } else {
                ToastUtil.showShortTimeMsg(result.getMessage());
            }
        }
    }

}
