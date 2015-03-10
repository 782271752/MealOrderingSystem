package com.mealordering.net.model;

import com.google.api.client.util.Key;

/**
 * Created by Anthony on 14-3-4.
 */
public class RegisterCodeResult extends Result<RegisterCodeResult.RegisterCode> {
    @Override
    public String getMessage() {
        String message = null;
        switch (result) {
            case 0:
                message = "无法获取验证码.";
                break;
            case 1:
                message = "验证码发送成功,请注意查收!";
                break;
            case 2:
                message = "手机号码格式不正确.";
                break;
            case 3:
                message = "手机号码为空.";
                break;
        }
        return message;
    }

    public static class RegisterCode {
        @Key("Code")
        private String code;

        @Override
        public String toString() {
            return "RegisterCode{" +
                    "code='" + code + '\'' +
                    '}';
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
