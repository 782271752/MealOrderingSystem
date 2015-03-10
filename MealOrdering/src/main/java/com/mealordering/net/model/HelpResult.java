package com.mealordering.net.model;

import com.google.api.client.util.Key;

/**
 * Created by Anthonit on 2014/4/22.
 */
public class HelpResult extends Results<HelpResult.Help> {

    public static class Help {
        @Key("AnsweTitle")
        private String answeTitle;
        @Key("AnsweContent")
        private String answeContent;
        @Key("AnsweTime")
        private String answeTime;
        @Key("PageId")
        private int pageId;

        @Override
        public String toString() {
            return "Help{" +
                    "answeTitle='" + answeTitle + '\'' +
                    ", answeContent='" + answeContent + '\'' +
                    ", answeTime='" + answeTime + '\'' +
                    ", pageId=" + pageId +
                    '}';
        }

        public String getAnsweTitle() {
            return answeTitle;
        }

        public void setAnsweTitle(String answeTitle) {
            this.answeTitle = answeTitle;
        }

        public String getAnsweContent() {
            return answeContent;
        }

        public void setAnsweContent(String answeContent) {
            this.answeContent = answeContent;
        }

        public String getAnsweTime() {
            return answeTime;
        }

        public void setAnsweTime(String answeTime) {
            this.answeTime = answeTime;
        }

        public int getPageId() {
            return pageId;
        }

        public void setPageId(int pageId) {
            this.pageId = pageId;
        }
    }
}
