package com.cheezhi.adapter;

import java.util.ArrayList;
import java.util.List;

import com.cheezhi.bean.Seat;
import com.cheezhi.studyseat.R;
import com.cheezhi.utils.MyMethod;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RoomInfo_Adapter extends BaseAdapter {
	List<Seat> list = new ArrayList<Seat>();
	Context context;
	LayoutInflater mInflater;
	ViewHolder mHolder;

	public RoomInfo_Adapter(List<Seat> list, Context context) {
		super();
		this.list = list;
		this.context = context;
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
			arg1 = mInflater.inflate(R.layout.room_num_item, null);
			mHolder = new ViewHolder();
			mHolder.area = (TextView) arg1.findViewById(R.id.area);
			mHolder.name = (TextView) arg1.findViewById(R.id.room_name);
			mHolder.num = (TextView) arg1.findViewById(R.id.room_num);
			arg1.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) arg1.getTag();
		}
/*		String areaString = null ;
		switch (list.get(arg0).getArea()) {
		case 4:
			areaString = "��1A";
			break;
		case 1:
			areaString = "��1B";
			break;
		case 2:
			areaString = "��1";
			break;
		case 3:
			areaString = "��2";
			break;
		default:
			break;
		}*/
		mHolder.area.setText(MyMethod.areaName(list.get(arg0).getArea()));
		mHolder.name.setText(list.get(arg0).getRoom_name());
		mHolder.num.setText(list.get(arg0).getSeatNum()+"");
		return arg1;
	}

	class ViewHolder {
		TextView area, name, num;
	}
}
