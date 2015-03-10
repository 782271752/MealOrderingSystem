package com.mealordering.ui;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.mealordering.R;
import com.mealordering.net.RequestHelper;
import com.mealordering.net.listener.SimpleRequestListener;
import com.mealordering.net.model.RegisterCodeResult;
import com.mealordering.utils.ToastUtil;
import com.mealordering.utils.UpdateUtil;
import com.mealordering.utils.ViewUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FindBackActivity extends TitleSubActivity implements View.OnClickListener {

    @InjectView(R.id.find_phone_et)
    EditText mPhoneEt;
    @InjectView(R.id.find_code_et)
    EditText mCodeEt;
    @InjectView(R.id.find_pwd_et)
    EditText mPwdEt;
    @InjectView(R.id.find_pwd_confirm_et)
    EditText mPwdConfrimEt;
    @InjectView(R.id.find_send_code_btn)
    Button mSendCodeBtn;


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
        setContentView(R.layout.activity_find_back);
        ButterKnife.inject(this);
        ViewUtil.findViewAndClick(this, R.id.find_btn, this);
        ViewUtil.setOnClick(mSendCodeBtn, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.find_send_code_btn:
                if (verifyPhone()) return;
                Message msg = new Message();
                msg.what = 0;
                mWaitHandler.sendMessage(msg);
                RequestHelper.exeGetCodeRequest(getSpiceManager(),
                        new GetCodeRequest(), mPhoneEt.getText().toString());
                break;
            case R.id.find_btn:
                onRegisterClick();
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

        String phone = mPhoneEt.getText().toString();
//        RequestHelper.exeRegisterRequest(getSpiceManager(), new RegisterRequestListener(), phone, pwd);
        UpdateUtil.showWaitDialog(this);
        UpdateUtil.updateUserAsync(UpdateUtil.getUpdateUserUrl(phone,pwd),this);
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
            ToastUtil.showShortTimeMsg(registerCode.getMessage());
        }
    }
}
