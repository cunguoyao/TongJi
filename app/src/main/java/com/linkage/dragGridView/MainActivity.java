package com.linkage.dragGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.VideoView;

import com.jaeger.library.StatusBarUtil;
import com.linkage.shapeloading.LoadingView;
import com.linkage.tongji.LoginActivity;
import com.linkage.tongji.MsgCentreActivity;
import com.linkage.tongji.R;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * 
 * 
 * @author lyk
 *
 */
public class MainActivity extends SwipeBackActivity implements OnItemClickListener{
	private List<HashMap<String, Object>> dataSourceList = new ArrayList<HashMap<String, Object>>();
	private WebView mWebView;
	private ProgressBar web_process_bar;
	private View v;
	private static final int VIBRATE_DURATION = 20;
	private LoadingView loadingView;
	private SwipeBackLayout mSwipeBackLayout;
	private WebSettings webSettings = null;
	private HTML5WebChromeClient webChromeClient = null;
	/**
	 * 一页可见提条目数
	 */
	private static final int VISIBIY_NUMS = 9;
	private DragAdapter mDragAdapter;
	private Toolbar mToolbar;
	private ActionBar actionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		actionBar  = getSupportActionBar();
		actionBar.hide();
		mToolbar = (Toolbar)findViewById(R.id.toolbar);
		loadingView= (LoadingView)findViewById(R.id.loadView);


		StatusBarUtil.setColor(MainActivity.this, getResources().getColor(R.color.colorMain),0);
//		mToolbar.setTitle("统计页面");

//		DragGridView mDragGridView = (DragGridView)findViewById(R.id.dragGridView);
//		mDragGridView.setOnItemClickListener(this);
//
//		for (int i = 0; i < VISIBIY_NUMS; i++) {
//			HashMap<String, Object> itemHashMap = new HashMap<String, Object>();
//			Random random =new Random();
//
//
//			if (random.nextInt(3) == 1) {
//				itemHashMap.put("item_image",R.drawable.ic_icon);
//			}
//
//			if (random.nextInt(3) == 0) {
//				itemHashMap.put("item_image",R.drawable.icon);
//			}
//
//			else {
//				itemHashMap.put("item_image",R.drawable.icon4);
//			}
//			itemHashMap.put("item_text", "icon" + Integer.toString(i));
//			dataSourceList.add(itemHashMap);
//		}
//		mDragAdapter = new DragAdapter(this, dataSourceList);
//
//		mDragGridView.setAdapter(mDragAdapter);
//		//设置需要抖动
//		mDragGridView.setNeedShake(true);


		mWebView = (WebView) findViewById(R.id.webview);
		ResolveInfo ret = getResolveInfo();
		WebSettings setting=mWebView.getSettings();
//		setting.setPluginState(PluginState.ON);
		setting.setJavaScriptEnabled(true);
//		webChromeClient = new HTML5WebChromeClient();
		String permName = "android.permission.CAMERA";
		String pkgName = getPackageName();
		// 结果为0则表示使用了该权限，-1则表求没有使用该权限
		int reslut = getPackageManager().checkPermission(permName, pkgName);
		if (reslut == PackageManager.PERMISSION_GRANTED) {

//                startActivity(new Intent(MainActivity。this, CaptureActivity.class));
		} else {

		}
//		FullscreenableChromeClient mFullscreenableChromeClient = new FullscreenableChromeClient(MainActivity.this);
//		mWebView.setWebChromeClient(mFullscreenableChromeClient);
//        mWebView.setWebChromeClient(new WebChromeClient());
//		mWebView.setWebViewClient(new MyWebViewClient());
		mWebView.getSettings().setJavaScriptEnabled(true);

		webSettings = mWebView.getSettings();
		webSettings.setSupportZoom(true);
//		webSettings.setBuiltInZoomControls(true);
		webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//		webSettings.setPluginState(PluginState.OFF);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setBuiltInZoomControls(false); // 设置显示缩放按钮
		webSettings.setSupportZoom(true);
		webSettings.setLoadWithOverviewMode(true);

