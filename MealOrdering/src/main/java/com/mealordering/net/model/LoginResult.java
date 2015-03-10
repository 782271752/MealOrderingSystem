package com.mealordering.net.model;

import com.google.api.client.util.Key;

/**
 * Created by Anthonit on 2014/4/21.
 */
public class LoginResult extends Result<LoginResult.User> {
    @Override
    public String getMessage() {
        String message = null;
        switch (result) {
            case 0:
                message = "手机号码或密码错误,登录失败.";
                break;
            case 1:
                message = "登录成功!!";
                break;
        }
        return message;
    }

    public static class User {
        @Key("UserId")
        private String userId;

        @Override
        public String toString() {
            return "User{" +
                    "userId='" + userId + '\'' +
                    '}';
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
