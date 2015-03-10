package com.mealordering.net.model;

import com.google.api.client.util.Key;

/**
 * Created by Anthonit on 2014/4/21.
 */
public class RegisterResult extends Result<RegisterResult.User> {
    @Override
    public String getMessage() {
        String message = null;
        switch (result) {
            case 0:
                message = "对不起,注册失败!";
                break;
            case 1:
                message = "欢迎使用肯急送!";
                break;
            case 2:
                message = "对不起,账号重复!!";
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