		webSettings.setBuiltInZoomControls(false);
		webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
		webSettings.setUseWideViewPort(true);
		webSettings.setLoadWithOverviewMode(true);
		//  s.setSavePassword(true);
		webSettings.setSaveFormData(true);

		webSettings.setUseWideViewPort(true);
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setSavePassword(true);
		webSettings.setSaveFormData(true);
		webSettings.setJavaScriptEnabled(true);     // enable navigator.geolocation
		webSettings.setGeolocationEnabled(true);

		webSettings.setDomStorageEnabled(true);
		webSettings.setJavaScriptEnabled(true);

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int mDensity = metrics.densityDpi;

		webSettings.setUseWideViewPort(true);

		webSettings.setLoadWithOverviewMode(true);
//		String url = "http://shop.jdyys.com/dist/home.html#/";
//		mWebView.loadUrl(url);
		mWebView.loadUrl("file:///android_asset/map-china.html");
		mWebView.setWebViewClient(new WebViewClient());
		mWebView.addJavascriptInterface(this, "android");

		mWebView.setWebChromeClient(new WebChromeClient()
		{
			public void onProgressChanged(WebView view, int progress)
			{
				//当进度走到100的时候做自己的操作，我这边是弹出dialog
				if(progress == 100){
					loadingView.setVisibility(View.GONE);
				}
			}
		});
	}


//	@JavascriptInterface
//	public void startFunction(){
//
//		runOnUiThread(new Runnable() {
//			@Override
//			public void run() {
//				Toast.makeText(MainActivity.this,"show",Toast.LENGTH_SHORT).show();
//
//			}
//		});
//	}

	@JavascriptInterface
	public void startFunction(final String text) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {


				Intent intent_sl = new Intent();
				intent_sl.setClass(MainActivity.this, MsgCentreActivity.class);
				intent_sl.putExtra("sfname", text);
				startActivity(intent_sl);
//				Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();

			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

	}


	private class HTML5WebChromeClient extends WebChromeClient {

		CustomViewCallback myCallback = null;
		View myView = null;
		View myVideoProgressView = null;


		// The undocumented magic method override
		// Eclipse will swear at you if you try to put @Override here
		// For Android 3.0+
		public void openFileChooser(ValueCallback<Uri> uploadMsg) {
			//AppLogger.i("dcc", "3.0 *");



		}

		// For Android 3.0+
		public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
			//AppLogger.i("dcc", "3.0");


		}

		// For Android 4.1
		public void openFileChooser(ValueCallback<Uri> uploadMsg,
									String acceptType, String capture) {
			//AppLogger.i("dcc", "4.1");

			//AppLogger.i("dcc", "4.1 isKilled=" + isKilled);






		}

		// file upload callback (Android 5.0 (API level 21) -- current) (public
		// method)
