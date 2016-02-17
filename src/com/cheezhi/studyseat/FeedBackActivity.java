package com.cheezhi.studyseat;

import com.cheezhi.utils.MyApplication;
import com.cheezhi.utils.MyMethod;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

public class FeedBackActivity extends Activity {
	EditText msgText, email;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_feed_back);
		msgText = (EditText) findViewById(R.id.add_content);
		email = (EditText) findViewById(R.id.userinfo);
	}

	public void submit(View view) {
		String text = msgText.getText().toString();
		String emails = email.getText().toString().trim();
		if (text == null || text.length() == 0) {
			MyMethod.showToast(this, "还是写点意见吧");
		} else if (emails == null || emails.length() == 0) {
			MyMethod.showToast(this, "告诉我们您的联系方式呗");
		} else {
			submitMyMessage(text, emails);
		}
	}

	private void submitMyMessage(String text, String emails) {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		params.addBodyParameter("flag", "2");
		params.addBodyParameter("content", text);
		params.addBodyParameter("contact", emails);
		MyApplication.utils.send(HttpMethod.POST, MyApplication.URL,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
						String result = arg0.result;
						if (result.equals("success")) {
							MyMethod.showToast(FeedBackActivity.this, "已提交");
							FeedBackActivity.this.finish();
						} else {
							MyMethod.showToast(FeedBackActivity.this, "提交失败");
						}
					}
				});
	}
}
