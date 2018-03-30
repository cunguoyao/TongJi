package com.linkage.tongji;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.linkage.adapter.ProvinceDetailAdapter;
import com.linkage.lib.SwipeBackLayout;
import com.linkage.shapeloading.LoadingView;
import com.linkage.tongji.app.Urls;
import com.linkage.tongji.bean.MsgBean;
import com.linkage.utils.LogUtils;
import com.linkage.utils.NetRequest;
import com.linkage.widget.SimpleListView;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

public class MsgCentreActivity extends BaseActivity implements SimpleListView.OnLoadListener, AdapterView.OnItemClickListener, OnClickListener{

	private static final String TAG = MsgCentreActivity.class.getSimpleName();

	private int provinceId;
	private View mEmptyView;
	private LoadingView loadingView;
	private SimpleListView listView;
	private ProvinceDetailAdapter mAdapter;
	private List<MsgBean> mData;
	private int page = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_msg_centre);
		getSwipeBackLayout().setSwipeMode(SwipeBackLayout.FULL_SCREEN_LEFT);
		setSwipeBackEnable(true);
		setTitle("消息中心");
		Intent intent =getIntent();
		provinceId =intent.getIntExtra("provinceId", 0);
		mEmptyView = findViewById(R.id.empty_rl);
		listView = (SimpleListView)findViewById(R.id.list);
		listView.setEmptyView(mEmptyView);
		mData = new ArrayList<>();
		mAdapter = new ProvinceDetailAdapter(this);
		listView.setAdapter(mAdapter);
		listView.setOnLoadListener(this);
		listView.setOnItemClickListener(this);
		fetchData();
	}

	@Override
	public void onLoad(boolean isRefresh) {
		if (isRefresh) {
			page = 1;
			mData.clear();
			mAdapter.notifyDataSetChanged();
		} else {
			page++;
		}
		fetchData();
	}

	private void fetchData() {
		Map<String, String> params = new HashMap<>();
		params.put("token", getAccount().getToken());
		params.put("provinceId", provinceId+"");
		params.put("page", String.valueOf(page));
		params.put("pageSize", String.valueOf(Urls.pageSize));
		NetRequest.postFormRequest(Urls.provinceDetail, params, TAG, new NetRequest.DataCallBack() {
			@Override
			public void requestSuccess(String result) throws Exception {

				LogUtils.d("--NetRequest--success--" + result);
				JSONObject jsonObject = new JSONObject(result);
				int ret = jsonObject.optInt("ret", -1);
				if(ret == 0) {
					List<MsgBean> temp = new Gson().fromJson(jsonObject.optString("data"), new TypeToken<ArrayList<MsgBean>>() {}.getType());
					mAdapter.setData(temp, page == 1 ? true : false);
					listView.finishLoad(page == 5 ? true : false); //最多加载五页数据
				}
			}
			@Override
			public void requestFailure(Request request, IOException e) {
				LogUtils.d("--NetRequest--fail--");
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Toast.makeText(this, "点击了" + position + " ", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		NetRequest.cancelRequest(TAG);
	}

}
