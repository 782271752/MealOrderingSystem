package com.mealordering.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.mealordering.R;
import com.mealordering.net.RequestHelper;
import com.mealordering.net.listener.SimpleRequestListener;
import com.mealordering.net.listener.UserDetailRequestListener;
import com.mealordering.net.model.RegisterCodeResult;
import com.mealordering.net.model.RegisterResult;
import com.mealordering.utils.ToastUtil;
import com.mealordering.utils.ViewUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Anthony on 14-2-20.
 */
public class RegisterActivity extends TitleSubActivity implements View.OnClickListener {

    @InjectView(R.id.register_phone_et)
    EditText mPhoneEt;
    @InjectView(R.id.register_code_et)
    EditText mCodeEt;
    @InjectView(R.id.register_pwd_et)
    EditText mPwdEt;
    @InjectView(R.id.register_pwd_confirm_et)
    EditText mPwdConfrimEt;
    @InjectView(R.id.register_send_code_btn)
    Button mSendCodeBtn;
    @InjectView(R.id.register_agree_cb)
    CheckBox mAgreeCb;
    @InjectView(R.id.register_agree_tv)
    TextView mAgreeTv;

    private RegisterCodeResult mRegisterCodeResult;
    private Handler mWaitHandler = new Handler() {

        private static final int SECOND = 1000;
        private static final int MINUTE = 60 * SECOND;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 <= MINUTE) {
                mSendCodeBtn.setEnabled(false);
                mSendCodeBtn.setText(String.format("(%s)重试", (MINUTE - msg.arg1) / SECOND));
                Message newMsg = obtainMessage();
                newMsg.arg1 = msg.arg1 + SECOND;
                sendMessageDelayed(newMsg, SECOND);
            } else {
                mSendCodeBtn.setEnabled(true);
                mSendCodeBtn.setText(R.string.send_code);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);
        ViewUtil.findViewAndClick(this, R.id.register_btn, this);
        ViewUtil.setOnClick(mSendCodeBtn, this);

        ViewUtil.findViewAndClick(this,R.id.register_agree_tv,this);
        ViewUtil.setOnClick(mAgreeTv,this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWaitHandler.dispatchMessage(mWaitHandler.obtainMessage());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_send_code_btn:
                if (verifyPhone()) return;
                Message msg = new Message();
                msg.what = 0;
                mWaitHandler.sendMessage(msg);
                RequestHelper.exeGetCodeRequest(getSpiceManager(),
                        new GetCodeRequest(), mPhoneEt.getText().toString());
                break;
            case R.id.register_btn:
                onRegisterClick();
                break;

            case R.id.register_agree_tv:
                Intent intent=new Intent(RegisterActivity.this,AboutActivity.class);
                startActivity(intent);
                break;
        }
    }

    void onRegisterClick() {
        if (verifyPhone()) return;

        if (ViewUtil.checkEmpty(mPwdEt, getString(R.string.input_pwd)))
            return;
        String pwd = mPwdEt.getText().toString();
        if (pwd.length() < 6) {
            ViewUtil.setError(mPwdEt, "密码长度不可少于6!");
            return;
        }
        String pwdConfirm = mPwdConfrimEt.getText().toString();
        if (!pwd.equals(pwdConfirm)) {
            ViewUtil.setError(mPwdConfrimEt, "输入的两次密码不一致!");
            return;
        }
        if (ViewUtil.checkEmpty(mCodeEt, getString(R.string.input_code)))
            return;

        if (mRegisterCodeResult == null || !mRegisterCodeResult.isSuccess()) {
            ViewUtil.setError(mCodeEt, "请获取验证码,并输入!");
            return;
        }

        String code = mCodeEt.getText().toString();

        if (!code.equals(String.valueOf(mRegisterCodeResult.getData().getCode()))) {
            ViewUtil.setError(mCodeEt, "验证码不正确,请重新输入!");
            return;
        }
        if (!mAgreeCb.isChecked()) {
            ToastUtil.showLongTimeMsg(R.string.read_and_agree_agreement);
            return;
        }
        String phone = mPhoneEt.getText().toString();
        RequestHelper.exeRegisterRequest(getSpiceManager(), new RegisterRequestListener(), phone, pwd);
    }

    private boolean verifyPhone() {
        if (ViewUtil.checkEmpty(mPhoneEt, getString(R.string.input_phone)))
            return true;
        String phone = mPhoneEt.getText().toString();
        if (!isMobileNum(phone)) {
            ViewUtil.setError(mPhoneEt, getString(R.string.phone_format_invaild));
            return true;
        }
        return false;
    }

    public boolean isMobileNum(String num) {
        String str = "^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(num);
        return m.matches();
    }

    private class GetCodeRequest extends SimpleRequestListener<RegisterCodeResult> {
        @Override
        public void onRequestSuccess(RegisterCodeResult registerCode) {
            super.onRequestSuccess(registerCode);
            mRegisterCodeResult = registerCode;
            if (registerCode.isSuccess()) {

            }
//            mWaitHandler.removeMessages(0);
//            mSendCodeBtn.setEnabled(true);
//            mSendCodeBtn.setText(R.string.send_code);
            ToastUtil.showShortTimeMsg(registerCode.getMessage());
        }
    }

    private class RegisterRequestListener extends SimpleRequestListener<RegisterResult> {
        @Override
        public void onRequestSuccess(RegisterResult result) {
            super.onRequestSuccess(result);
            ToastUtil.showShortTimeMsg(result.getMessage());
            if (result.isSuccess()) {
                RequestHelper.exeUserDetailRequest(getSpiceManager(), new UserDetailRequestListener(RegisterActivity.this),
                        result.getData().getUserId());

            }
        }
    }
}

