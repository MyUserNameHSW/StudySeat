package com.cheezhi.studyseat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener {
	EditText username, password;
	Button login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		setContentView(R.layout.activity_main);
		// ��ʼ��view
		initView();
	}

	private void initView() {
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		login = (Button) findViewById(R.id.login);
		login.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		// ��ȡ���������
		String user = username.getText().toString().trim();
		String code = password.getText().toString().trim();
		switch (arg0.getId()) {
		case R.id.login:
			// ��������Ƿ���ȷ
			checkLogin(user, code);
			break;

		default:
			break;
		}
	}

	private void checkLogin(String userString, String codeString) {
//		if (userString.equals("")) {
//			MyMethod.showToast(getApplicationContext(), "学号为空");
//		} else if (codeString.equals("")) {
//			MyMethod.showToast(getApplicationContext(), "密码为空");
//		}
//		// ���������������ӽӿڣ�
//		else if (userString.equals("123456") && codeString.equals("123456")) {
//			MyMethod.showToast(getApplicationContext(), "登录成功");
			Intent intent = new Intent(MainActivity.this,
					RoomListActivity.class);
			startActivity(intent);
//		} else {
//			MyMethod.showToast(getApplicationContext(), "学号或密码错误");
//		}
	}
}
