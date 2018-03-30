package com.linkage.utils;

import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class FileHelper {

	private final String TAG = this.getClass().getSimpleName();

	public int fileSize;
	public int downLoadFileSize;
	public String fileEx, fileNa, filename;
	public String errormsg;
	/** 终止 */
	public volatile boolean mIsStop = false;
	/** 线程*/
	private ThreadPool mPool = new ThreadPool();
	/** 下载 */
	public static final int MESSAGE_OPEN_DIALOG = 0;
	/** 下载 */
	public static final int MESSAGE_START = 1;
	/** 更新进度 */
	public static final int MESSAGE_PROGRESS = 2;
	/** 下载结束 */
	public static final int MESSAGE_STOP = 3;
	/** 下载出错 */
	public static final int MESSAGE_ERROR = 4;

	public Handler mHandler;

	public FileHelper(Handler handler) {
		this.mHandler = handler;
	}
	public void down_file(String url, String path, String filename)  {
		LogUtils.i(TAG + " 文件保存到本地地址：" + path + "/" + filename);
		sendMsg(MESSAGE_OPEN_DIALOG);
		// 获取文件�?		
		URL myURL;

		InputStream is = null;
		FileOutputStream fos = null;

		// 下载函数
		if(filename==null)
		{
			filename = url.substring(url.lastIndexOf("/") + 1);
		}
		File file = new File(path, filename);
		try {
			File folder = new File(path);
			if(!folder.exists()) {
				folder.mkdirs();
			}
			if(file.exists())
			{
				file.delete();
			}

			myURL = new URL(url);
			URLConnection conn = myURL.openConnection();
			conn.connect();
//			conn.setRequestProperty("Accept-Encoding", "identity"); 
//			this.fileSize = conn.getContentLength();// 根据响应获取文件大小



			is = conn.getInputStream();
			fos = new FileOutputStream(file);
			// 把数据存入路�?文件�?			
			byte buf[] = new byte[1024];
			downLoadFileSize = 0;
			sendMsg(MESSAGE_START);
			do {
				// 循环读取
				int numread = is.read(buf);
				if (numread == -1) {
					break;
				}
				fos.write(buf, 0, numread);
				downLoadFileSize += numread;

				sendMsg(MESSAGE_PROGRESS);// 更新进度�?	
			} while (!mIsStop);
			if(!mIsStop)
			{
				sendMsg(MESSAGE_STOP);// 通知下载完成
			}
			else {
				sendMsg(MESSAGE_ERROR);// 通知下载失败
			}


		} catch (MalformedURLException e) {
			sendMsg(MESSAGE_ERROR);
		} catch (IOException e) {
			e.printStackTrace();
			if(file.exists()) {
				file.delete();
			}
			sendMsg(MESSAGE_ERROR);// 通知下载失败
		}
		finally
		{
			try {
				if(is!=null)
				{
					is.close();
				}
				if(fos!=null)
				{
					fos.close();
				}
			} catch (Exception ex) {
				LogUtils.e("tag:error: ", ex.getMessage());
			}
		}



	}

	private void sendMsg(int flag) {
		Message msg = new Message();
		msg.what = flag;
		mHandler.sendMessage(msg);
	}

	public void stopALl() {
		mIsStop = true;
		mPool.stop();
	}
}
