package com.linkage.tongji;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;

import com.google.gson.Gson;
import com.linkage.tongji.app.Urls;
import com.linkage.tongji.bean.VersionBean;
import com.linkage.utils.BuildInfo;
import com.linkage.utils.LogUtils;
import com.linkage.utils.NetRequest;
import com.linkage.widget.AlertDialog;

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
        params.put("origin", "android");
        params.put("version", String.valueOf(BuildInfo.VERSION_CODE));
        NetRequest.postFormRequest(Urls.version, params, TAG, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                LogUtils.d("--NetRequest--success--" + result);
                JSONObject jsonObject = new JSONObject(result);
                int ret = jsonObject.optInt("ret", -1);
                if(ret == 0) {
                    VersionBean version = new Gson().fromJson(jsonObject.optString("data"), VersionBean.class);
                    int versionCode = version.getVersion();
                    if(versionCode > BuildInfo.VERSION_CODE) {
                        popNeedUpdate(version);
                    }else if(versionCode == BuildInfo.VERSION_CODE) {
                        int pathVersion = version.getPatch();
                        if(pathVersion > Integer.valueOf(BuildInfo.TINKER_ID)) {
                            //TODO download and load patch
                        }else {
                            goToLogin();
                        }
                    }else {
                        goToLogin();
                    }
                }else {
                    goToLogin();
                }
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                LogUtils.d("--NetRequest--fail--");
                goToLogin();
            }
        });
    }

    private void popNeedUpdate(final VersionBean version){
        AlertDialog dialog = new AlertDialog(this);
        dialog.builder();
        dialog.setTitle("发现新版本");
        dialog.setMsg(""+version.getUpdateContent());
        dialog.setPositiveButton("更新", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Uri uri = Uri.parse(version.getUpdateUrl());
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                    finish();
                } catch (Exception e) {
                    goToLogin();
                }
            }
        });
        if(version.getUpdateForce() != 1) {
            dialog.setCancelable(true);
            dialog.setNegativeButton("取消", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToLogin();
                }
            });
        }else {
            dialog.setCancelable(false);
        }
        dialog.show();
    }

    private void loadPatch() {

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
