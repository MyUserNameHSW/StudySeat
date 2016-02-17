package com.cheezhi.services;

import java.lang.reflect.Type;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.cheezhi.bean.NotificationMsg;
import com.cheezhi.bean.Seat;
import com.cheezhi.db.DBManager;
import com.cheezhi.studyseat.MessageListActivity;
import com.cheezhi.studyseat.MsgContentActivity;
import com.cheezhi.studyseat.R;
import com.cheezhi.studyseat.RoomListActivity;
import com.cheezhi.utils.MyApplication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class NotificationService extends Service {
	private DBManager manager = null;
	private boolean flag;
	private Context context;
	List<NotificationMsg> list;
	RequestParams params;
	// 获取消息线程
	private MessageThread messageThread = null;

	// 点击查看
	private Intent messageIntent = null;
	private PendingIntent messagePendingIntent = null;
	// 通知栏消息
	private int messageNotificationID = 1000;
	private Notification messageNotification = null;
	private NotificationManager messageNotificatioManager = null;

	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		manager = new DBManager(getApplicationContext());
		messageThread = new MessageThread();
		messageThread.isRunning = true;
		messageThread.start();
		context = this;
		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * 从服务器端获取消息
	 * 
	 */
	class MessageThread extends Thread {
		// 设置是否循环推送
		public boolean isRunning = true;

		public void run() {
			while (isRunning) {
				try {
					getMsgBean();
					// 间隔时间
					Thread.sleep(10000);
					// 获取服务器消息
					String theme = "新消息";
					if (flag) {
						// 更新通知栏
						messageIntent = new Intent(context,
								MessageListActivity.class);

						messagePendingIntent = PendingIntent.getActivity(
								context, 0, messageIntent,
								PendingIntent.FLAG_UPDATE_CURRENT);
						messageNotification = new Notification();
						messageNotification.tickerText = theme;
						messageNotification.setLatestEventInfo(
								getApplicationContext(), "新消息",
								"您有" + list.size() + "条新消息",
								messagePendingIntent);
						messageNotification.icon = R.drawable.cheezhi;
						messageNotification.defaults = Notification.DEFAULT_SOUND;
						messageNotification.flags = Notification.FLAG_AUTO_CANCEL;
						messageNotificatioManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
						messageNotificatioManager.notify(messageNotificationID,
								messageNotification);

						// 每次通知完，通知ID递增一下，避免消息覆盖掉
						messageNotificationID++;
						for (int i = 0; i < list.size(); i++) {
							manager.add(list.get(i));
						}
					}

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void onDestroy() {
		// System.exit(0);
		messageThread.isRunning = false;
		super.onDestroy();
	}


	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (list.size() > 0) {
					flag = true;
				} else {
					flag = false;
				}
				break;

			default:
				break;
			}
		};
	};
	private HttpUtils utils;

	private void getMsgBean() {
		utils = new HttpUtils();
		params = new RequestParams();
		params.addBodyParameter("flag", "2");
		params.addBodyParameter("maxId", manager.getIsExit() + "");
		utils.send(HttpMethod.POST, MyApplication.URL, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
						String result = arg0.result;
						Gson gson = new Gson();
						Type typeofType = new TypeToken<List<NotificationMsg>>() {
						}.getType();
						list = gson.fromJson(result, typeofType);
//						Log.e("list.size", list.size()+"+++");
						Message message = new Message();
						message.what = 1;
						handler.sendMessage(message);
					}
				});
	}
}