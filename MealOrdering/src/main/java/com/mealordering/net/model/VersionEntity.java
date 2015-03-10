package com.mealordering.net.model;

/**
 * Created by li on 2014/6/18.
 */
public class VersionEntity {
    private String id;
    private String versionNo;
    private String versionUrl;

    public void setId(String id){
        this.id=id;
    }

    public String getId(){
        return this.id;
    }

    public void setVersionNo(String versionNo){
        this.versionNo=versionNo;
    }
    public String getVersionNo(){
        return this.versionNo;
    }


    public void setVersionUrl(String versionUrl){
        this.versionUrl=versionUrl;
    }
    public String getVersionUrl(){
        return  this.versionUrl;
    }

}
