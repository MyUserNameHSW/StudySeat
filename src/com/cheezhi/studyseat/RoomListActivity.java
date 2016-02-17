package com.cheezhi.studyseat;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.cheezhi.adapter.OneSpinner_Adapter;
import com.cheezhi.adapter.RoomInfo_Adapter;
import com.cheezhi.adapter.ThreeSpinner_Adapter;
import com.cheezhi.adapter.TwoSpinner_Adapter;
import com.cheezhi.bean.Seat;
import com.cheezhi.utils.MyApplication;
import com.cheezhi.utils.MyMethod;
import com.cheezhi.views.ActionSheetDialog;
import com.cheezhi.views.ActionSheetDialog.OnSheetItemClickListener;
import com.cheezhi.views.ActionSheetDialog.SheetItemColor;
import com.cheezhi.views.EditAlertDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class RoomListActivity extends Activity implements OnClickListener {
	Spinner areaSpinner, typeSpinner, sortSpinner;
	LinearLayout lin11, lin12, lin14, img_lin, empty;
	TextView area_choose, typeChoose, msgTextView;
	ImageView search, sort_img;
	PullToRefreshListView listView;
	RequestParams params;
	View view;
	List<Integer> list1, list2, list3;
	int rl_area = 0;
	int rl_type = 0;
	int rl_sort = 0;
	String rl_name = "";
	ArrayAdapter<String> areaAdapter = null;
	ArrayAdapter<String> typeAdapter = null;
	ArrayAdapter<String> sortAdapter = null;
	OneSpinner_Adapter mAdapter1;
	TwoSpinner_Adapter mAdapter2;
	ThreeSpinner_Adapter mAdapter3;
	List<Seat> list;
	RoomInfo_Adapter adapter;
	int totalNum = 0;
	int appointNum = 0;
	int surplusNum = 0;
	private PopupWindow popupWindow;
	private TextView showTools;
	private RelativeLayout twoPage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_room_list);
		initView(); // 初始化部分组件
		initList();
		initListView(); // 初始化listview和适配器
		initData(rl_area, rl_type, rl_sort, rl_name); // 网络请求获取list集合数据
	}

	// 初始化部分组件
	private void initView() {
		view = findViewById(R.id.load_2);
		msgTextView = (TextView) findViewById(R.id.my_msg);
		area_choose = (TextView) findViewById(R.id.area_choose);
		typeChoose = (TextView) findViewById(R.id.type_choose);
		lin11 = (LinearLayout) findViewById(R.id.lin11);
		lin12 = (LinearLayout) findViewById(R.id.lin12);
		lin14 = (LinearLayout) findViewById(R.id.lin14);
		empty = (LinearLayout) findViewById(R.id.empty_1);
		img_lin = (LinearLayout) findViewById(R.id.img_lin);
		search = (ImageView) findViewById(R.id.search_choose);
		listView = (PullToRefreshListView) findViewById(R.id.rl_listview);
		sort_img = (ImageView) findViewById(R.id.sort_img);
		showTools = (TextView) findViewById(R.id.showTools);
		twoPage = (RelativeLayout) findViewById(R.id.two_page);
		twoPage.setBackgroundResource(R.drawable.login_background);
		lin11.setOnClickListener(this);
		lin12.setOnClickListener(this);
		lin14.setOnClickListener(this);
		img_lin.setOnClickListener(this);
		msgTextView.setOnClickListener(this);
		showTools.setOnClickListener(this);
		// search.setOnClickListener(this);
	}

	// 初始化listview和适配器
	private void initListView() {
		list = new ArrayList<Seat>();
		adapter = new RoomInfo_Adapter(list, getApplicationContext());
		listView.setAdapter(adapter);
	}

	// 定义教学区选择框
	private void showTypeDialog1() {
		ActionSheetDialog dialog;
		if (list1.size() > 0) {
			dialog = new ActionSheetDialog(RoomListActivity.this).builder()
					.setTitle("请选择教学区").setCancelable(true)
					.setCanceledOnTouchOutside(true);
			for (int i = 0; i < list1.size(); i++) {
				final int a = i;
				dialog.addSheetItem(MyMethod.areaName(list1.get(i)),
						SheetItemColor.S_GREEN, new OnSheetItemClickListener() {

							@Override
							public void onClick(int which) {
								// TODO Auto-generated method stub
								rl_name = "";
								initListView();
								rl_area = list1.get(a);
								if (a == 0) {
									area_choose.setText("区域");
								} else {
									area_choose.setText(MyMethod
											.areaName(rl_area));
								}
								initData(rl_area, rl_type, rl_sort, rl_name);
							}
						});

			}
			dialog.show();
		}
	}

	// 定义类型选择弹框
	private void showTypeDialog2() {
		ActionSheetDialog dialog;
		if (list2.size() > 0) {
			dialog = new ActionSheetDialog(RoomListActivity.this).builder()
					.setTitle("请选择类型").setCancelable(true)
					.setCanceledOnTouchOutside(true);
			for (int i = 0; i < list2.size(); i++) {
				final int a = i;
				dialog.addSheetItem(MyMethod.nameName(list2.get(i)),
						SheetItemColor.S_GREEN, new OnSheetItemClickListener(

						) {

							@Override
							public void onClick(int which) {
								// TODO Auto-generated method stub
								rl_name = "";
								initListView();
								rl_type = list2.get(a);
								if (a == 0) {
									typeChoose.setText("类型");
								} else {
									typeChoose.setText(MyMethod
											.nameName(rl_type));
								}
								initData(rl_area, rl_type, rl_sort, rl_name);
							}
						});

			}
			dialog.show();
		}
	}

	// 添加分组数据
	private void initList() {
		list1 = new ArrayList<Integer>();
		list2 = new ArrayList<Integer>();
		list3 = new ArrayList<Integer>();
		list1.add(0);
		list1.add(4);
		list1.add(1);
		list1.add(2);
		list1.add(3);
		list2.add(0);
		list2.add(1);
		list2.add(3);
		list2.add(2);
		list3.add(1);
		list3.add(0);
	}

	// 网络请求获取数据
	private void initData(int areas, int types, int sorts, String name) {
		empty.setVisibility(View.GONE);
		view.setVisibility(View.VISIBLE);
		listView.setVisibility(View.VISIBLE);
		HttpUtils utils = new HttpUtils();
		params = new RequestParams();
		// Log.e("area", areas+"area");
		params.addBodyParameter("flag", 1 + "");
		params.addBodyParameter("sort", sorts + "");
		if ("".equals(rl_name)) {
			params.addBodyParameter("area", areas + "");
			params.addBodyParameter("type", types + "");
		} else {
			params.addBodyParameter("roomName", name + "");
		}

		utils.send(HttpMethod.POST, MyApplication.URL, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
						Log.e("TAG!", "网络连接失败");
						MyMethod.showToast(getApplicationContext(), "网络连接失败");
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
						String result = arg0.result;
						// Log.e("result", result+"result");
						Gson gson = new Gson();
						Type typeofType = new TypeToken<List<Seat>>() {
						}.getType();
						List<Seat> resultList = gson.fromJson(result,
								typeofType);
						// initTextNum(resultList);//获取座位数量，并填充数据
						view.setVisibility(View.GONE);
						if (resultList.size() == 0) {
							empty.setVisibility(View.VISIBLE);
							listView.setVisibility(View.GONE);
						}
						list.addAll(resultList);
						adapter.notifyDataSetChanged();
					}
				});
	}

	// 点击事件
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.lin11:
			showTypeDialog1();
			break;
		case R.id.lin12:
			showTypeDialog2();
			break;
		case R.id.lin14:

			if (rl_sort == 0) {
				rl_sort = 1;
				sort_img.setImageResource(R.drawable.down_sort);
			} else {
				rl_sort = 0;
				sort_img.setImageResource(R.drawable.up_sort);
			}
			initListView();
			initData(rl_area, rl_type, rl_sort, rl_name);
			break;
		case R.id.img_lin:
			showMyDialog();
			break;
		case R.id.my_msg:
			Intent intent = new Intent(this, MessageListActivity.class);
			startActivity(intent);
			break;
		case R.id.showTools:
			if (popupWindow != null && popupWindow.isShowing()) {
//				Animation animation = AnimationUtils.loadAnimation(this, R.anim.pop_move_hidden);
//	        	animation.setFillAfter(true);
				popupWindow.dismiss();
			} else {
//				final TranslateAnimation animation = new TranslateAnimation(0, 0,180, 0);
//	        	animation.setDuration(500);
				initmPopupWindowView();
//				Animation animation = AnimationUtils.loadAnimation(this, R.anim.pop_move_show);
//				animation.setFillAfter(true);
				popupWindow.showAsDropDown(showTools, 0, 2);
			}
			break;
		case R.id.goto_feedback:
			Intent intent2 = new Intent(this, FeedBackActivity.class);
			popupWindow.dismiss();
			startActivity(intent2);
			break;
		case R.id.goto_aboutus:
			Intent intent3 = new Intent(this, AboutUsActivity.class);
			popupWindow.dismiss();
			startActivity(intent3);
			break;
		default:
			break;
		}
	}



	// 显示教室号搜索框
	private void showMyDialog() {
		final EditAlertDialog eDialog = new EditAlertDialog(
				RoomListActivity.this).builder().setEditText("输入教室号")
				.setNegativeButton("取消", new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub

					}
				});
		eDialog.setPositiveButton("确定", new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String text = eDialog.getResult();
				initListView();
				rl_name = text;
				if (rl_name == null || rl_name.length() == 0) {
					initData(0, 0, 0, "");
				}
				initData(rl_area, rl_type, rl_sort, rl_name);
				area_choose.setText("区域");
				typeChoose.setText("类型");
				eDialog.dismiss();
			}
		});
		eDialog.show();
	}
	// 初始化popwindow
	private void initmPopupWindowView() {
		View customView = getLayoutInflater().inflate(R.layout.tools_item,
				null, false);
		TextView gotoFeedback = (TextView) customView
				.findViewById(R.id.goto_feedback);
		TextView gotoAboutUs = (TextView) customView
				.findViewById(R.id.goto_aboutus);
		gotoFeedback.setOnClickListener(this);
		gotoAboutUs.setOnClickListener(this);
		popupWindow = new PopupWindow(customView, 180, 180);
		popupWindow.setAnimationStyle(R.style.popmove);
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
}
