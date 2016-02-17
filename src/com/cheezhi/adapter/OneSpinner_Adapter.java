package com.cheezhi.adapter;

import java.util.List;

import com.cheezhi.studyseat.R;
import com.cheezhi.utils.MyMethod;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OneSpinner_Adapter extends BaseAdapter {
	Context context;
	List<Integer> list;
	LayoutInflater mInflater;
	ViewHolder mHolder;

	public OneSpinner_Adapter(Context context, List<Integer> list) {
		super();
		this.context = context;
		this.list = list;
		mInflater = LayoutInflater.from(context);
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

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if (arg1 == null) {
			arg1 = mInflater.inflate(R.layout.one_spinner_item, null);
			mHolder = new ViewHolder();
			mHolder.tv1 = (TextView) arg1.findViewById(R.id.my_item1);
			arg1.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) arg1.getTag();
		}
		mHolder.tv1.setText(MyMethod.areaName(list.get(arg0)));
		return arg1;
	}

	class ViewHolder {
		TextView tv1;
	}
}
