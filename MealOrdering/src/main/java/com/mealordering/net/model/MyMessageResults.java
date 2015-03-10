package com.mealordering.net.model;

import com.google.api.client.util.Key;

/**
 * Created by Anthonit on 2014/4/26.
 */
public class MyMessageResults extends Results<MyMessageResults.MyMessage> {
    @Override
    public String getMessage() {
        String message = null;
        switch (result) {
            case 0:
                message = "无消息!";
                break;
            case 1:
                if (getData().isEmpty()) {
                    message = "无消息!";
                } else {
                    message = "";
                }
                break;
        }
        return message;
    }

    public static class MyMessage {
        @Key("MessageId")
        private String messageId;
        @Key("MessageContent")
        private String messageContent;
        @Key("UserId")
        private String userId;
        @Key("ReleaseTime")
        private String releaseTime;
        @Key("WhetherRead")
        private boolean whetherRead;

        public String getMessageId() {
            return messageId;
        }

        public String getMessageContent() {
            return messageContent;
        }

        public String getUserId() {
            return userId;
        }

        public String getReleaseTime() {
            return releaseTime;
        }

        public boolean isWhetherRead() {
            return whetherRead;
        }

        public void setWhetherRead(boolean whetherRead) {
            this.whetherRead = whetherRead;
        }

        @Override
        public String toString() {
            return "MyMessage{" +
                    "messageId='" + messageId + '\'' +
                    ", messageContent='" + messageContent + '\'' +
                    ", userId='" + userId + '\'' +
                    ", releaseTime='" + releaseTime + '\'' +
                    ", whetherRead=" + whetherRead +
                    '}';
        }
    }
}
