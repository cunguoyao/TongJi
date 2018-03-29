package com.linkage.utils.tinker.reporter;

import com.tencent.tinker.loader.app.ApplicationLike;

/**
 * Created by cunguoyao on 2017/4/26.
 */

public class MyReporter implements SampleTinkerReport.Reporter {

    private static final String TAG = MyReporter.class.getName();

    private ApplicationLike application;

    private long cost;

    public MyReporter(ApplicationLike application) {
        //Log.e("---c--3", "MyReporter");
        this.application = application;
    }

    @Override
    public long getCost() {
        return cost;
    }

    @Override
    public void setCost(long cost) {
        this.cost = cost;
    }

    @Override
    public void onReport(int key) {
    }

    @Override
    public void onReport(int key, String keyStr) {
        //LogUtils.e("---c-----MyReporter--" + "key=" + key + "/cost=" + getCost() + "/keyStr=" + keyStr);
        uploadResult(key, keyStr);
    }

    @Override
    public void onReport(String message) {
    }

    /**
     * Upload path result to App Server
     * @param patchCode
     * @param message
     */
    private void uploadResult(int patchCode, String message) {

    }
}
