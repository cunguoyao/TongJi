package com.linkage.tongji.bean;

import java.io.Serializable;

/**
 * Created by cunguoyao on 2018/3/29.
 */

public class VersionBean implements Serializable {

    private int version;
    private String versionStr;
    private int patch;
    private String updateUrl;
    private String updateContent;
    private String updateTime;
    private String patchUrl;
    private int updateForce;
    private int updateFlag;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getVersionStr() {
        return versionStr;
    }

    public void setVersionStr(String versionStr) {
        this.versionStr = versionStr;
    }

    public int getPatch() {
        return patch;
    }

    public void setPatch(int patch) {
        this.patch = patch;
    }

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getUpdateFlag() {
        return updateFlag;
    }

    public void setUpdateFlag(int updateFlag) {
        this.updateFlag = updateFlag;
    }

    public String getPatchUrl() {
        return patchUrl;
    }

    public void setPatchUrl(String patchUrl) {
        this.patchUrl = patchUrl;
    }

    public int getUpdateForce() {
        return updateForce;
    }

    public void setUpdateForce(int updateForce) {
        this.updateForce = updateForce;
    }
}
