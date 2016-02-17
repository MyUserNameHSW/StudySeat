package com.cheezhi.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheezhi.bean.NotificationMsg;
import com.cheezhi.db.DBManager;
import com.cheezhi.studyseat.MessageListActivity;
import com.cheezhi.studyseat.R;
import com.cheezhi.utils.AnimationUtil;

public class MsgList_Adapter extends BaseAdapter {
	List<NotificationMsg> list;
	Context context;
	CheckBox checkBox;
	DBManager manager;
	MessageListActivity activity;
	LayoutInflater mInflater;
	public ViewHolder mHolder;
	public boolean isVisiable;

	public boolean isVisiable() {
		return isVisiable;
	}

	public void setVisiable(boolean isVisiable) {
		this.isVisiable = isVisiable;
	}

	public MsgList_Adapter(List<NotificationMsg> list, Context context ) {
		super();
		this.list = list;
		this.context = context;
		mInflater = LayoutInflater.from(context);
		activity = (MessageListActivity) context;
		checkBox = activity.getAll();
		manager = new DBManager(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@SuppressLint("ResourceAsColor") @Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if (arg1 == null) {
			arg1 = mInflater.inflate(R.layout.msg_listitem, null);
			mHolder = new ViewHolder();
			
			mHolder.theme = (TextView) arg1.findViewById(R.id.theme_item);
			mHolder.content = (TextView) arg1.findViewById(R.id.content_item);
			mHolder.day = (TextView) arg1.findViewById(R.id.year_day);
			mHolder.hour = (TextView) arg1.findViewById(R.id.hour_min);
			mHolder.relativeLayout = (RelativeLayout) arg1.findViewById(R.id.color_view);
			mHolder.selectItem = (CheckBox) arg1
					.findViewById(R.id.msg_checkbox);
			arg1.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) arg1.getTag();
		}
		if (isVisiable) {
			mHolder.selectItem.setVisibility(View.VISIBLE);
		} else {
			mHolder.selectItem.setVisibility(View.GONE);
		}
		final int index = arg0;
		
		if (manager.isLook(list.get(index).getId()+"") == true) {
			mHolder.relativeLayout.setBackgroundResource(R.color.yellow_alpha1);
		}else {
//			Log.e("state", "++"+list.get(arg0).getState());
			mHolder.relativeLayout.setBackgroundResource(R.color.white);
			
		}
		mHolder.theme.setText(list.get(arg0).getTheme());
		mHolder.content.setText(list.get(arg0).getContent());
		String time = list.get(arg0).getTime();
		mHolder.day.setText(yearAndDay(time));
		mHolder.hour.setText(hourAndMin(time));
		mHolder.selectItem.setChecked(list.get(arg0).isChecked());
		mHolder.selectItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CheckBox checkBox = (CheckBox) v;
				list.get(index).setChecked(checkBox.isChecked());
				isSelectedAll();
			}
		});
		return arg1;
	}

	private void isSelectedAll() {
		if (list.size() == 0) {
			checkBox.setClickable(false);
		}
		for (int i = 0; i < list.size(); i++) {
			if (!list.get(i).isChecked()) {
				checkBox.setChecked(false);
				return;
			} else {
				checkBox.setChecked(true);
			}
		}
	}

	private String yearAndDay(String string) {
		String[] s = string.split(" ");
		return s[0];
	}

	private String hourAndMin(String string) {
		String[] s = string.split(" ");
		return s[1];
	}

	private final class ViewHolder {
		TextView theme, content, day, hour;
		CheckBox selectItem;
		RelativeLayout relativeLayout;
	}
}
