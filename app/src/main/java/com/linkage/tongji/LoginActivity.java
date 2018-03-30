package com.linkage.tongji;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.linkage.tongji.app.Urls;
import com.linkage.tongji.bean.IndexReport;
import com.linkage.tongji.bean.User;
import com.linkage.utils.C;
import com.linkage.utils.LogUtils;
import com.linkage.utils.NetRequest;
import com.linkage.utils.SharedPreferencesUtils;
import com.linkage.widget.EditPwdDialog;
import com.linkage.widget.JellyInterpolator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

public class LoginActivity extends BaseActivity implements OnClickListener {

	private static final String TAG = LoginActivity.class.getName();

	private TextView mBtnLogin;
	private View progress;
	private View mInputLayout;
	private LinearLayout mName, mPsw;
	private EditText tv_user,tv_pwd;
	private User user;
	private boolean autoLogin;
	private EditPwdDialog editPwdDialog;

	private int fromLogout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		setSwipeBackEnable(false);
		initView();
	}
	public void hideKeyboard(IBinder token) {
		((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(token, 0);
	}

	@Override
	public void onResume() {
		super.onResume();
		mInputLayout.setVisibility(View.VISIBLE);
		mName.setVisibility(View.VISIBLE);
		mPsw.setVisibility(View.VISIBLE);
		mBtnLogin.setVisibility(View.VISIBLE);
		tv_pwd.setText("");
	}

	private void initView() {
		fromLogout = getIntent().getIntExtra("logout", 0);
		mBtnLogin = (TextView) findViewById(R.id.main_btn_login);
		progress = findViewById(R.id.layout_progress);
		mInputLayout = findViewById(R.id.input_layout);
		mName = (LinearLayout) findViewById(R.id.input_layout_name);
		mPsw = (LinearLayout) findViewById(R.id.input_layout_psw);
		tv_user = (EditText) findViewById(R.id.tv_user);
		tv_pwd= (EditText) findViewById(R.id.tv_pwd);
		mBtnLogin.setOnClickListener(this);

		tv_pwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
										  KeyEvent event) {
				if (actionId == EditorInfo.IME_NULL) {
					hideKeyboard(mPsw.getWindowToken());
					return true;
				}
				return false;
			}
		});
		if(fromLogout != 1) {
			user = getAccount();
			if (user != null) {
				tv_user.setText(user.getLoginName());
				//tv_pwd.setText(user.getLoginPass());
				autoLogin = true;
				handler.sendEmptyMessageDelayed(1, 100);
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.main_btn_login:
				String username = tv_user.getText().toString();
				String password = tv_pwd.getText().toString();
				if(TextUtils.isEmpty(username)) {
					Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
					return;
				}
				if(!autoLogin) {
					if (TextUtils.isEmpty(password)) {
						Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
						return;
					}
				}
				mBtnLogin.setVisibility(View.GONE);
				hideKeyboard(mPsw.getWindowToken());
				float loginBtnWidth = mBtnLogin.getMeasuredWidth();
				float loginBtnHeight = mBtnLogin.getMeasuredHeight();
				inputAnimator(mInputLayout, loginBtnWidth, loginBtnHeight);
				break;
		}
	}



	private void inputAnimator(final View view, float w, float h) {

		ValueAnimator animator = ValueAnimator.ofFloat(0, w);
		animator.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float value = (Float) animation.getAnimatedValue();
				ViewGroup.MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
				params.leftMargin = (int) value;
				params.rightMargin = (int) value;
				view.setLayoutParams(params);
			}
		});
		AnimatorSet set = new AnimatorSet();
		ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout, "scaleX", 1f, 0.1f);
		ObjectAnimator animator1 = ObjectAnimator.ofFloat(mInputLayout, "scaleY", 1f, 0.1f);
		set.setDuration(500);
		set.setInterpolator(new AccelerateDecelerateInterpolator());
		set.playTogether(animator, animator2);
		set.playTogether(animator, animator1);
		set.start();
		set.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {

			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				progress.setVisibility(View.VISIBLE);
				progressAnimator(progress);
				mInputLayout.setVisibility(View.INVISIBLE);
				if(autoLogin) {
					login(user.getLoginName(), user.getLoginPass());
				}else {
					String username = tv_user.getText().toString();
					String password = tv_pwd.getText().toString();
					login(username, C.md5(password));
				}
			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}
		});

	}

	private void progressAnimator(final View view) {
		PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX", 0.5f, 0.5f);
		PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY", 0.5f, 0.5f);
		ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view, animator, animator2);
		animator3.setDuration(1000);
		animator3.setInterpolator(new JellyInterpolator());
		animator3.start();

	}

	/**
	 * 登录成功后跳转页面
	 */
	private void startIndexReportPage(ArrayList<IndexReport> list) {
		reDisplayForm();
		Intent intent_sl = new Intent();
		intent_sl.setClass(LoginActivity.this, MapActivity.class);
		Bundle b = new Bundle();
		b.putSerializable("IndexReportList", list);
		intent_sl.putExtras(b);
		startActivity(intent_sl);
		finish();
	}

	//login @params:password is after md5
	private void login(final String username, String password) {
		Map<String, String> params = new HashMap<>();
		params.put("user", username);
		params.put("pwd", password);
		autoLogin = false;
		NetRequest.postFormRequest(Urls.login, params, TAG, new NetRequest.DataCallBack() {
			@Override
			public void requestSuccess(String result) {
				LogUtils.d("--NetRequest--success--" + result);
				onLoginSuccess(result);
			}

			@Override
			public void requestFailure(Request request, IOException e) {
				LogUtils.d("--NetRequest--fail--");
				reDisplayForm();
				Toast.makeText(LoginActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
			}
		});
    }

    private void reDisplayForm() {
		progress.setVisibility(View.GONE);
		mInputLayout.setVisibility(View.VISIBLE);
		mBtnLogin.setVisibility(View.VISIBLE);

		ViewGroup.MarginLayoutParams params = (MarginLayoutParams) mInputLayout.getLayoutParams();
		params.leftMargin = 0;
		params.rightMargin = 0;
		mInputLayout.setLayoutParams(params);

		ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout, "scaleX", 0.1f, 1f );
		ObjectAnimator animator1 = ObjectAnimator.ofFloat(mInputLayout, "scaleY", 0.1f, 1f );
		animator2.setDuration(10);
		animator2.setInterpolator(new AccelerateDecelerateInterpolator());
		animator2.start();

		animator1.setDuration(10);
		animator1.setInterpolator(new AccelerateDecelerateInterpolator());
		animator1.start();
	}

    //首次登录修改密码
	private void modify(String token, final String password) {
		showLoading();
		Map<String, String> params = new HashMap<>();
		params.put("token", token);
		params.put("newPwd", C.md5(password));
		NetRequest.postFormRequest(Urls.resetPass, params, TAG, new NetRequest.DataCallBack() {
			@Override
			public void requestSuccess(String result) throws Exception {
				dismissLoading();
				editPwdDialog.dismiss();
				LogUtils.d("--NetRequest--success--" + result);
				JSONObject jsonObject = new JSONObject(result);
				int ret = jsonObject.optInt("ret", -1);
				if(ret == 0) {
					User user = getAccount();
					user.setLoginPass(C.md5(password));
					SharedPreferencesUtils.getInstance(LoginActivity.this, "report-client").setObject("assemble_", user);
					String token = user.getToken();
					fetchIndexReport(token);
				}else {
					String msg = jsonObject.optString("msg");
					if(TextUtils.isEmpty(msg)) {
						msg = "服务器返回失败";
					}
					Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void requestFailure(Request request, IOException e) {
				dismissLoading();
				LogUtils.d("--NetRequest--fail--");
				Toast.makeText(LoginActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

	//获取首页数据
	private void fetchIndexReport(String token) {
		Map<String, String> params = new HashMap<>();
		params.put("token", token);
		NetRequest.postFormRequest(Urls.indexReportList, params, TAG, new NetRequest.DataCallBack() {
			@Override
			public void requestSuccess(String result) throws Exception {
				LogUtils.d("--NetRequest--success--" + result);
				JSONObject jsonObject = new JSONObject(result);
				int ret = jsonObject.optInt("ret", -1);
				if(ret == 0) {
					ArrayList<IndexReport> reports = new Gson().fromJson(jsonObject.optString("data"), new TypeToken<ArrayList<IndexReport>>() {}.getType());
					Message message = new Message();
					message.obj = reports;
					message.what = 0;
					handler.sendMessage(message);
				}
			}

			@Override
			public void requestFailure(Request request, IOException e) {
				LogUtils.d("--NetRequest--fail--");
			}
		});
	}

	private void onLoginSuccess(String result) {
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(result);
		} catch (JSONException e) {
			e.printStackTrace();
			Toast.makeText(LoginActivity.this, "服务器返回错误", Toast.LENGTH_SHORT).show();
		}
		if(jsonObject != null) {
			int ret = jsonObject.optInt("ret", -1);
			if (ret == 0) {
				User user = new Gson().fromJson(jsonObject.optString("data"), User.class);
				if(user != null) {
					SharedPreferencesUtils.getInstance(LoginActivity.this, "report-client").setObject("assemble_", user);
					if(user.getPassFlag() == 0) {//首次登录，未修改密码
						popModifyPwdDialog(user.getToken());
					}else {
						fetchIndexReport(user.getToken());
					}
				}
			} else {
				reDisplayForm();
				String msg = jsonObject.optString("msg");
				if(TextUtils.isEmpty(msg)) {
					msg = "服务器返回失败";
				}
				Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
			}
		}
	}

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
        	switch (msg.what) {
				case 0:
					ArrayList<IndexReport> list = (ArrayList<IndexReport>)msg.obj;
					startIndexReportPage(list);
					break;
				case 1:
					mBtnLogin.performClick();
					break;
			}
        }

    };

	private void popModifyPwdDialog(final String token) {
		editPwdDialog = new EditPwdDialog(this);
		editPwdDialog.builder().setTitle("提示").setMsg("系统检测到您是第一次登录，需要修改密码才能使用").setCancelable(false)
				.setPositiveButton("确认", new OnClickListener() {
					@Override
					public void onClick(View v) {
						String input1 = editPwdDialog.getEditText1().getText().toString();
						String input2 = editPwdDialog.getEditText2().getText().toString();
						if(TextUtils.isEmpty(input1)) {
							Toast.makeText(LoginActivity.this, "请输入新密码", Toast.LENGTH_SHORT).show();
							return;
						}
						if(input1.length() > 10) {
							Toast.makeText(LoginActivity.this, "新密码长度过长", Toast.LENGTH_SHORT).show();
							return;
						}
						if(!input1.equals(input2)) {
							Toast.makeText(LoginActivity.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
							return;
						}
						modify(token, input1);
					}
		}).show();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		NetRequest.cancelRequest(TAG);
	}
}
