package com.cheezhi.db;

import java.util.ArrayList;
import java.util.List;

import com.cheezhi.bean.NotificationMsg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBManager {
	private DBHelper helper;
	private SQLiteDatabase db;

	public DBManager(Context context) {
		helper = new DBHelper(context);
		db = helper.getWritableDatabase();
	}

	// 增加send_msg表数据
	public void add(NotificationMsg msgBean) {
		db.beginTransaction(); // 开始事务
		try {
			db.execSQL(
					"INSERT INTO send_msgs VALUES(?,?,?,?,?,?,?)",
					new Object[] { msgBean.getId(), msgBean.getTheme(),
							msgBean.getContent(), msgBean.getDepartment(),
							msgBean.getTime(), 0, msgBean.getState() });
			db.setTransactionSuccessful(); // 设置事务成功完成
		} finally {
			db.endTransaction(); // 结束事务
		}
	}

	// 获取最大的id
	public int getIsExit() {
		Cursor c = db.rawQuery(
				"select msg_id from send_msgs order by msg_id desc limit 1",
				null);
		while (c.moveToNext()) {
			return c.getInt(0);
		}
		return 0;
	}

	// 查询所有消息
	public List<NotificationMsg> getResultList() {
		List<NotificationMsg> list = new ArrayList<NotificationMsg>();
		Cursor cursor = db.rawQuery(
				"select * from send_msgs where shows=0 order by msg_id desc",
				null);
		while (cursor.moveToNext()) {
			int id = cursor.getInt(0);
			String theme = cursor.getString(1);
			String content = cursor.getString(2);
			String department = cursor.getString(3);
			String times = cursor.getString(4);
			int item_state = cursor.getInt(5);
			NotificationMsg msg = new NotificationMsg(id, theme, content,
					department, times, item_state);
			list.add(msg);
		}
		return list;
	}

	// 根据id改变show
	public void updataShowById(String id) {
		ContentValues values = new ContentValues();
		values.put("shows", 1);// key为字段名，value为值
		db.update("send_msgs", values, "msg_id=?", new String[] { id });
	}

	// 根据id删除某一条信息
	public void deteleItemById(String[] id) {
		db.delete("send_msgs", "msg_id", id);
	}

	// 修改未被查看的信息为已查看
	public void updataStateById(String id) {
		ContentValues values = new ContentValues();
		values.put("item_states", 1);
		db.update("send_msgs", values, "msg_id=?", new String[] { id });
		// db.execSQL("UPDATE send_msgs SET item_states = 1 WHERE msg_id="+id);
	}

	// 判断信息是否被查看
	public boolean isLook(String id) {
		Cursor cursor = db.rawQuery(
				"select item_states from send_msgs where msg_id =" + id
						+ " and item_states = 0", null);
		while (cursor.moveToNext()) {
			return true;
		}
		return false;
	}
}
