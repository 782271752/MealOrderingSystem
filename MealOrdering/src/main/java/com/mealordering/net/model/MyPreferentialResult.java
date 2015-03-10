package com.mealordering.net.model;

import com.google.api.client.util.Key;

import java.io.Serializable;

/**
 * Created by Anthonit on 2014/4/22.
 */
public class MyPreferentialResult extends Results<MyPreferentialResult.MyPreferential> {
    public static class MyPreferential implements Serializable{
        @Key("PersonalPreferentialId")
        /**优惠券的标识ID(用于使用优惠券进行修改)*/
        private String personalPreferentialId;
        @Key("PreferentialId")
        private String preferentialId;
        @Key("Img")
        private String img;
        @Key("Title")
        private String title;
        @Key("Pnumber")
        private int pNumber;
        @Key("outTime")
        /**到期时间*/
        private String outTime;
        @Key("PreferentialType")
        /**1(物品券)，2（现金券）*/
        private int preferentialType;

        @Override
        public String toString() {
            return "MyPreferential{" +
                    "personalPreferentialId='" + personalPreferentialId + '\'' +
                    ", preferentialId='" + preferentialId + '\'' +
                    ", img='" + img + '\'' +
                    ", title='" + title + '\'' +
                    ", pNumber='" + pNumber + '\'' +
                    ", outTime='" + outTime + '\'' +
                    ", preferentialType='" + preferentialType + '\'' +
                    '}';
        }

        public String getPersonalPreferentialId() {
            return personalPreferentialId;
        }

        public void setPersonalPreferentialId(String personalPreferentialId) {
            this.personalPreferentialId = personalPreferentialId;
        }

        public String getPreferentialId() {
            return preferentialId;
        }

        public void setPreferentialId(String preferentialId) {
            this.preferentialId = preferentialId;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getpNumber() {
            return pNumber;
        }

        public void setpNumber(int pNumber) {
            this.pNumber = pNumber;
        }

        public String getOutTime() {
            return outTime;
        }

        public void setOutTime(String outTime) {
            this.outTime = outTime;
        }

        public int getPreferentialType() {
            return preferentialType;
        }

        public void setPreferentialType(int preferentialType) {
            this.preferentialType = preferentialType;
        }
    }
}
