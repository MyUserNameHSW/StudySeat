package com.cheezhi.studyseat;

import com.cheezhi.adapter.MsgList_Adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class MsgContentActivity extends Activity {
    TextView themeTextView,contentTextView,depTextView,timeTextView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_msg_content);
		initView();
		get_Intent();
	}

	private void initView() {
		// TODO Auto-generated method stub
		themeTextView = (TextView) findViewById(R.id.msg_theme);
		contentTextView = (TextView) findViewById(R.id.msg_content);
		depTextView = (TextView) findViewById(R.id.msg_dep);
		timeTextView = (TextView) findViewById(R.id.msg_time);
	}

	private void get_Intent() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		String theme = intent.getStringExtra("theme");
		String content = intent.getStringExtra("content");
		String dep = intent.getStringExtra("department");
		String time = intent.getStringExtra("time");
		initTextView(theme,content,dep,time);
	}

	private void initTextView(String theme, String content, String dep,
			String time) {
		// TODO Auto-generated method stub
		themeTextView.setText(theme);
		contentTextView.setText("        "+content);
		depTextView.setText(dep);
		timeTextView.setText(time);
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		startActivity(new Intent(this,MessageListActivity.class));
	}
}
