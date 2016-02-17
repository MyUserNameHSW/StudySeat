package com.cheezhi.studyseat;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cheezhi.adapter.MsgList_Adapter;
import com.cheezhi.bean.NotificationMsg;
import com.cheezhi.db.DBManager;
import com.cheezhi.utils.AnimationUtil;

public class MessageListActivity extends Activity implements OnClickListener {
	ListView listView;
	TextView showAll;
	Button detele;
	CheckBox all;
	LinearLayout selectAll;
	MsgList_Adapter adapter;

	List<NotificationMsg> list;
	DBManager manager;
	Intent intent;
	boolean isChick = true;

	public CheckBox getAll() {
		return all;
	}

	public void setAll(CheckBox all) {
		this.all = all;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_message_list);
		manager = new DBManager(MessageListActivity.this);
		initView();
		initListView();
	}

	private void initListView() throws ArrayStoreException{
		// TODO Auto-generated method stub
		list = manager.getResultList();
		adapter = new MsgList_Adapter(list, MessageListActivity.this);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Log.e("give me result", list.get(position).getState()+"前前前");
				manager.updataStateById(list.get(position).getId()+"");
				Log.e("give me result", list.get(position).getState()+"后后后");
				intent = new Intent(MessageListActivity.this,
						MsgContentActivity.class);
				intent.putExtra("theme", list.get(position).getTheme());
				intent.putExtra("content", list.get(position).getContent());
				intent.putExtra("department", list.get(position)
						.getDepartment());
				intent.putExtra("time", list.get(position).getTime());
				startActivity(intent);
			}
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		showAll = (TextView) findViewById(R.id.show_all);
		selectAll = (LinearLayout) findViewById(R.id.select_all);
		all = (CheckBox) findViewById(R.id.all_item);
		detele = (Button) findViewById(R.id.detele_item);
		listView = (ListView) findViewById(R.id.msg_listview);

		all.setOnClickListener(this);
		detele.setOnClickListener(this);
		showAll.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.show_all:
			showTools();
			break;
		case R.id.all_item:
			selectAll();
			break;
		case R.id.detele_item:
			deteleItem();
			initListView();
			showAll.setText("编辑");
			selectAll.setVisibility(View.GONE);
			selectAll.setAnimation(AnimationUtil.moveToViewBottom());
			isChick = !isChick;
			break;
		default:
			break;
		}
		adapter.notifyDataSetChanged();
	}

	private void deteleItem() throws ArrayStoreException {
		// List<String> list2 = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).isChecked() == true) {
				Log.e("ITEM", "-->" + list.get(i).getId());
				manager.updataShowById(list.get(i).getId() + "");
			}
		}
		// final int listSize = list2.size();
		// String[] id = (String[])list2.toArray(new String[listSize]);//list转数组
	}

	private void selectAll() {
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setChecked(all.isChecked());
		}
	}

	private void showTools() {
		if (isChick) {
			showAll.setText("取消");
			selectAll.setVisibility(View.VISIBLE);
			selectAll.setAnimation(AnimationUtil.moveToViewLocation());
			adapter.setVisiable(true);
		} else {
			showAll.setText("编辑");
			all.setChecked(false);
			selectAll.setVisibility(View.GONE);
			selectAll.setAnimation(AnimationUtil.moveToViewBottom());
			adapter.setVisiable(false);
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setChecked(false);
			}
		}
		isChick = !isChick;
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		startActivity(new Intent(this,RoomListActivity.class));
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		adapter.notifyDataSetChanged();
	}
}
