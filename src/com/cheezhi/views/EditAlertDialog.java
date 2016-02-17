package com.cheezhi.views;

import com.cheezhi.studyseat.R;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class EditAlertDialog {
	private Context context;
	private Dialog dialog;
	private LinearLayout lLayout_bg;
	private EditText edittxt_result;
	private ImageView dialog_marBottom;
	private Button btn_neg;
	private Button btn_pos;
	private ImageView img_line;
	private Display display;
	private boolean showMsg = false;
	private boolean showEditText = false;

	public EditAlertDialog(Context context) {
		this.context = context;
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		display = windowManager.getDefaultDisplay();
	}

	public EditAlertDialog builder() {
		// 获取Dialog布局
		View view = LayoutInflater.from(context).inflate(
				R.layout.toast_view_edittext, null);
		// 获取自定义Dialog布局中的控件
		lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bgs);
		edittxt_result = (EditText) view.findViewById(R.id.rooms_name);
		dialog_marBottom = (ImageView) view.findViewById(R.id.dialog_marBottom);
		btn_neg = (Button) view.findViewById(R.id.btn_neg);
		btn_pos = (Button) view.findViewById(R.id.btn_pos);
		img_line = (ImageView) view.findViewById(R.id.img_line);

		// 定义Dialog布局和参数
		dialog = new Dialog(context, R.style.EditDialogStyle);
		dialog.setContentView(view);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.TOP);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.y = 100;
        window.setAttributes(lp);
		// 调整dialog背景大小
		lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
				.getWidth() * 0.85), LayoutParams.WRAP_CONTENT));

		return this;
	}

	/*public EditAlertDialog setTitle(String title) {
		showTitle = true;
		if ("".equals(title)) {
			txt_title.setText("标题");
		} else {
			txt_title.setText(title);
		}
		return this;
	}*/

	public EditAlertDialog setEditText(String msg) {
		showEditText = true;
			edittxt_result.setHint(msg);
		return this;
	}

	public String getResult() {
		return edittxt_result.getText().toString().trim();
	}

	public EditAlertDialog setCancelable(boolean cancel) {
		dialog.setCancelable(cancel);
		return this;
	}

	public EditAlertDialog setPositiveButton(String text,
			final OnClickListener listener) {
		if ("".equals(text)) {
			btn_pos.setText("确定");
		} else {
			btn_pos.setText(text);
		}
		btn_pos.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onClick(v);
//				dialog.dismiss();
			}
		});
		return this;
	}

	public EditAlertDialog setNegativeButton(String text,
			final OnClickListener listener) {
		if ("".equals(text)) {
			btn_neg.setText("取消");
		} else {
			btn_neg.setText(text);
		}
		btn_neg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onClick(v);
				dialog.dismiss();
			}
		});
		return this;
	}

	public void dismiss(){
		dialog.dismiss();
	}

	public void show() {
		dialog.show();
	}
}
