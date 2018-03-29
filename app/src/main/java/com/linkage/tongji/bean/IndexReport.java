package com.linkage.tongji.bean;

import java.io.Serializable;

/**
 * Created by cunguoyao on 2018/3/28.
 */

public class IndexReport implements Serializable {

    private int provinceId;
    private String provinceName;
    private long createTime;
    private long userTotal;
    private long userIncrease;

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUserTotal() {
        return userTotal;
    }

    public void setUserTotal(long userTotal) {
        this.userTotal = userTotal;
    }

    public long getUserIncrease() {
        return userIncrease;
    }

    public void setUserIncrease(long userIncrease) {
        this.userIncrease = userIncrease;
    }

}
