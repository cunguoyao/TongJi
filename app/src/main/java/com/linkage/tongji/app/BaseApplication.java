package com.linkage.tongji.app;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.support.multidex.MultiDex;

import com.linkage.utils.tinker.TinkerManager;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cunguoyao on 2018/3/29.
 */

@DefaultLifeCycle(application = "com.linkage.tongji.app.TApplication",
        flags = ShareConstants.TINKER_ENABLE_ALL,
        loadVerifyFlag = false)
public class BaseApplication extends DefaultApplicationLike {

    public BaseApplication(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        MultiDex.install(base);
        TinkerManager.setTinkerApplicationLike(this);
        TinkerManager.initFastCrashProtect();
        //should set before tinker is installed
        TinkerManager.setUpgradeRetryEnable(true);
        //installTinker after load multiDex
        //or you can put com.tencent.tinker.** to main dex
        TinkerManager.installTinker(this);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callback) {
        getApplication().registerActivityLifecycleCallbacks(callback);
    }

    private static BaseApplication mInstance;
    private Context context;
    private List<String> unSetStateBarColorPage;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        context = getApplication();
        initUnSetStatusBarColorPage();
    }

    public static synchronized BaseApplication getInstance() {
        return mInstance;
    }

    private void initUnSetStatusBarColorPage() {
        unSetStateBarColorPage = new ArrayList<>();
        unSetStateBarColorPage.add(context.getPackageName() + ".SplashActivity");
        unSetStateBarColorPage.add(context.getPackageName() + ".LoginActivity");
    }

    public List<String> getUnSetStateBarColorPage() {
        return unSetStateBarColorPage;
    }

    public File getDirs() {
        File file = Environment.getExternalStorageDirectory();
        File workspace = new File(file, "report-client");
        if (!workspace.exists()) {
            workspace.mkdirs();
        }
        return workspace;
    }
}