//		@SuppressWarnings("all")
//		public boolean onShowFileChooser(WebView webView,
//				ValueCallback<Uri[]> filePathCallback,
//				FullscreenableChromeClient.FileChooserParams fileChooserParams) {
//			//AppLogger.i("dcc", "5.0");
//
//
//			return true;
//		}

		@Override
		public boolean onJsConfirm(WebView view, String url, String message,
								   JsResult result) {

			if (isKitKat441()) {

				//AppLogger.i("dcc", "message=" + message);
				// 文件上传特定标示
//				if (!TextUtils.isEmpty(message)
//						&& message.indexOf("upload file") >= 0) {
//					result.confirm();
//
//					PreferenceUtils.getInstance().setWebViewJson(message);
//
//					showUploadWindow();
//
//					return true;
//				} else {
				return super.onJsConfirm(view, url, message, result);
//				}

			} else {
				return super.onJsConfirm(view, url, message, result);
			}
		}

		@Override
		public void onShowCustomView(View view,
									 CustomViewCallback callback) {

			super.onShowCustomView(view, callback);

//			Intent intent = new Intent(Intent.ACTION_VIEW);
//			String type = "video/3gp";
//			Uri uri = Uri.parse("http://dstatic.139jy.cn/studyvideo/donghuapian/wenziwangguo/164669313.mp4");
//			intent.setDataAndType(uri, type);
//			MainActivity.this.startActivity(intent);
//
			if (myCallback != null) {
				myCallback.onCustomViewHidden();
				myCallback = null;
				return;
			}

			FrameLayout frame = (FrameLayout) view;
			if (frame.getFocusedChild() instanceof VideoView){
				VideoView video = (VideoView) frame.getFocusedChild();
				frame.removeView(video);
//		            a.setContentView(video);
//		            video.setOnCompletionListener(this);
//		            video.setOnErrorListener(this);
//		            video.start();
			}

//			ViewGroup parent = (ViewGroup) mWebView.getParent();

//			removeWebView();

//			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//					LinearLayout.LayoutParams.FILL_PARENT,
//					LinearLayout.LayoutParams.FILL_PARENT);
//
//			lp.gravity = Gravity.CENTER_VERTICAL;
//			parent.addView(view, lp);
//			myView = view;
//			myCallback = callback;
		}

		@Override
		public void onHideCustomView() {

			// super.onHideCustomView();

			if (myView != null) {

				ViewGroup parent = (ViewGroup) myView.getParent();
				parent.removeView(myView);
				addWebView();
				myView = null;

				if (myCallback != null) {
					myCallback.onCustomViewHidden();
					myCallback = null;
				}
			}
		}

//		@Override
//		public View getVideoLoadingProgressView() {

//	        if (myVideoProgressView == null) {
//	            LayoutInflater inflater = LayoutInflater.from(engine.getCurActivity());
//	            myVideoProgressView = inflater.inflate(R.layout.video_loading_progress, null);
//	        }
//	        return myVideoProgressView;
//		}

		public boolean isShowFullScreenVideo()
		{
			return myView == null ? false : true;
		}

		@Override
		public void onProgressChanged(WebView view, int newProgress) {

			super.onProgressChanged(view, newProgress);

			if (web_process_bar != null) {
				if (newProgress <= 0 || newProgress >= 100) {
					web_process_bar.setVisibility(View.GONE);
					loadingView.setVisibility(View.GONE);
				}
				else {
					web_process_bar.setVisibility(View.VISIBLE);
					loadingView.setVisibility(View.VISIBLE);
				}
				web_process_bar.setProgress(newProgress);
			}

		}

	}

	/**
	 * 是否是安卓4.4.1
	 *
	 * @return
	 */
	public boolean isKitKat441() {

		if (android.os.Build.VERSION.RELEASE.equals("4.4.1")
				|| android.os.Build.VERSION.RELEASE.equals("4.4.2")) {
			return true;
		}

		return false;
	}

	private void addWebView()
	{

		LinearLayout.LayoutParams lps = new LinearLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);

		((ViewGroup) v).addView(mWebView, lps);


	}

	private void removeWebView()
	{
		((ViewGroup) v).removeView(mWebView);
	}

	private ResolveInfo getResolveInfo() {

		Intent intent =createVoiceSearchIntent();

		ResolveInfo ri = getPackageManager().

				resolveActivity(intent,PackageManager.MATCH_DEFAULT_ONLY);

		return ri;

	}

	protected Intent createVoiceSearchIntent() {

		return new Intent(RecognizerIntent.ACTION_WEB_SEARCH);

	}

	@Override
// 设置回退
// 覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
			mWebView.goBack(); // goBack()表示返回WebView的上一页面
			return true;
		}
		return super.onKeyDown(keyCode,event);
	}
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		view.loadUrl(url);
		return true;
	}

	private void vibrate(long duration) {
		Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		long[] pattern = {
				0, duration
		};
		vibrator.vibrate(pattern, -1);
	}

}
