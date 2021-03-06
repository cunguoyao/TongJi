package com.linkage.tongji;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.linkage.adapter.MenuAdapter;
import com.linkage.lib.SwipeBackLayout;
import com.linkage.shapeloading.LoadingView;
import com.linkage.tongji.app.Urls;
import com.linkage.tongji.bean.MenuBean;
import com.linkage.utils.LogUtils;
import com.linkage.utils.NetRequest;
import com.linkage.widget.MyGridView;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by cunguoyao on 2018/3/27.
 */

public class MenuActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = MenuActivity.class.getName();

    private LoadingView loadingView;
    private MyGridView gridView;
    private MenuAdapter menuAdapter;
    private List<MenuBean> menuList;
    private String provinceName;
    private int provinceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSwipeBackLayout().setSwipeMode(SwipeBackLayout.FULL_SCREEN_LEFT);
        setSwipeBackEnable(true);
        (findViewById(R.id.title_back)).setVisibility(View.VISIBLE);
        (findViewById(R.id.title_back)).setOnClickListener(this);
        loadingView = (LoadingView)findViewById(R.id.loadView);
        gridView = (MyGridView)findViewById(R.id.gridView);
        provinceName = getIntent().getStringExtra("provinceName");
        provinceId = getIntent().getIntExtra("provinceId", 0);
        menuList = new ArrayList<>();
        menuAdapter = new MenuAdapter(this, menuList);
        gridView.setAdapter(menuAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MenuBean item = menuAdapter.getItem(i);
                if(item.getCategory() == 6) {
                    Intent intent = new Intent(MenuActivity.this, MsgCentreActivity.class);
                    intent.putExtra("provinceId", provinceId);
                    intent.putExtra("title", item.getTitle());
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(MenuActivity.this, WebViewActivity.class);
                    String url = item.getSkipUrl() + "?category=" + item.getCategory() + "&token=" + getAccount().getToken()
                            + "&key=buMSl1ktYnt6a9ikMImfjV0aX6qNE" + "&provinceId=" + provinceId;
                    intent.putExtra("title", item.getTitle());
                    intent.putExtra("url", url);
                    intent.putExtra("displayType", item.getDisplayType());
                    startActivity(intent);
                }
            }
        });
        setTitle(provinceName);
        fetchMenuData();
    }

    private void fetchMenuData() {
        loadingView.setVisibility(View.VISIBLE);
        Map<String, String> params = new HashMap<>();
        params.put("token", getAccount().getToken());
        params.put("provinceId", provinceId+"");
        NetRequest.postFormRequest(Urls.menuList, params, TAG, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                loadingView.setVisibility(View.INVISIBLE);
                LogUtils.d("--NetRequest--success--" + result);
                JSONObject jsonObject = new JSONObject(result);
                int ret = jsonObject.optInt("ret", -1);
                if(ret == 0) {
                    List<MenuBean> temp = new Gson().fromJson(jsonObject.optString("data"), new TypeToken<ArrayList<MenuBean>>() {}.getType());
                    menuList.clear();
                    if(temp != null) {
                        menuList.addAll(temp);
                    }
                    menuAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void requestFailure(Request request, IOException e) {
                loadingView.setVisibility(View.INVISIBLE);
                LogUtils.d("--NetRequest--fail--");
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
        }
    }
}
