package com.linkage.tongji.app;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cunguoyao on 2018/3/28.
 */

public class BaseApplication extends Application {

    private static String TAG = BaseApplication.class.getName();
    private static BaseApplication mInstance;
    private List<String> unSetStateBarColorPage;

    public static synchronized BaseApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initUnSetStatusBarColorPage();
    }

    private void initUnSetStatusBarColorPage() {
        unSetStateBarColorPage = new ArrayList<>();
        unSetStateBarColorPage.add(getPackageName() + ".LoginActivity");
    }

    public List<String> getUnSetStateBarColorPage() {
        return unSetStateBarColorPage;
    }

}
