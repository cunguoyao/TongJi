package com.linkage.tongji;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.linkage.tongji.app.Urls;
import com.linkage.utils.BuildInfo;
import com.linkage.utils.C;
import com.linkage.utils.LogUtils;
import com.linkage.utils.NetRequest;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by cunguoyao on 2018/3/29.
 */

public class SplashActivity extends BaseActivity {

    private static final String TAG = SplashActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setSwipeBackEnable(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fetchData();
            }
        }, 500);
    }

    private void fetchData() {
        Map<String, String> params = new HashMap<>();
        params.put("appSmartVersion", String.valueOf(BuildInfo.TINKER_ID));
        params.put("appDevice", "android");
        params.put("appVersion", String.valueOf(BuildInfo.VERSION_CODE));
        NetRequest.postFormRequest(Urls.version, params, TAG, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                LogUtils.d("--NetRequest--success--" + result);
                JSONObject jsonObject = new JSONObject(result);
                int ret = jsonObject.optInt("ret", -1);
                goToLogin();
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                LogUtils.d("--NetRequest--fail--");
                goToLogin();
            }
        });
    }

    private void goToLogin() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetRequest.cancelRequest(TAG);
    }
}
