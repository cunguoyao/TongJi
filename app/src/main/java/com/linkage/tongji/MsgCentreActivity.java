package com.linkage.tongji;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsoluteLayout;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.linkage.dragGridView.MainActivity;
import com.linkage.shapeloading.LoadingView;
import com.linkage.tongji.bean.MsgBean;

import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MsgCentreActivity extends AppCompatActivity implements OnClickListener {

	private static final String TAG = MsgCentreActivity.class.getSimpleName();

	private ListView mListView;
	private Button back;
	private MsgCentreListAdapter mAdapter;
	private List<MsgBean> exerList;
	private LoadingView loadingView;
	private ActionBar actionBar;
	private Toolbar mToolbar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_msg_centre);
//		StatusBarUtil.setColor(MsgCentreActivity.this, getResources().getColor(R.color.colorMain),0);
		loadingView= (LoadingView)findViewById(R.id.loadView);
		actionBar  = getSupportActionBar();
		if(actionBar!=null) {
			actionBar.hide();
		}
		mToolbar = (Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(mToolbar);
		if (getSupportActionBar() != null) {
			getSupportActionBar().setDisplayHomeAsUpEnabled(false);
			getSupportActionBar().setDisplayShowTitleEnabled(false);
		}

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//        }
		setTitle("消息中心");
//		back = (Button) findViewById(R.id.back);
		Intent intent =getIntent();
		String text =intent.getStringExtra("sfname");
//		Toast.makeText(MsgCentreActivity.this, text, Toast.LENGTH_SHORT).show();
		mListView = (ListView) findViewById(R.id.list);

//		back.setOnClickListener(this);
		exerList = new ArrayList<MsgBean>();

		mAdapter = new MsgCentreListAdapter(this, exerList);
		mListView.setAdapter(mAdapter);

//		fetchExerciseList();
		getData();
	}


	private void fetchExerciseList() {

	}




	@Override
	public void onClick(View v) {
		switch (v.getId()) {
//			case R.id.back:
//				finish();
//				break;

		}

	}

	@Override
	protected void onPause() {
		super.onPause();
		if (isFinishing()) {
//			BaseApplication.getInstance().cancelPendingRequests(TAG);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}


	class MsgCentreListAdapter extends BaseAdapter {

		private Context mContext;
		private LayoutInflater mLayoutInflater;
		private List<MsgBean> members;
		private SimpleDateFormat sdf;

		@SuppressLint("SimpleDateFormat")
		public MsgCentreListAdapter(Context context, List<MsgBean> members) {
			this.mContext = context;
			this.mLayoutInflater = LayoutInflater.from(mContext);
			this.members = members;
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return members.size();
		}

		public void addAll(List<MsgBean> data) {
			if (this.members != null) {
				this.members.clear();
				this.members.addAll(data);
			} else {
				this.members = data;
			}
			notifyDataSetChanged();
		}

		@Override
		public MsgBean getItem(int position) {
			// TODO Auto-generated method stub
			return members.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			final ViewHolder holder;
			final MsgBean item = getItem(position);
			if (convertView == null || convertView.getTag() == null) {
				convertView = mLayoutInflater
						.inflate(R.layout.adapter_msg_centre_item,
								parent,
								false);
				holder = new ViewHolder();
				holder.init(convertView);
//				holder.swipe_msg_list_item = (SwipeMenuLayout) convertView;
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			TextPaint tp = holder.title.getPaint();

			holder.title.setText(item.title);
			holder.content.setText(item.content);
			holder.time.setText(item.time);

//			holder.rlDel.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					cleanMessage(holder.swipe_msg_list_item, position, item);
//				}
//			});

			holder.front_msg_list_item.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent_sl = new Intent();
					intent_sl.setClass(mContext, ViewPagerActivity.class);
					startActivity(intent_sl);

				}
			});
			return convertView;
		}

		class ViewHolder {
			private TextView title;
			private TextView content;
			private TextView time;
			private RelativeLayout rlDel;
			private RelativeLayout front_msg_list_item;
//			private SwipeMenuLayout swipe_msg_list_item;

			void init(View convertView) {
				title = (TextView) convertView.findViewById(R.id.item_title);
				content = (TextView) convertView.findViewById(R.id.item_content);
				time = (TextView) convertView.findViewById(R.id.date_text);
//				rlDel = (RelativeLayout) convertView.findViewById(R.id.rl_msglist_delete);
				front_msg_list_item = (RelativeLayout) convertView.findViewById(R.id.front_msg_list_item);
			}
		}

	}

	private void onClickEnvet(MsgBean pushMsgBean){
		Intent intent = null;

	}

	private void getData() {
		loadingView.setVisibility(View.VISIBLE);
		try {
			String url = "https://www.baidu.com";
			OkHttpClient client = new OkHttpClient();
			RequestBody formBody = new FormBody.Builder()
					.add("name", "liming")
					.add("school", "beida")
					.build();

			Request request = new Request.Builder()
					.url(url)
					.post(formBody)
					.build();

			Call call = client.newCall(request);
			call.enqueue(new Callback() {
				@Override
				public void onFailure(Call call, IOException e) {
//					loadingView.setVisibility(View.GONE);
					handler.sendEmptyMessage(0);

				}

				@Override
				public void onResponse(Call call, Response res) throws IOException {


					try {
						Thread.sleep(2000);
					} catch (InterruptedException ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}

						MsgCentreActivity.this.runOnUiThread(new Runnable() {
							@Override
							public void run() {

								try {
									JSONObject response ;
									loadingView.setVisibility(View.GONE);
//								Toast.makeText(MainActivity.this, errorMMessage, Toast.LENGTH_SHORT).show();


                            String str = "{\"ret\":0,\"data\":[{\"id\":1,\"content\":\"内容内容内容内容内容内容\",\"tutorialId\":1,\"title\":\"标题\",\"time\":\"2018-01-03(周三)\"},{\"id\":2,\"content\":\"内容\",\"tutorialId\":1,\"title\":\"标题\",\"time\":\"2018-01-03(周三)\"}],\"msg\":\"成功\"}";
						JSONObject jsonObject = new JSONObject(str);
                            response = jsonObject;


					if (response.optInt("ret") == 0) {
						List<MsgBean> temp = MsgBean
								.parseFromJson(response
										.optJSONArray("data"));
						if (temp != null && temp.size() > 0) {
							mAdapter.addAll(temp);
                            handler.sendEmptyMessage(0);
						}else{

						}



					} else {

					}
					}catch (Exception e){
						e.printStackTrace();
					}
						}
					});
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub

			try {
				Thread.sleep(1000);
                loadingView.setVisibility(View.GONE);
			} catch (InterruptedException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}


		}

	};


}
