package com.cheezhi.utils;

import android.app.Application;
import android.content.Intent;

import com.cheezhi.studyseat.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;

public class MyApplication extends Application {
	public static HttpUtils utils;
	public static BitmapUtils bitmapUtils;
//	public static String IP = "192.168.1.20";
	 public static String IP = "115.28.111.135";
	public static String URL = "http://" + IP + ":8080/StudySeat/Link";

	
	// public static String URL = "http://"+IP+":8080/StudySeat/Link";

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		utils = new HttpUtils();
		bitmapUtils = new BitmapUtils(getApplicationContext());
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.smallimage_fail);
		bitmapUtils.configDefaultLoadingImage(R.drawable.smallimage_load);
		startMyService();
		super.onCreate();
	}

	private void startMyService() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setAction("NEW_MY_SERVICE");
		startService(intent);
	}

}
